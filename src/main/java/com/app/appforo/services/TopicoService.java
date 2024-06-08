package com.app.appforo.services;

import com.app.appforo.persistence.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TopicoService {
    Page<Topico> findAll(Pageable pageable);

    Topico add(Topico topico);

    Topico findById(String topicoId);

    Map<String, String> delete(String topicoId);

    Topico update(Topico topico,String topicoId);
}
