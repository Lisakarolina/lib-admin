package com.example.libadmin.service;


import com.example.libadmin.domain.User;
import com.example.libadmin.repository.RoleRepository;
import com.example.libadmin.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        encoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        // take the password from the form and encode
        String secret = encoder.encode(user.getPassword());
        user.setPassword(secret);
        // confirm password
        user.setConfirmPassword(secret);

        user.addRole(roleRepository.findByName("ROLE_USER"));
        user.setEnabled(true);
        save(user);

        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void saveUsers(User... users) {
        for(User user : users) {
            userRepository.save(user);
        }
    }

}