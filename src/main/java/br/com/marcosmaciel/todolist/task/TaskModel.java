package br.com.marcosmaciel.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
     * ID
     * Usuário (ID_USUARIO)
     * Descrição
     * Título
     * Data de início
     * Data de termino
     * Prioridade  (Alta-Baixa-Média)
     */
    

@Data
@Entity(name = "tb_tasks")

public class TaskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    //Usado para gerar o ID automaticamente.//

     private UUID ID;
     private String description;

     @Column(length = 50)
     //Usado para limitar o tanto de caracteres.//
     private String title;
     private LocalDateTime startAt;
     private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

    @CreationTimestamp
    //Usado para quando essa tarefa for criado no banco de dados.//
    private LocalDateTime createdAt;


    public void setTitle(String title) throws Exception{
        if(title.length()>50){
            throw new Exception("O campo title deve conter no maximo 50 caracteres");
        }
        this.title=title;
    }
    
    
}
