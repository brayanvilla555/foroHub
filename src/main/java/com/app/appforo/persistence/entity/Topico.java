package com.app.appforo.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The tittle is required!!")
    @Column(unique = true)
    private String title;

    @NotBlank(message = "The message is required!!")
    @Column(unique = true)
    private String message;

    private LocalDateTime dateCreate;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public static enum StatusType{
        ACTIVE, INACTIVE
    }

    @PrePersist
    private void createdDate(){
        dateCreate = LocalDateTime.now();
    }

}
