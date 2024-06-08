package com.app.appforo.controller.web;

import com.app.appforo.persistence.entity.Response;
import com.app.appforo.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/response")
public class ResponseController {
    @Autowired
    private ResponseService responseService;

    @GetMapping("/")
    public ResponseEntity<Page<Response>> findAll(Pageable pageable) {
        return ResponseEntity.ok(responseService.findAll(pageable));
    }

    @PostMapping("/")
    public ResponseEntity<Response> add(@RequestBody Response response) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseService.add(response));
    }

    @GetMapping("/{responseId}")
    public ResponseEntity<Response> findById(@PathVariable String responseId) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseService.findById(responseId));
    }

    @DeleteMapping("/{responseId}")
    public Map<String, String> delete(@PathVariable String responseId) {
        return responseService.delete(responseId);
    }

    @PutMapping("/{responseId}")
    public ResponseEntity<Response> update(@RequestBody Response response, @PathVariable String responseId) {
        return ResponseEntity
                .status(HttpStatus.MULTI_STATUS)
                .body(responseService.update(response, responseId));
    }
}
