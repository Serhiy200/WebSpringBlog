package com.MyBlog.blog.repo;

import com.MyBlog.blog.models.Post;
import org.springframework.data.repository.CrudRepository;



public interface PostRepository extends CrudRepository<Post, Long> {
}
