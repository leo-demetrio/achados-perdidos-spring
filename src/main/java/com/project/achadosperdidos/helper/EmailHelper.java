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
    public void sentEmailDocumentFoundBank(User user, User userVerified){
        Email email = Email.builder()
                .emailTo(user.getEmail())
                .subject("Documento encontrado no banco")
                .text("O documento foi encontrado e pertence ao sr. " + userVerified.getName() + " entramos em contato para que possa ser devolvido")
                .build();
        Email emailVerified = Email.builder()
                .emailTo(userVerified.getEmail())
                .subject("Documento encontrado com sucesso")
                .text("Seu documento foi encontrado com sr. " + user.getName() + " entramos em contato para que possa ser devolvido")
                .build();
        emailService.sendEmail(email);
        emailService.sendEmail(emailVerified);
    }
}
