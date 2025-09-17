package com.example.BlogApplication.Service.impli;

import com.example.BlogApplication.Dto.LikeRequestDto;
import com.example.BlogApplication.Dto.LikeResponseDto;
import com.example.BlogApplication.Entity.Like;
import com.example.BlogApplication.Entity.Post;
import com.example.BlogApplication.Entity.User;
import com.example.BlogApplication.Exception.ResourceNotFoundException;
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

    private LikeResponseDto mapToDto(Post post,boolean liked)
    {
        LikeResponseDto likeResponseDto=new LikeResponseDto();

        likeResponseDto.setPostId(post.getId());
        likeResponseDto.setLiked(liked);
        likeResponseDto.setTotalLikes(likeRepository.countByPost(post));
        return  likeResponseDto;
    }

    @Override
    public LikeResponseDto toggleLike(LikeRequestDto likeRequestDto, User loggedUser) {

        Post post=postRepository.findById(likeRequestDto.getPostId()).orElseThrow(()->new ResourceNotFoundException("Post with id: " +likeRequestDto.getPostId()+" Not Found "));
        boolean liked;
        var existingLike=likeRepository.findByUserIdAndPostId(loggedUser.getId(),post.getId());
        if(existingLike.isPresent())
        {
            likeRepository.delete(existingLike.get());
            liked=false;
        }
        else
        {
            Like like= Like.builder()
                    .user(loggedUser)
                    .post(post)
                    .build();
            likeRepository.save(like);
            liked=true;
        }
        return mapToDto(post,liked);
    }
}
