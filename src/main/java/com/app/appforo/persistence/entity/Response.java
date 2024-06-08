package com.app.appforo.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "topico_id")
    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Topico topico;

    @JoinColumn(name = "user_id")
    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private User user;

    @Column(nullable = false)
    private String message;

    private String solution;
    private LocalDateTime dateCreate;

    @PrePersist
    private void genarateDate(){
        dateCreate = LocalDateTime.now();
    }
}
