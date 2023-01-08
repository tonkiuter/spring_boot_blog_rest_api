package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

import static com.springboot.blog.utils.AppConstants.*;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    //the interface
    @Inject
    private PostService postService;

//    public PostController(PostService postService) {
//        this.postService = postService;
//    }

    //Create blog post rest api
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //getAll Posts rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir
    ){

        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get post by Id rest api
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //update post by Id rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") Long id){
       PostDTO updatePost = postService.updatePost(postDTO, id);

       return ResponseEntity.ok(updatePost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);

        return new ResponseEntity<String>( "Post with ID: " + id + " deleted", HttpStatus.OK);
    }
}
