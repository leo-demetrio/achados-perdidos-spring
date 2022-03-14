package com.project.achadosperdidos.controller;


import com.project.achadosperdidos.domain.User;
import com.project.achadosperdidos.request.UserPostRequestBody;
import com.project.achadosperdidos.request.UserPutRequestBody;
import com.project.achadosperdidos.service.UserService;
import com.project.achadosperdidos.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DateUtil dateUtil;

    @GetMapping
    public ResponseEntity<List<User>> all(){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(userService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> listOne(@PathVariable Long id){
        return new ResponseEntity<>(userService.findByIdOrThrowsBadRequestException(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserPostRequestBody userRequestBody){
        return new ResponseEntity<>(userService.save(userRequestBody),HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserPutRequestBody userPutRequestBody){
        userService.updateOrThrowsBadRequestException(userPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
