package com.optimal.solution.repository;

import com.optimal.solution.dto.UserDto;
import com.optimal.solution.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select new com.optimal.solution.dto.UserDto( u.login, u.password, u.active, u.roles) from User u")
    List<UserDto> findAllUsers();

    @Query("select new com.optimal.solution.dto.UserDto( u.login, u.password, u.active, u.roles) from User u where u.id = :id")
    Optional<UserDto> findByIdUsers(@Param("id") int id);

    Optional<User> findByLogin(String login);
}
