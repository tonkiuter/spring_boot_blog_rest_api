package com.springboot.blog.payload;


import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long Id;
    private String title;
    private String description;
    private String content;

    private Set<CommentDTO> comments;

}
