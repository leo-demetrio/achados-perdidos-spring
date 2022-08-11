package com.project.achadosperdidos.request;

import lombok.Data;

import java.util.UUID;


@Data
public class DocumentPutRequestBody {
    private UUID id;
    private String numberDocument;
}
