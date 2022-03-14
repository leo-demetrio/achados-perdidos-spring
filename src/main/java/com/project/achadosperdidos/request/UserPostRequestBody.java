package com.project.achadosperdidos.request;


import lombok.Data;

@Data
public class UserPostRequestBody {
    private String email;
    private String password;
}
