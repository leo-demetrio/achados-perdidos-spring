package com.project.achadosperdidos.service.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name= "tb_user")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    private String email;
    private String password;
}
