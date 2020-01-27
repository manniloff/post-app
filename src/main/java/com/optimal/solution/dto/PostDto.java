package com.optimal.solution.dto;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PostDto {
    private String title;
    private String body;
    private Date createDate;
    private Date updateDate;
    private int userId;
    private Set<Integer> categoriesId;
    private Set<Integer> commentsId;
}
