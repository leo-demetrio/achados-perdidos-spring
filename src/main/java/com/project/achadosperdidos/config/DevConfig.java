package com.project.achadosperdidos.config;

import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.service.domain.User;
import com.project.achadosperdidos.repository.ObjectInputRepository;
import com.project.achadosperdidos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.UUID;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ObjectInputRepository objectInputRepository;

    @Override
    public void run(String... args) throws Exception{
        User u1 = new User(null, "leohotmail", "leopoldocd@hotmail.com","123");
        User u2 = new User(null, "leogmail","leocdemetrio@gmail.com","123");
        userRepository.saveAll(Arrays.asList(u1,u2));

        ObjectInput d1 = new ObjectInput(null, "11111","lost", u1.getId());
        ObjectInput d2 = new ObjectInput(null, "11112","lost", u1.getId());
        ObjectInput d3 = new ObjectInput(null, "22222","found", u2.getId());
        objectInputRepository.saveAll(Arrays.asList(d1,d2,d3));
    }

}
