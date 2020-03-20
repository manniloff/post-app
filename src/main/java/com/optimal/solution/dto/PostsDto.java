package com.optimal.solution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsDto {
    private int id;
    private String title;
    private String body;
    private Date createDate;
    private String author;
    private String categoriesName;
    private Set<CommentsDto> comment;
}
