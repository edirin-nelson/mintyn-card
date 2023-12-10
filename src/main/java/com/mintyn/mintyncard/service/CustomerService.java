package com.mintyn.mintyncard.service;

import com.mintyn.mintyncard.dto.request.RegisterRequest;
import com.mintyn.mintyncard.dto.response.RegisterResponse;
import com.mintyn.mintyncard.exceptions.UserAlreadyExistsException;
import com.mintyn.mintyncard.exceptions.UserNotFoundException;

public interface CustomerService {

    RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException, UserNotFoundException;
}
