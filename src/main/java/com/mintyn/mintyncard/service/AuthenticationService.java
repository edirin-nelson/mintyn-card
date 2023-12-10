package com.mintyn.mintyncard.service;

import com.mintyn.mintyncard.dto.request.LoginRequest;
import com.mintyn.mintyncard.dto.response.LoginResponse;
import com.mintyn.mintyncard.exceptions.UserAccountDisabledException;
import com.mintyn.mintyncard.exceptions.UserNotFoundException;

public interface AuthenticationService  {

    LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException, UserAccountDisabledException;
}
