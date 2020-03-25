package com.optimal.solution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private int id;
    private String title;
    private String body;
    private Date createDate;
    private Date updateDate;
    private int userId;
    private Set<Integer> categoriesId;
    private Set<Integer> commentsId;
}
