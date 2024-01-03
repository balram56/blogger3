package com.blog2.service.impl;

import com.blog2.entity.Post;
import com.blog2.payload.PostDto;
import com.blog2.repository.PostRepository;
import com.blog2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savePost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setId(savePost.getId());
        dto.setTitle(savePost.getTitle());
        dto.setDescription(savePost.getDescription());
        dto.setContent(savePost.getContent());
        dto.setMessage("post is saved ");
        return dto;
    }

    @Override
    public void deletePost(long id) {
       Post post = postRepository.findById(id).orElseThrow(
                ()->new RuntimeException("post not found with id:"+id)
        );

        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
      // Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public PostDto updatePosts(long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("post is Not present with id :" + postId)
        );

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post savePost = postRepository.save(post);
       PostDto dto = mapToDto(savePost);
        return dto;
    }

    PostDto mapToDto(Post post){
PostDto dto = new PostDto();
dto.setId(post.getId());
dto.setTitle(post.getTitle());
dto.setDescription(post.getDescription());
dto.setContent(post.getContent());
return dto;
    }
}
