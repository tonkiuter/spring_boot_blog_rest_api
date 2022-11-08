package com.springboot.blog.service.implementation;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Inject
    private PostRepository postRepository;

    @Inject
    private ModelMapper mapper;

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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //Sort by dynamically
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Create Paginable Instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        //Here we recover Post not PostDTO's because info we send is that, not the payload itself
        //Post is our Entity
        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
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

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", id));

        postRepository.delete(post);
    }

    //Convert Entity into DTO
    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = mapper.map(post, PostDTO.class);
//
//        PostDTO postDTO = new PostDTO();
//
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setContent(post.getContent());
//        postDTO.setDescription(post.getDescription());

        return postDTO;
    }

    //converted DTO to Entity
    private Post mapToEntity(PostDTO postDTO){
        Post post = mapper.map(postDTO, Post.class);

//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());

        return post;
    }
}