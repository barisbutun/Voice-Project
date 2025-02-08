package org.example.voicespringboot.service;


import lombok.RequiredArgsConstructor;
import org.example.voicespringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }





}
