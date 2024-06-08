package com.app.appforo.persistence.repository;

import com.app.appforo.persistence.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Integer> {

}
