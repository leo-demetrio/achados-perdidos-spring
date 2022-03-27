package com.project.achadosperdidos.config;

import com.project.achadosperdidos.domain.Document;
import com.project.achadosperdidos.domain.User;
import com.project.achadosperdidos.repository.DocumentRepository;
import com.project.achadosperdidos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Override
    public void run(String... args) throws Exception{
        User u1 = new User(null, "usser1@gmail.com","123");
        User u2 = new User(null, "usser2@gmail.com","123");
        userRepository.saveAll(Arrays.asList(u1,u2));

        Document d1 = new Document(null, "11111", u1.getId());
        Document d2 = new Document(null, "11112", u1.getId());
        Document d3 = new Document(null, "22222", u2.getId());
        documentRepository.saveAll(Arrays.asList(d1,d2,d3));
    }

}
