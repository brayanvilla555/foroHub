package com.app.appforo.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @JsonIgnoreProperties("role")
    @OneToMany(mappedBy = "role", fetch =FetchType.EAGER)
    private Set<GrantedPermission> grantedPermissions;

    @Override
    public String getAuthority() {
        if (name == null) {
            return null;
        }
        return "ROLE_" + name.name();
    }


    private static enum RoleEnum{
        EDITOR, ADMIN
    }

}
