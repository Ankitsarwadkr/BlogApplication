package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.PostRequestDto;
import com.example.BlogApplication.Dto.PostResponseDto;
import com.example.BlogApplication.Entity.Category;
import com.example.BlogApplication.Entity.Post;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
import com.example.BlogApplication.Repositry.CategoryRepository;
import com.example.BlogApplication.Repositry.LikeRepository;
import com.example.BlogApplication.Repositry.PostRepository;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Security.OpenAiClientService;
import com.example.BlogApplication.Service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpli implements PostService {



    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CategoryRepository categoryRepository;
    private final OpenAiClientService openAiClientService;

    private PostResponseDto mapToDto(Post post)
    {
        PostResponseDto dto = new PostResponseDto();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorname(post.getUser().getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLikeCount(likeRepository.countByPost(post));
        dto.setCategory(post.getCategory().getName());
        log.info("Mapped Post entity to DTO: {}", dto);

        return dto;
    }

    @Override
    public PostResponseDto createPost(PostRequestDto dto, User loggedInUser) {
        log.info("Creating post with title: '{}' for user: {}", dto.getTitle(), loggedInUser.getUsername());

        String predictedCategory=openAiClientService.ClassifyCategory(dto.getTitle()+ " "+dto.getContent());
        log.info("OpenAI suggested category: {}", predictedCategory);

        Category category=categoryRepository.findByName(predictedCategory).orElseGet(()->categoryRepository.findByName("Uncategorized")
                .orElseThrow(()->new ResourceNotFoundException("Default category not found")));


        Post post1=Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(loggedInUser)
                .category(category)
                .build();
      Post postsaved=  postRepository.save(post1);
        log.info("Post saved with ID: {}", postsaved.getId());
        return mapToDto(postsaved);
    }

    @Transactional
    @Override
    public List<PostResponseDto> getAllPost() {
        return postRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostResponseDto getPostByID(Long id)  {
       return postRepository.findById(id).map(this::mapToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Post With id "+id+" not found"));
    }


    @Override
    public void deletePost(Long id, User user)
    {
        log.info("Deleting post with ID: {} requested by user: {}", id, user.getUsername());
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post with Id "+id+" not found"));

        if(!post.getUser().getId().equals(user.getId()))
        {
            throw  new AccessDeniedException("You are not Authorized to delete this post");
        }
        postRepository.delete(post);
    }
}
