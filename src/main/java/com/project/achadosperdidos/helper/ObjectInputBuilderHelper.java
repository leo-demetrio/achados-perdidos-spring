package com.project.achadosperdidos.helper;

import com.project.achadosperdidos.request.ObjectInputPostRequestBody;
import com.project.achadosperdidos.service.domain.ObjectInput;

public class ObjectInputBuilderHelper {

    public static ObjectInput buildObjectInput(ObjectInputPostRequestBody objectInputPostRequestBody) {
        return ObjectInput.builder()
                .numberDocument(objectInputPostRequestBody.getNumberDocument())
                .situation(objectInputPostRequestBody.getSituation())
                .userId(objectInputPostRequestBody.getUserId())
                .build();
    }
}
