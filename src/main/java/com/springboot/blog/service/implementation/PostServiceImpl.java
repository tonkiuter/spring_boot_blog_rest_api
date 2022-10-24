package com.springboot.blog.service.implementation;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
        //Here we recover Post not PostDTO's because info we send is that, not the payload itself
        //Post is our Entity
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id ));

        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {

        //get post by id from database
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatePost = postRepository.save(post);

        return mapToDTO(updatePost);
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

        return post;
    }
}