package com.mintyn.mintyncard.service.serviceImpl;

import com.mintyn.mintynassessment.dto.request.RegisterRequest;
import com.mintyn.mintynassessment.dto.response.RegisterResponse;
import com.mintyn.mintynassessment.entity.User;
import com.mintyn.mintynassessment.enums.Role;
import com.mintyn.mintynassessment.exceptions.UserAlreadyExistsException;
import com.mintyn.mintynassessment.repository.UserRepository;
import com.mintyn.mintynassessment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    @Override
    public RegisterResponse registerUser(RegisterRequest request) throws UserAlreadyExistsException {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistsException("User with email "+request.getEmail() + " already exists.");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(false)
                .role(Role.MINTYN_USER)
                .build();

        User u = userRepository.save(newUser);

        RegisterResponse response = new RegisterResponse();
        response.setEmail(u.getEmail());
        response.setLastName(u.getLastName());
        response.setFirstName(u.getFirstName());

        return response;
    }


}
