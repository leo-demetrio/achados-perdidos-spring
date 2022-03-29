package com.project.achadosperdidos.service;

import com.project.achadosperdidos.domain.Document;
import com.project.achadosperdidos.domain.User;
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

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final EmailHelper emailHelper;
    private final UserRepository userRepository;
    private final VerificationDocumentInBankHelper verificationDocumentInBank;


    public List<Document> listAll(){
        return documentRepository.findAll();
    }
    public Document findByIdOrThrowsBadRequestException(Long id){
            return documentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Document not found"));
    }

    public Document save(DocumentPostRequestBody documentPostRequestBody) {
        Document document = simpleBuilderDocument(documentPostRequestBody);
        Document documentVerified =
                verificationDocumentInBank.verifyDocumentInBank(documentPostRequestBody.getNumberDocument());
        if(documentVerified == null){
            return registerDocument(documentPostRequestBody, document);
        }
        return registerDocumentFound(documentPostRequestBody,document,documentVerified);
    }
    public void delete(Long id){
        documentRepository.delete(findByIdOrThrowsBadRequestException(id));
    }
    public Document replaceOrThrowsBadRequestException(DocumentPutRequestBody documentPutRequestBody){
        Document documentBank = findByIdOrThrowsBadRequestException(documentPutRequestBody.getId());
        documentBank.setNumberDocument(documentPutRequestBody.getNumberDocument());
        return documentRepository.save(documentBank);
    }

    public List<Document> findDocumentsByIdOrThrowsBadRequestException(Long id) {
        return documentRepository.findByUserId(id);
    }
    private Document registerDocument(DocumentPostRequestBody documentPostRequestBody, Document document){
        Document documentBank = documentRepository.save(document);
        User user = userRepository.getById(documentPostRequestBody.getUserId());
        emailHelper.sentEmailDocumentRegister(user);
        return documentBank;
    }
    private Document registerDocumentFound(DocumentPostRequestBody documentPostRequestBody, Document document, Document documentVerified){
        Document documentBank = documentRepository.save(document);
        User user = userRepository.getById(documentPostRequestBody.getUserId());
        User userVerifiedBank = userRepository.getById(documentVerified.getUserId());
        emailHelper.sentEmailDocumentFoundBank(user,userVerifiedBank);
        return documentBank;
    }
    private Document simpleBuilderDocument(DocumentPostRequestBody documentPostRequestBody){
        return Document.builder()
                .numberDocument(documentPostRequestBody.getNumberDocument())
                .userId(documentPostRequestBody.getUserId())
                .build();
    }
}
