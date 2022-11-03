package com.springboot.blog.service.implementation;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private  PostRepository postRepository;

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);

        //retrieve post entity by Id
        Post post = postRepository.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post", "id", postId));

        //set post to comment entity
        comment.setPost(post);

        //save comment entity to database
        Comment newComment = commentRepository.save(comment);

        //Remember we always return a DTO to the client!
        return mapToDTO(newComment);
    }


    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(commentDTO.getBody());

        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = new Comment();

        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        return  comment;
    }
}
