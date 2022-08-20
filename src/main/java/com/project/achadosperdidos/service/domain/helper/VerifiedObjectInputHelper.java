package com.project.achadosperdidos.service.domain.helper;

import com.project.achadosperdidos.helper.EmailHelper;
import com.project.achadosperdidos.repository.ObjectInputRepository;
import com.project.achadosperdidos.repository.UserRepository;
import com.project.achadosperdidos.request.ObjectInputPostRequestBody;
import com.project.achadosperdidos.service.ObjectInputService;
import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.service.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.achadosperdidos.helper.ObjectInputBuilderHelper.buildObjectInput;

@RequiredArgsConstructor
@Service
public class VerifiedObjectInputHelper {
    private final ObjectInputRepository objectInputRepository;
    private final ObjectInputService objectInputService;
    private final UserRepository userRepository;
    private final EmailHelper emailHelper;

    public Object verifiedObjectInputInDataBase(ObjectInputPostRequestBody objectInputPostRequestBody){
        ObjectInput objectInputVerifiedInBank = objectInputRepository.findByNumberDocument(objectInputPostRequestBody.getNumberDocument());

        if(objectInputVerifiedInBank == null) {
            emailHelper.sentEmailDocumentRegister(userRepository.getById(objectInputVerifiedInBank.getUserId()));
            return objectInputService.save(buildObjectInput(objectInputPostRequestBody));
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
