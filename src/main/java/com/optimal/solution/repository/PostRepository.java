package com.optimal.solution.repository;

import com.optimal.solution.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Post p where p.id = :id")
    void deleteByIds(@Param("id") int id);

    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findAllByUser(@Param("userId") int userId);

    @Query("select p from Post p where p.id = :id and p.user.id = :userId")
    Optional<Post> findByIdAndUser(@Param("id") int id, @Param("userId") int userId);
}
