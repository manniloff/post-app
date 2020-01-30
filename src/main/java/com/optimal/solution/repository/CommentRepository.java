package com.optimal.solution.repository;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;
import com.optimal.solution.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select new  com.optimal.solution.dto.CommentDto(c.id, c.text, c.postedDate, c.post.id, c.user.id) from Comment c")
    List<CommentDto> findAllDto();

    @Query("select new  com.optimal.solution.dto.CommentDto(c.id, c.text, c.postedDate, c.post.id, c.user.id) from Comment c where c.user.id = :userId")
    List<CommentDto> findAllByUser(@Param("userId") int userId);

    @Query("select c from Comment c where c.id = :id and c.user.id = :userId")
    Optional<Comment> findByIdAndUser(@Param("id") int id, @Param("userId") int userId);
}
