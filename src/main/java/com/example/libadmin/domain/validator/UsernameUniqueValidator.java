package com.example.libadmin.domain.validator;

import com.example.libadmin.domain.User;
import com.example.libadmin.repository.UserRepository;
import com.example.libadmin.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, User> {

    private UserRepository userRepository;

    @Autowired
    public UsernameUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsernameUniqueValidator() {}

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if(userRepository == null) return true;
        return userRepository.findByUsername(user.getUsername()).isEmpty();
    }

    @Override
    public void initialize(UsernameUnique constraintAnnotation) {

    }
}
