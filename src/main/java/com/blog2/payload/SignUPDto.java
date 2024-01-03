package com.blog2.payload;

import lombok.Data;

@Data
public class SignUPDto {

    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
}
