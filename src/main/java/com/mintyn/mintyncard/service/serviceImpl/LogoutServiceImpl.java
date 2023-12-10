package com.mintyn.mintyncard.service.serviceImpl;

import com.mintyn.mintyncard.repository.CustomerRepository;
import com.mintyn.mintyncard.security.repository.JwtTokenRepository;
import com.mintyn.mintyncard.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private  final JwtService jwtUtils;
    private final CustomerRepository employeeRepository;
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwt);

        var user = employeeRepository.findByEmail(userEmail)
                .orElse(null); // throw a null if the customer is not found.
        var token = jwtTokenRepository.findByToken(jwt)
                .orElse(null);
        if (token == null && user == null) {
            return; // does nothing if either one is null
        }
        assert user != null; // this make sure the customer is not null
        employeeRepository.save(user);

        assert token != null; //this make sure the token is not null
        token.setExpired(true);
        token.setRevoked(true);
        jwtTokenRepository.save(token);
    }
}
