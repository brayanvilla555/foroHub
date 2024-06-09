package com.app.appforo.persistence.entity;

import com.app.appforo.exception.ApiException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String username;
    private String password;

    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean enabled;

    //podria pedir Get and Set
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null){
            throw new ApiException("No cuentas con un rol");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getAuthority()));

        if (role.getGrantedPermissions() == null) {
            return authorities;
        }

        role.getGrantedPermissions()
                .forEach(each->{
                    String permission = each.getPermission().getName();
                    authorities.add(new SimpleGrantedAuthority(permission));
                });
        return authorities;
    }



    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    //crear el id profile
}
