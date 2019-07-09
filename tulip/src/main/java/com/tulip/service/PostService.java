package com.tulip.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tulip.model.AppUser;
import com.tulip.model.Post;


public interface PostService {

	public Post savePost(AppUser appUser, HashMap<String, String> request , String postImageName);
	
	public List<Post> postList();
	
	public Post getPostById(Long id);
	
	public List<Post> findPostByUsername(String username);
	
	public Post deletePost(Post post);
	
	public String savePostImage(MultipartFile multipartFile, String fileName);
	
	
}
