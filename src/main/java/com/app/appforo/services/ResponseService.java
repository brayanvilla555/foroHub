package com.app.appforo.services;

import com.app.appforo.persistence.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ResponseService {
    Page<Response> findAll(Pageable pageable);

    Response add(Response response);

    Response findById(String responseId);

    Map<String, String> delete(String responseId);

    Response update(Response response,String responseId);
}
