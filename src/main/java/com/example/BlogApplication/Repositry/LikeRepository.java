package com.example.BlogApplication.Repositry;

import com.example.BlogApplication.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long id);
    boolean existsByUserIdAndPostId(Long userId, Long postId);

}