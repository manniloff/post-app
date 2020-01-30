package com.optimal.solution.auth.filter;

import com.optimal.solution.auth.service.LoginDetailsService;
import com.optimal.solution.auth.util.JwtUtil;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.UserRepository;
import com.optimal.solution.util.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    public static int id;
    public static Roles role;
    private final LoginDetailsService loginDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = httpServletRequest.getHeader("Authorization");

        String login = null;
        String jwt = null;

        if (authorization != null && authorization.startsWith(("Bearer "))) {
            jwt = authorization.substring(7);
            login = jwtUtil.extractUsername(jwt);
        }

        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails loginDetails = this.loginDetailsService.loadUserByUsername(login);
            if (jwtUtil.validateToken(jwt, loginDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        loginDetails, null, loginDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        Optional<User> user = userRepository.findByLogin(login);

        if (user.isPresent()) {
            id = user.get().getId();
            role = user.get().getRoles();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
