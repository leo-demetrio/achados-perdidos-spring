package com.project.achadosperdidos.request;

import lombok.Data;

import java.util.UUID;


@Data
public class ObjectInputPostRequestBody {
    private String numberDocument;
    private String situation;
    private UUID userId;
}
