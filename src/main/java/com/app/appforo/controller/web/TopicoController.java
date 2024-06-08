package com.app.appforo.controller.web;

import com.app.appforo.persistence.entity.Topico;
import com.app.appforo.services.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping("/")
    public ResponseEntity<Page<Topico>> findAll(Pageable pageable) {
        return ResponseEntity.ok(topicoService.findAll(pageable));
    }

    @PostMapping("/")
    public ResponseEntity<Topico> add(@RequestBody Topico topico) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(topicoService.add(topico));
    }

    @GetMapping("/{topicoId}")
    public ResponseEntity<Topico> findById(@PathVariable String topicoId) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(topicoService.findById(topicoId));
    }

    @DeleteMapping("/{topicoId}")
    public Map<String, String> delete(@PathVariable String topicoId) {
        return topicoService.delete(topicoId);
    }

    @PutMapping("/{topicoId}")
    public ResponseEntity<Topico> update(@RequestBody Topico topico, @PathVariable String topicoId) {
        return ResponseEntity
                .status(HttpStatus.MULTI_STATUS)
                .body(topicoService.update(topico, topicoId));
    }
}
