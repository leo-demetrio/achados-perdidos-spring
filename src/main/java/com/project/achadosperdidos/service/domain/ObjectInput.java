package com.project.achadosperdidos.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "tb_document")
public class ObjectInput {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String numberDocument;
    private String situation;
    private UUID userId;

}
