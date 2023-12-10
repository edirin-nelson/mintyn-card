package com.mintyn.mintyncard.service.serviceImpl;


import com.mintyn.mintynassessment.dto.request.LoginRequest;
import com.mintyn.mintynassessment.dto.response.LoginResponse;
import com.mintyn.mintynassessment.entity.JwtToken;
import com.mintyn.mintynassessment.entity.User;
import com.mintyn.mintynassessment.exceptions.UserNotFoundException;
import com.mintyn.mintynassessment.repository.JwtTokenRepository;
import com.mintyn.mintynassessment.repository.UserRepository;
import com.mintyn.mintynassessment.security.JwtService;
import com.mintyn.mintynassessment.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UserNotFoundException("User with email: " +request.getEmail() +" not found"));
        user.setEnabled(true);
        userRepository.save(user);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        revokeToken(user);
        var jwtToken = jwtService.generateToken(user);
        saveToken(user,jwtToken);

        return LoginResponse.builder()
                .userID(user.getId().toString())
                .token(jwtToken)
                .expiredAt(jwtService.extractExpiration(jwtToken))
                .firstName(user.getFirstName())
                .userType(user.getRole().toString())
                .build();
    }

    private void saveToken(User user, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        jwtTokenRepository.save(token);
    }

    private void revokeToken(User user){
        var validTokenByUser= jwtTokenRepository.findTokenByUserAndExpiredIsFalseAndRevokedIsFalse(user);

        if(validTokenByUser.isEmpty()) return;

        validTokenByUser.forEach(token->{
            token.setRevoked(true);
            token.setExpired(true);
        });

        jwtTokenRepository.saveAll(validTokenByUser);
    }


}
