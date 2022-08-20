package com.project.achadosperdidos.service.domain.helper;

import com.project.achadosperdidos.helper.EmailHelper;
import com.project.achadosperdidos.repository.ObjectInputRepository;
import com.project.achadosperdidos.repository.UserRepository;
import com.project.achadosperdidos.request.ObjectInputPostRequestBody;
import com.project.achadosperdidos.service.ObjectInputService;
import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.service.domain.User;
import com.project.achadosperdidos.service.domain.util.RegisterDocument;
import org.springframework.stereotype.Service;

import static com.project.achadosperdidos.helper.ObjectInputBuilderHelper.buildObjectInput;


@Service
public class VerifiedObjectInputHelper {
    private final ObjectInputRepository objectInputRepository;
    private final ObjectInputService objectInputService;
    private final UserRepository userRepository;
    private final EmailHelper emailHelper;
    private final RegisterDocument registerDocument;

    public VerifiedObjectInputHelper(
            ObjectInputRepository objectInputRepository,
            ObjectInputService objectInputService,
            UserRepository userRepository,
            EmailHelper emailHelper,
            RegisterDocument registerDocument)
    {

        this.objectInputRepository = objectInputRepository;
        this.objectInputService = objectInputService;
        this.userRepository = userRepository;
        this.emailHelper = emailHelper;
        this.registerDocument = registerDocument;
    }

    public Object verifiedObjectInputInDataBase(ObjectInputPostRequestBody objectInputPostRequestBody){
        ObjectInput objectInputVerifiedInBank = objectInputRepository.findByNumberDocument(objectInputPostRequestBody.getNumberDocument());
        User userOwnerDocument = userRepository.getById(objectInputPostRequestBody.getUserId());
        System.out.println(userOwnerDocument);
        if(objectInputVerifiedInBank == null) {
            registerDocument.registerDocument(userOwnerDocument,objectInputPostRequestBody);
            return objectInputPostRequestBody;
        }
        if(objectInputPostRequestBody.getSituation().equals(objectInputVerifiedInBank.getSituation())) return objectInputPostRequestBody;

        return registerDocumentFound(objectInputPostRequestBody, objectInputVerifiedInBank);
    }
    private ObjectInput registerDocumentFound(ObjectInputPostRequestBody objectInputPostRequestBody, ObjectInput objectInputVerifiedInBank){
        User user = userRepository.getById(objectInputPostRequestBody.getUserId());
        User userVerifiedBank = userRepository.getById(objectInputVerifiedInBank.getUserId());
        ObjectInput objectInputBank = objectInputRepository.save(buildObjectInput(objectInputPostRequestBody));
        emailHelper.sentEmailDocumentFoundBank(user,userVerifiedBank);
        return objectInputBank;
    }
}
