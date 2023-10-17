package br.com.marcosmaciel.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    //UUID usado para criar um ID para cada um//
    @Column(unique = true) // criado para falar nçao deixar repetir informações//
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAT;
    //feito para mostra a ultima criação//
}
