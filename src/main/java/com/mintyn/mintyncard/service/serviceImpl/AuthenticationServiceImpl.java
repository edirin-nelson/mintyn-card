package com.mintyn.mintyncard.service.serviceImpl;

import com.mintyn.mintyncard.dto.request.LoginRequest;
import com.mintyn.mintyncard.dto.response.LoginResponse;
import com.mintyn.mintyncard.entity.Customer;
import com.mintyn.mintyncard.entity.JwtToken;
import com.mintyn.mintyncard.exceptions.UserNotFoundException;
import com.mintyn.mintyncard.repository.CustomerRepository;
import com.mintyn.mintyncard.repository.JwtTokenRepository;
import com.mintyn.mintyncard.security.JwtService;
import com.mintyn.mintyncard.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException {
        var customer = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UserNotFoundException("Customer with email: " +request.getEmail() +" not found"));
        customer.setEnabled(true);
        userRepository.save(customer);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        revokeToken(customer);
        var jwtToken = jwtService.generateToken(customer);
        saveToken(customer,jwtToken);

        return LoginResponse.builder()
                .userID(customer.getId().toString())
                .token(jwtToken)
                .expiredAt(jwtService.extractExpiration(jwtToken))
                .firstName(customer.getFirstName())
                .userType(customer.getRole().toString())
                .build();
    }

    private void saveToken(Customer customer, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .customer(customer)
                .build();
        jwtTokenRepository.save(token);
    }

    private void revokeToken(Customer user){
        var validTokenByUser= jwtTokenRepository.findTokenByCustomerAndExpiredIsFalseAndRevokedIsFalse(user);

        if(validTokenByUser.isEmpty()) return;

        validTokenByUser.forEach(token->{
            token.setRevoked(true);
            token.setExpired(true);
        });

        jwtTokenRepository.saveAll(validTokenByUser);
    }


}
