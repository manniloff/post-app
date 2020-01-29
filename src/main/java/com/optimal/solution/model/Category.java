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

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnoreProperties("categories")
    private Set<Post> posts = new HashSet<>();
   /* @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinTable(name = "categories_posts",
            joinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "post_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    @JsonIgnoreProperties("categories")
    private Set<Post> posts = new HashSet<>();*/
}
