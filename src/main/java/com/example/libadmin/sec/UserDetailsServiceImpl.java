package com.example.libadmin.sec;

import com.example.libadmin.domain.User;
import com.example.libadmin.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRep;

    public UserDetailsServiceImpl(UserRepository userRep) {
        this.userRep = userRep;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRep.findByEmail(email);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        return user.get();
    }
}
