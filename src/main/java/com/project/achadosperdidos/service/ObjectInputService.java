package com.project.achadosperdidos.service;

import com.project.achadosperdidos.helper.EmailHelper;
import com.project.achadosperdidos.helper.VerificationDocumentInBankHelper;
import com.project.achadosperdidos.repository.ObjectInputRepository;
import com.project.achadosperdidos.repository.UserRepository;
import com.project.achadosperdidos.request.ObjectInputPutRequestBody;
import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.service.domain.User;
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
    public ObjectInput save(ObjectInput objectInput) {
        return objectInputRepository.save(objectInput);
    }
    public void delete(UUID id){
        objectInputRepository.delete(findByIdOrThrowsBadRequestException(id));
    }
    public ObjectInput replaceOrThrowsBadRequestException(ObjectInputPutRequestBody objectInputPutRequestBody){
        ObjectInput objectInputBank = findByIdOrThrowsBadRequestException(objectInputPutRequestBody.getId());
        objectInputBank.setNumberDocument(objectInputPutRequestBody.getNumberDocument());
        return objectInputRepository.save(objectInputBank);
    }

    public List<ObjectInput> findDocumentsByIdOrThrowsBadRequestException(UUID id) {
        return objectInputRepository.findByUserId(id);
    }
    private ObjectInput registerDocument(ObjectInput objectInput){
        log.info(objectInput + "Register document");
        ObjectInput objectInputBank = objectInputRepository.save(objectInput);
        log.info(objectInputBank + "bank document");
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

}
