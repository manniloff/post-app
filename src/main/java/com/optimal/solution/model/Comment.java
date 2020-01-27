package com.optimal.solution.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="text")
    private String text;

    @Column(name="posted_date")
    private Date postedDate;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
