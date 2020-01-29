package com.optimal.solution.auth;

import com.optimal.solution.model.User;
import com.optimal.solution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(userName);

        System.out.println(user.toString());
        user.orElseThrow(() -> new UsernameNotFoundException("Not found user with user name - " + userName ));

        return user.map(LoginDetails::new).get();
    }
}
