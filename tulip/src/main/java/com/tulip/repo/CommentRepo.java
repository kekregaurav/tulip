package com.tulip.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tulip.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long>{

}
