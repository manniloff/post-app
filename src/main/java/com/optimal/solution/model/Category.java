package com.optimal.solution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "posts")
@EqualsAndHashCode(exclude = "posts")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "categories",fetch = FetchType.LAZY)
    @JsonIgnoreProperties("categories")
    private Set<Post> posts = new HashSet<>();
}
