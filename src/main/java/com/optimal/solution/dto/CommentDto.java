package com.optimal.solution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentDto {
    private int id;
    private String text;
    private Date postedDate;
    private int postId;
    private int userId;
}
