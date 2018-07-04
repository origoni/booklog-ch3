package com.bookonspring.booklog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Integer> {

    List<Comment> findByPostId(int postId); // ①
}