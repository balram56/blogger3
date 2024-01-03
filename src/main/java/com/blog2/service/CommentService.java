package com.blog2.service;

import com.blog2.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto comentDto);

    void deletePostAndComment(long id);

    List<CommentDto> getComment(long postId);

    CommentDto updateComment(long postId, CommentDto commentDto);
}
