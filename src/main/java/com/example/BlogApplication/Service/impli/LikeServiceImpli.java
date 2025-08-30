package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.LikeRequestDto;
import com.example.BlogApplication.Dto.LikeResponseDto;
import com.example.BlogApplication.Entity.Like;
import com.example.BlogApplication.Entity.Post;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Repositry.LikeRepository;
import com.example.BlogApplication.Repositry.PostRepository;
import com.example.BlogApplication.Repositry.UserRepository;
import com.example.BlogApplication.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpli implements LikeService {

    private final LikeRepository likeRepository;
    private  final PostRepository postRepository;
    private final UserRepository userRepository;

    private LikeResponseDto mapToDto(Like like)
    {
        LikeResponseDto likeResponseDto=new LikeResponseDto();
        likeResponseDto.setId(like.getId());
        likeResponseDto.setUsername(like.getUser().getUsername());
        likeResponseDto.setPostId(like.getPost().getId());
        likeResponseDto.setCreatedAt(like.getCreatedAt());

        return  likeResponseDto;
    }

    @Override
    public LikeResponseDto addLike(LikeRequestDto dto) {
        if (likeRepository.existsByUserIdAndPostId(dto.getUserId(), dto.getPostId()))
        {
            throw new RuntimeException("User Already Liked this Post");
        }
        User user=userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User Not Found"));

        Post post=postRepository.findById(dto.getPostId()).orElseThrow(()->new RuntimeException("Post Not Found"));

        Like like= Like.builder()
                .user(user)
                .post(post)
                .build();

        return mapToDto(likeRepository.save(like));

    }

    @Override
    public List<LikeResponseDto> getLikesByPostId(Long postId) {
        return likeRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
