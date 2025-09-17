package com.example.BlogApplication.Repositry;

import com.example.BlogApplication.Entity.Like;
import com.example.BlogApplication.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long id);
    Optional<Like> findByUserIdAndPostId(Long userId,Long postId);
    int countByPost(Post post);
}