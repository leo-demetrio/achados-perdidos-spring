package com.project.achadosperdidos.service;

import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.service.domain.User;
import com.project.achadosperdidos.helper.EmailHelper;
import com.project.achadosperdidos.helper.VerificationDocumentInBankHelper;
import com.project.achadosperdidos.repository.ObjectInputRepository;
import com.project.achadosperdidos.repository.UserRepository;
import com.project.achadosperdidos.request.DocumentPostRequestBody;
import com.project.achadosperdidos.request.DocumentPutRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class ObjectInputService {

    private final ObjectInputRepository objectInputRepository;
    private final EmailHelper emailHelper;
    private final UserRepository userRepository;
    private final VerificationDocumentInBankHelper verificationDocumentInBank;


    public List<ObjectInput> listAll(){
        return objectInputRepository.findAll();
    }
    public ObjectInput findByIdOrThrowsBadRequestException(UUID id){
            return objectInputRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Document not found"));
    }
    public ObjectInput save(DocumentPostRequestBody documentPostRequestBody) {
        ObjectInput objectInput = simpleBuilderDocument(documentPostRequestBody);
        ObjectInput objectInputVerifiedInBank = objectInputRepository.findByNumberDocument(objectInput.getNumberDocument());

        if(objectInputVerifiedInBank == null) return registerDocument(objectInput);

        if(objectInput.getSituation().equals(objectInputVerifiedInBank.getSituation())) return objectInput;

        return registerDocumentFound(objectInput, objectInputVerifiedInBank);
    }
    public void delete(UUID id){
        objectInputRepository.delete(findByIdOrThrowsBadRequestException(id));
    }
    public ObjectInput replaceOrThrowsBadRequestException(DocumentPutRequestBody documentPutRequestBody){
        ObjectInput objectInputBank = findByIdOrThrowsBadRequestException(documentPutRequestBody.getId());
        objectInputBank.setNumberDocument(documentPutRequestBody.getNumberDocument());
        return objectInputRepository.save(objectInputBank);
    }

    public List<ObjectInput> findDocumentsByIdOrThrowsBadRequestException(UUID id) {
        return objectInputRepository.findByUserId(id);
    }
    private ObjectInput registerDocument(ObjectInput objectInput){
        log.info(objectInput + "Register document");
        ObjectInput objectInputBank = objectInputRepository.save(objectInput);
        User user = userRepository.getById(objectInput.getUserId());
        emailHelper.sentEmailDocumentRegister(user);
        return objectInputBank;
    }
    private ObjectInput registerDocumentFound(ObjectInput objectInput, ObjectInput objectInputVerifiedInBank){
        User user = userRepository.getById(objectInput.getUserId());
        User userVerifiedBank = userRepository.getById(objectInputVerifiedInBank.getUserId());
        ObjectInput objectInputBank = objectInputRepository.save(objectInput);
        emailHelper.sentEmailDocumentFoundBank(user,userVerifiedBank);
        return objectInputBank;
    }
    private ObjectInput simpleBuilderDocument(DocumentPostRequestBody documentPostRequestBody){
        return ObjectInput.builder()
                .numberDocument(documentPostRequestBody.getNumberDocument())
                .situation(documentPostRequestBody.getSituation())
//                .userId(documentPostRequestBody.getUserId())
                //.userId(1)
                .build();
    }
}
