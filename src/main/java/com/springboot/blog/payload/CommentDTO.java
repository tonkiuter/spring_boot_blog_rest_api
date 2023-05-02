package com.springboot.blog.payload;


import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class CommentDTO {
    private Long id;

    //name should not be null nor empty
    @NotEmpty(message = "Name should not be null nor empty :3")
    private String name;

    @NotEmpty(message = "email should not be null nor empty!!")
    @Email
    private String email;

    @NotEmpty(message = "Body should not be empty!!")
    @Size(min = 10, message = "Comment must be at least 10 characters :)")
    private String body;
}
