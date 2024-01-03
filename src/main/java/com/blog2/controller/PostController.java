package com.blog2.controller;

import com.blog2.payload.PostDto;
import com.blog2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postss")
public class PostController {
    @Autowired
    private PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
return new ResponseEntity<>("post is deleted ",HttpStatus.OK);
    }

    //read data
    //http://localhost:8080/api/postss?pageNo=0&pageSize=4&sortBy=id&sortDir=desc
    @GetMapping
    public ResponseEntity<List<PostDto>> getALlPosts(
            @RequestParam(name = "pageNo", defaultValue = "0",required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "3", required = false)  int pageSize,
            @RequestParam(name = "sortBy" ,defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        List<PostDto> allPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);

    }
    //for update
    //http://localhost:8080/api/postss
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<PostDto> updatePosts(
            @RequestParam("postId") long postId,
            @RequestBody PostDto postDto
    ){
        PostDto dto = postService.updatePosts(postId, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
