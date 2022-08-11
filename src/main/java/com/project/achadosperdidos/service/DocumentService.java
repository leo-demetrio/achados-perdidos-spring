package com.project.achadosperdidos.service;

import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.service.domain.User;
import com.project.achadosperdidos.helper.EmailHelper;
import com.project.achadosperdidos.helper.VerificationDocumentInBankHelper;
import com.project.achadosperdidos.repository.DocumentRepository;
import com.project.achadosperdidos.repository.UserRepository;
import com.project.achadosperdidos.request.DocumentPostRequestBody;
import com.project.achadosperdidos.request.DocumentPutRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final EmailHelper emailHelper;
    private final UserRepository userRepository;
    private final VerificationDocumentInBankHelper verificationDocumentInBank;


    public List<ObjectInput> listAll(){
        return documentRepository.findAll();
    }
    public ObjectInput findByIdOrThrowsBadRequestException(Long id){
            return documentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Document not found"));
    }
    public ObjectInput save(DocumentPostRequestBody documentPostRequestBody) {
        ObjectInput objectInput = simpleBuilderDocument(documentPostRequestBody);
        ObjectInput objectInputVerifiedInBank = documentRepository.findByNumberDocument(objectInput.getNumberDocument());

        if(objectInputVerifiedInBank == null) return registerDocument(objectInput);

        if(objectInput.getSituation().equals(objectInputVerifiedInBank.getSituation())) return objectInput;

        return registerDocumentFound(objectInput, objectInputVerifiedInBank);
    }
    public void delete(Long id){
        documentRepository.delete(findByIdOrThrowsBadRequestException(id));
    }
    public ObjectInput replaceOrThrowsBadRequestException(DocumentPutRequestBody documentPutRequestBody){
        ObjectInput objectInputBank = findByIdOrThrowsBadRequestException(documentPutRequestBody.getId());
        objectInputBank.setNumberDocument(documentPutRequestBody.getNumberDocument());
        return documentRepository.save(objectInputBank);
    }

    public List<ObjectInput> findDocumentsByIdOrThrowsBadRequestException(Long id) {
        return documentRepository.findByUserId(id);
    }
    private ObjectInput registerDocument(ObjectInput objectInput){
        log.info(objectInput + "Register document");
        ObjectInput objectInputBank = documentRepository.save(objectInput);
        User user = userRepository.getById(objectInput.getUserId());
        emailHelper.sentEmailDocumentRegister(user);
        return objectInputBank;
    }
    private ObjectInput registerDocumentFound(ObjectInput objectInput, ObjectInput objectInputVerifiedInBank){
        User user = userRepository.getById(objectInput.getUserId());
        User userVerifiedBank = userRepository.getById(objectInputVerifiedInBank.getUserId());
        ObjectInput objectInputBank = documentRepository.save(objectInput);
        emailHelper.sentEmailDocumentFoundBank(user,userVerifiedBank);
        return objectInputBank;
    }
    private ObjectInput simpleBuilderDocument(DocumentPostRequestBody documentPostRequestBody){
        return ObjectInput.builder()
                .numberDocument(documentPostRequestBody.getNumberDocument())
                .situation(documentPostRequestBody.getSituation())
//                .userId(documentPostRequestBody.getUserId())
                .userId(1l)
                .build();
    }
}
