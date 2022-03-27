package com.project.achadosperdidos.service;

import com.project.achadosperdidos.domain.Document;
import com.project.achadosperdidos.repository.DocumentRepository;
import com.project.achadosperdidos.request.DocumentPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.print.Doc;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;


    public List<Document> listAll(){
        return documentRepository.findAll();
    }
    public Document findByIdOrThrowsBadRequestException(Long id){
            return documentRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Document not found"));
    }

    public Document save(Document document) {
        return documentRepository.save(document);
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
}
