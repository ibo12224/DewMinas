package me.leewonjun.dewminas.services;

import lombok.RequiredArgsConstructor;
import me.leewonjun.dewminas.domains.User;
import me.leewonjun.dewminas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public User findUser(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("UserService.findUser() : no user mail - "+email)
                );
    }
}
