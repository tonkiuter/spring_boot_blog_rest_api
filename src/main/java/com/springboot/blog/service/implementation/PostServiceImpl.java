package com.springboot.blog.service.implementation;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Inject
    private PostRepository postRepository;

    //     Remember to uncomment
    /*
    public PostServiceImpl(PostRepository postRepository) {
            this.postRepository = postRepository;
        }
     */

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //convert DTO into Entity
        Post post = mapToEntity(postDTO);

        Post newPost =  postRepository.save(post);

       //convert entity to Dto
        PostDTO postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return null;
    }

    //Convert Entity into DTO
    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();

        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setDescription(post.getDescription());

        return postDTO;
    }

    //converted DTO to Entity
    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        return  post;
    }
}
