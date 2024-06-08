package com.app.appforo.persistence.repository;

import com.app.appforo.persistence.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Integer> {
}
