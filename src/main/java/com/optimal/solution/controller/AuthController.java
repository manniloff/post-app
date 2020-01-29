package com.optimal.solution.controller;

import com.optimal.solution.auth.model.AuthRequest;
import com.optimal.solution.auth.model.AuthResponse;
import com.optimal.solution.auth.service.LoginDetailsService;
import com.optimal.solution.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final LoginDetailsService loginDetailsService;
    private final JwtUtil jwtUtil;


    @PostMapping(value = {"", "/"}, produces = "application/json")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect login or password", e);
        }

        final UserDetails loginDetails = loginDetailsService
                .loadUserByUsername(authRequest.getLogin());

        final String jwt = jwtUtil.generateToken(loginDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
