package com.project.achadosperdidos.request;


import lombok.Data;

@Data
public class UserPutRequestBody {
    private Long id;
    private String email;
    private String password;
}
