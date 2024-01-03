package com.blog2.controller;

import com.blog2.payload.CommentDto;
import com.blog2.service.CommentService;
import com.blog2.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;
    private PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    //for create
    @PostMapping
    public ResponseEntity<CommentDto> createdComment(
            @RequestParam("postId") long postId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
    // for Delete
    //delete post , comment is automatically delete
   // http://localhost:8080/api/comments/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostAndcomment(@PathVariable long id){
        commentService.deletePostAndComment(id);
        return new ResponseEntity<>("Comment is delted!!", HttpStatus.OK);
    }
    //For read
  //http://localhost:8080/api/comments/6
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getComment(
            @PathVariable long postId
    ){
        List<CommentDto> dtos = commentService.getComment(postId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    //for updating comment
    //http://localhost:8080/api/comments?postId=6
    @PutMapping
    public ResponseEntity<CommentDto> updateComments(
            @RequestParam("postId") long postId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
