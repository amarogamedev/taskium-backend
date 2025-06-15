package com.amarogamedev.taskium.entity;

import com.amarogamedev.taskium.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nome", nullable = false)
    String nome;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @JsonIgnore
    @Column(name = "senha", nullable = false)
    String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    Tipo tipo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(tipo == Tipo.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
