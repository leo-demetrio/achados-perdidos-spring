package com.project.achadosperdidos.service;


import com.project.achadosperdidos.domain.User;
import com.project.achadosperdidos.repository.UserRepository;
import com.project.achadosperdidos.request.UserPostRequestBody;
import com.project.achadosperdidos.request.UserPutRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    public List<User> listAll(){
        return userRepository.findAll();
    }
    public User findByIdOrThrowsBadRequestException(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Document not found"));
    }

    public User save(UserPostRequestBody userRequestBody) {
        User user = User.builder().email(userRequestBody.getEmail()).password(userRequestBody.getPassword()).build();
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(findByIdOrThrowsBadRequestException(id));
    }

    public User updateOrThrowsBadRequestException(UserPutRequestBody userPutRequestBody) {
        User userBank = findByIdOrThrowsBadRequestException(userPutRequestBody.getId());
        User userForSave = User.builder()
                .id(userBank.getId())
                .email(userPutRequestBody.getEmail())
                .password(userPutRequestBody.getPassword())
                .build();
        return userRepository.save(userForSave);
    }
}
