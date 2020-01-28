package com.optimal.solution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optimal.solution.util.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "posts")
@EqualsAndHashCode(exclude = "posts")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private int id;

    private String login;

    private String password;

    private boolean active;

    private Roles roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private Set<Post> posts;
}
