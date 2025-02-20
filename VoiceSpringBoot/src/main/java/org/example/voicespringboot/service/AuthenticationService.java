package org.example.voicespringboot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.voicespringboot.dto.LoginDto;
import org.example.voicespringboot.entity.User;
import org.example.voicespringboot.enums.AuthProvider;
import org.example.voicespringboot.mapper.UserMapper;
import org.example.voicespringboot.repository.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Transactional
    public LoginDto login(OidcUser oidcUser) {
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String provider=oidcUser.getIssuer().toString();

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            try{
                user.setProvider(AuthProvider.fromIssuer(provider));
            }
            catch(IllegalArgumentException ex){
                System.err.println("Bilinmeyen Oauth sağlayıcısı: "+ex.getMessage());
            }

            user = userRepository.save(user);
        }

        return userMapper.toDtoLogin(user);
    }




}
