package com.springboot.blog;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PostServiceTest {

    private PostService postService;
    private PostRepository postRepository;

    @Before
    public void setup() {
        postRepository = mock(PostRepository.class);
//        postService = new PostService(postRepository);
    }

    @Test
    public void testGetAllPosts() {
        // Mock data
        List<Post> mockPosts = new ArrayList<>();

        Set<Comment> comments = new HashSet<>();

        Comment comment = new Comment(23L, "okaru", "aklivmairkoo@gmail.com", "I love Runapur!",
                new Post(1L, "Test Post 1", "This is a test post.", "18+", null) );

        comments.add(comment);

//        Post newPost = new Post(22L, "Black Lagoon", "Anime Serie", "18+", null);
        mockPosts.add(new Post(1L, "Test Post 1", "This is a test post.", "18+", comments));
        mockPosts.add(new Post(2L, "Test Post 2", "This is another test post.", "18+", comments));
        mockPosts.add(new Post(3L, "Test Post 3", "This is yet another test post.", "18+", comments));
        Page<Post> mockPage = new PageImpl<>(mockPosts);

        // Mock postRepository's findAll method
        when(postRepository.findAll(PageRequest.of(0, 10, Sort.by("title").ascending()))).thenReturn(mockPage);

        // Call getAllPosts method
        PostResponse postResponse = postService.getAllPosts(0, 10, "title", "asc");

        // Assert expected results
        assertEquals(3, postResponse.getContent().size());
        assertEquals(0, postResponse.getPageNo());
        assertEquals(10, postResponse.getPageSize());
        assertEquals(3, postResponse.getTotalElements());
        assertEquals(1, postResponse.getTotalPages());
        assertEquals(true, postResponse.isLast());
    }
}
