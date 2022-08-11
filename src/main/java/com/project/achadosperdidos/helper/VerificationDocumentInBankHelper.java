package com.project.achadosperdidos.helper;

import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.repository.ObjectInputRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationDocumentInBankHelper {
    public final ObjectInputRepository objectInputRepository;
    public ObjectInput verifyDocumentInBank(String numberDocument){
        return objectInputRepository.findByNumberDocument(numberDocument);
    }
}
