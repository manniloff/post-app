package com.optimal.solution.auth.service;

import com.optimal.solution.auth.model.LoginDetails;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found user with user name - " + userName));

        return user.map(LoginDetails::new).get();
    }
}
