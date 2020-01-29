package com.optimal.solution.repository;

import com.optimal.solution.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Post p where p.id = :id")
    void deleteByIds(@PathParam("id") int id);
}
