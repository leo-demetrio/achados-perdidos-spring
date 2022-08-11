package com.project.achadosperdidos.request;


import lombok.Data;

import java.util.UUID;

@Data
public class UserPutRequestBody {
    private UUID id;
    private String email;
    private String password;
}
