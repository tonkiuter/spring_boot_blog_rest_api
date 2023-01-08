package com.springboot.blog.payload;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    private Long Id;


    //title should no be empty nor null
    //title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post Title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;

}
