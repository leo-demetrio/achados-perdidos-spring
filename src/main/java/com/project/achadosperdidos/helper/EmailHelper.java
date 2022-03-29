package com.project.achadosperdidos.helper;

import com.project.achadosperdidos.domain.Email;
import com.project.achadosperdidos.domain.User;
import com.project.achadosperdidos.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailHelper {
    private final EmailService emailService;

    public void sentEmailUserRegister(User user){
        Email email = Email.builder()
                .emailTo(user.getEmail())
                .subject("Usuário Cadastrado com sucesso")
                .text("Obrigado por se cadastrar")
                .build();
        emailService.sendEmail(email);
    }
    public void sentEmailDocumentRegister(User user){
        Email email = Email.builder()
                .emailTo(user.getEmail())
                .subject("Documento Cadastrado com sucesso")
                .text("Obrigado por cadastrar seu documento")
                .build();
        emailService.sendEmail(email);
    }
}
