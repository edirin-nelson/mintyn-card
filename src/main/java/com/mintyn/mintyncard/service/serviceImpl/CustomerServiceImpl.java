package com.mintyn.mintyncard.service.serviceImpl;

import com.mintyn.mintyncard.dto.request.RegisterRequest;
import com.mintyn.mintyncard.dto.response.RegisterResponse;
import com.mintyn.mintyncard.entity.Customer;
import com.mintyn.mintyncard.enums.Role;
import com.mintyn.mintyncard.exceptions.UserAlreadyExistsException;
import com.mintyn.mintyncard.repository.CustomerRepository;
import com.mintyn.mintyncard.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException {

        Optional<Customer> customer = customerRepository.findByEmail(request.getEmail());

        if(customer.isPresent()){
            throw new UserAlreadyExistsException("Customer with email "+request.getEmail() + " already exists.");
        }

        Customer newCustomer = Customer.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(false)
                .role(Role.MINTYN_USER)
                .build();

        customerRepository.save(newCustomer);

        RegisterResponse response = new RegisterResponse();
        response.setEmail(newCustomer.getEmail());
        response.setLastName(newCustomer.getLastName());
        response.setFirstName(newCustomer.getFirstName());

        return response;
    }


}
