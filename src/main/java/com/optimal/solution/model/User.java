package com.optimal.solution.model;

import com.optimal.solution.util.Roles;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String login;

    private String password;

    private boolean active;

    private Roles roles;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;
}
