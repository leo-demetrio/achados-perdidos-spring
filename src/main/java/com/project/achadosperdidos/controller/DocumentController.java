package com.project.achadosperdidos.controller;

import com.project.achadosperdidos.domain.Document;
import com.project.achadosperdidos.request.DocumentPutRequestBody;
import com.project.achadosperdidos.service.DocumentService;
import com.project.achadosperdidos.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("documents")
@Log4j2
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DateUtil dateUtil;

    @GetMapping
    public ResponseEntity<List<Document>> listAll(){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(documentService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Document> listOne(@PathVariable Long id){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(documentService.findByIdOrThrowsBadRequestException(id), HttpStatus.OK);
    }
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<Document>> listAllOfUser(@PathVariable Long id){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(documentService.findDocumentsByIdOrThrowsBadRequestException(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Document> save(@RequestBody Document document){
        return new ResponseEntity<>(documentService.save(document),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody DocumentPutRequestBody documentPutRequestBody){
        log.info(dateUtil.formatLocalDateTimeTiDatabaseStyle(LocalDateTime.now()));
        log.info(documentService.replaceOrThrowsBadRequestException(documentPutRequestBody));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
