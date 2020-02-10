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
@ToString(exclude = {"posts", "comments"})
@EqualsAndHashCode(exclude = {"posts", "comments"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private int id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    private boolean active;

    @Column(name = "role", nullable = false,
            columnDefinition = "ENUM('ADMIN', 'USER')")
    @Enumerated(value = EnumType.STRING)
    private Roles roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments;
}
