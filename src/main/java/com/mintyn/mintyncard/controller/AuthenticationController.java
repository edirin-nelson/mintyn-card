package com.mintyn.mintyncard.controller;

import com.mintyn.mintyncard.dto.request.LoginRequest;
import com.mintyn.mintyncard.dto.request.RegisterRequest;
import com.mintyn.mintyncard.dto.response.LoginResponse;
import com.mintyn.mintyncard.dto.response.RegisterResponse;
import com.mintyn.mintyncard.exceptions.UserAccountDisabledException;
import com.mintyn.mintyncard.exceptions.UserAlreadyExistsException;
import com.mintyn.mintyncard.exceptions.UserNotFoundException;
import com.mintyn.mintyncard.service.AuthenticationService;
import com.mintyn.mintyncard.service.CustomerService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) throws UserNotFoundException, UserAccountDisabledException {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody @Valid @NonNull RegisterRequest request) throws UserAlreadyExistsException, UserNotFoundException {
        return ResponseEntity.ok(customerService.registerUser(request));
    }
}