package com.mintyn.mintyncard.controller;

import com.isdservice.qrcpay.dto.request.LoginRequest;
import com.isdservice.qrcpay.dto.request.RegisterRequest;
import com.isdservice.qrcpay.dto.response.LoginResponse;
import com.isdservice.qrcpay.dto.response.RegisterResponse;
import com.isdservice.qrcpay.exceptions.UserAccountDisabledException;
import com.isdservice.qrcpay.exceptions.UserAlreadyExistsException;
import com.isdservice.qrcpay.exceptions.UserNotFoundException;
import com.isdservice.qrcpay.service.AuthenticationService;
import com.isdservice.qrcpay.service.UserService;
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
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) throws UserNotFoundException, UserAccountDisabledException {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody @Valid @NonNull RegisterRequest request) throws UserAlreadyExistsException, UserNotFoundException {
        return ResponseEntity.ok(userService.registerUser(request));
    }
}