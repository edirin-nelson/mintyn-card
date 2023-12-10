package com.mintyn.mintyncard.service;

import com.mintyn.mintynassessment.dto.request.LoginRequest;
import com.mintyn.mintynassessment.dto.response.LoginResponse;
import com.mintyn.mintynassessment.exceptions.UserAccountDisabledException;
import com.mintyn.mintynassessment.exceptions.UserNotFoundException;

public interface AuthenticationService  {

    LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException, UserAccountDisabledException;
}
