package com.tulip.service;

import com.tulip.model.Post;

public interface CommentService {

	public void saveComment(Post post, String username, String content);
}
