package com.mintyn.mintyncard.service;

import com.mintyn.mintynassessment.dto.request.RegisterRequest;
import com.mintyn.mintynassessment.dto.response.RegisterResponse;
import com.mintyn.mintynassessment.exceptions.UserAlreadyExistsException;
import com.mintyn.mintynassessment.exceptions.UserNotFoundException;

public interface UserService {

    RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException, UserNotFoundException;
}
