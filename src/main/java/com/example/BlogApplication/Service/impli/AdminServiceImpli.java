package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Dto.UserResponseDto;
import com.example.BlogApplication.Repositry.CommentRepository;
import com.example.BlogApplication.Repositry.PostRepository;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AdminServiceImpli implements AdminService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()

                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getRole()
                )).collect(Collectors.toList());    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<PostResponseDto> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> new PostResponseDto(post.getId(),post.getTitle(),
                        post.getContent(),post.getUser().getUsername(),post.getCreatedAt(),
                        post.getUpdatedAt(),
                        post.getLikes().size())).collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
