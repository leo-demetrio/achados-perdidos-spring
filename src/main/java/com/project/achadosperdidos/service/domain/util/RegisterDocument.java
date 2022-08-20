package com.project.achadosperdidos.service.domain.util;


import com.project.achadosperdidos.helper.EmailHelper;
import com.project.achadosperdidos.request.ObjectInputPostRequestBody;
import com.project.achadosperdidos.service.ObjectInputService;
import com.project.achadosperdidos.service.domain.User;
import org.springframework.stereotype.Component;

import static com.project.achadosperdidos.helper.ObjectInputBuilderHelper.buildObjectInput;

@Component
public class RegisterDocument {

    private final EmailHelper emailHelper;
    private final ObjectInputService objectInputService;

    public RegisterDocument(EmailHelper emailHelper, ObjectInputService objectInputService) {
        this.emailHelper = emailHelper;
        this.objectInputService = objectInputService;
    }


    public void registerDocument(User userOwnerDocument, ObjectInputPostRequestBody objectInputPostRequestBody){
        emailHelper.sentEmailDocumentRegister(userOwnerDocument);
        objectInputService.save(buildObjectInput(objectInputPostRequestBody));
    }
}
