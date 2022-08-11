package com.project.achadosperdidos.request.controller;


import com.project.achadosperdidos.service.domain.ObjectInput;
import com.project.achadosperdidos.request.DocumentPostRequestBody;
import com.project.achadosperdidos.request.DocumentPutRequestBody;
import com.project.achadosperdidos.service.ObjectInputService;
import com.project.achadosperdidos.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("documents")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin(value = "*")
public class ObjectInputController {

    private final ObjectInputService documentService;
    private final DateUtil dateUtil;

    @GetMapping
    public ResponseEntity<List<ObjectInput>> listAll(){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(documentService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ObjectInput> listOne(@PathVariable UUID id){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(documentService.findByIdOrThrowsBadRequestException(id), HttpStatus.OK);
    }
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<ObjectInput>> listAllOfUser(@PathVariable UUID id){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(documentService.findDocumentsByIdOrThrowsBadRequestException(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ObjectInput> save(@RequestBody DocumentPostRequestBody documentPostRequestBody){
        log.info(documentPostRequestBody);
        return new ResponseEntity<>(documentService.save(documentPostRequestBody),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody DocumentPutRequestBody documentPutRequestBody){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        log.info(documentService.replaceOrThrowsBadRequestException(documentPutRequestBody));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
