package com.project.achadosperdidos.service.domain;

import com.project.achadosperdidos.enums.StatusEmailEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_email")
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID emailId;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "text")
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmailEnum statusEmail;

}
