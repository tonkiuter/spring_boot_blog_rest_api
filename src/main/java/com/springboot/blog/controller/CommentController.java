package com.springboot.blog.controller;


import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/")

public class CommentController {

    @Inject
    private CommentService commentService;


    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") Long id, @RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(commentService.createComment(id, commentDTO), HttpStatus.CREATED);

    }

}
