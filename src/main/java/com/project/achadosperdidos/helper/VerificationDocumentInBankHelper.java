package com.project.achadosperdidos.helper;

import com.project.achadosperdidos.domain.Document;
import com.project.achadosperdidos.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationDocumentInBankHelper {
    public final DocumentRepository documentRepository;
    public Document verifyDocumentInBank(String numberDocument){
        return documentRepository.findByNumberDocument(numberDocument);
    }
}
