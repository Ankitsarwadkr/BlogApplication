package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.PostRequestDto;
import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Entity.Post;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.PostRepository;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpli implements PostService {


    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private PostResponseDto mapToDto(Post post)
    {
        PostResponseDto dto = new PostResponseDto();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorname(post.getUser().getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLikeCount(post.getLikes()!=null ? post.getLikes().size():0);

        return dto;
    }

    @Override
    public PostResponseDto createPost(PostRequestDto dto) {
        User user1 =userRepository.findById(dto.getUserId())
                .orElseThrow(()->new RuntimeException("User Not Found"));

        Post post1=Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user1)
                .build();

        return mapToDto(postRepository.save(post1));
    }

    @Override
    public PostResponseDto getPostByID(Long id) {
       return postRepository.findById(id).map(this::mapToDto)
                .orElseThrow(()->new RuntimeException("Post Not Found"));
    }

    @Override
    public List<PostResponseDto> getAllPost() {
        return postRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
