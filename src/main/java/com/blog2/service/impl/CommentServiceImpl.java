package com.blog2.service.impl;

import com.blog2.entity.Comment;
import com.blog2.entity.Post;
import com.blog2.payload.CommentDto;
import com.blog2.repository.CommentRepository;
import com.blog2.repository.PostRepository;
import com.blog2.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto comentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Post is not found with id :" + postId)
        );

       Comment comment = new Comment();
       comment.setName(comentDto.getName());
       comment.setEmail(comentDto.getEmail());
       comment.setBody(comentDto.getBody());
       comment.setPost(post);

        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = new CommentDto();
        dto.setId(saveComment.getId());
        dto.setName(saveComment.getName());
        dto.setBody(saveComment.getBody());
        dto.setEmail(saveComment.getEmail());
        return dto;
    }


    @Override
    public void deletePostAndComment(long id) {
        postRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Comment is not present with id !!" +id)
        );
       postRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> getComment(long postId) {
        List<Comment> comment = commentRepository.findByPostId(postId);
        List<CommentDto> dtos = comment.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Comment is not present with id :" + postId)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(saveComment);

        return dto;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}
