package com.app.appforo.persistence.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String username;
    private String password;

    //podria pedir Get and Set
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    //crear el id profile
}
