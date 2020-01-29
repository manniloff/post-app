package com.optimal.solution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.ws.rs.core.Context;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"categories","user"})
@EqualsAndHashCode(exclude = {"categories","user"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_category_relation",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id",referencedColumnName = "id")}
    )
    @JsonIgnoreProperties("posts")
    Set<Category> categories  = new HashSet<>();

    /*@ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.REFRESH , CascadeType.MERGE})
    @JsonIgnoreProperties("posts")
    Set<Category> categories  = new HashSet<>();*/
}
