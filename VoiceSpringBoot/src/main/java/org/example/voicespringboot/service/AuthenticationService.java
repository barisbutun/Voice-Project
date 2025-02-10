package org.example.voicespringboot.service;

import lombok.RequiredArgsConstructor;
import org.example.voicespringboot.dto.RegisterDto;
import org.example.voicespringboot.mapper.UserMapper;
import org.example.voicespringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

}
