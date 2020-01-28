package com.optimal.solution.repository;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select new  com.optimal.solution.dto.CommentDto(c.id, c.text, c.postedDate, c.post.id) from Comment c")
    List<CommentDto> findAllDto();
}
