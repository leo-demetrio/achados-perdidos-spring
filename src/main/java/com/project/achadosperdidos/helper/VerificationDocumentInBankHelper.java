package com.project.achadosperdidos.helper;

import com.project.achadosperdidos.domain.ObjectInput;
import com.project.achadosperdidos.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationDocumentInBankHelper {
    public final DocumentRepository documentRepository;
    public ObjectInput verifyDocumentInBank(String numberDocument){
        return documentRepository.findByNumberDocument(numberDocument);
    }
}
