package com.project.achadosperdidos.request;

import lombok.Data;


@Data
public class DocumentPostRequestBody {
    private String numberDocument;
    private String situation;
    private Long userId;
}
