package com.example.BlogApplication.Repositry;

import com.example.BlogApplication.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}