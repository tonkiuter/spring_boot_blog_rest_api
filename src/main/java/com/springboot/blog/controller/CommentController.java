package com.springboot.blog.controller;


import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/")

public class CommentController {

    @Inject
    private CommentService commentService;

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") Long id, @RequestBody CommentDTO commentDTO){

        return new ResponseEntity<>(commentService.createComment(id, commentDTO), HttpStatus.CREATED);

    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId( @PathVariable(name = "postId") Long postId){

        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("posts/name/{nameId}/comments")
    public List<CommentDTO> getCommentsByName( @PathVariable(name = "nameId") String nameId){

        return commentService.getCommentsByName(nameId);
    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId){

        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @RequestBody CommentDTO commentDTO){

        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,@PathVariable(value = "commentId") Long commentId){
        commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("Comment deleted successfully!", HttpStatus.OK);
    }

}
