package br.com.marcosmaciel.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;


    @PostMapping("/")
    public ResponseEntity create (@RequestBody UserModel userModel){
        //ResponseEntity criado para ter mais de uma resposta na API//
        var user =this.userRepository.findByUsername(userModel.getUsername());
        //para verificar se tem um username criado ja.//
        if(user != null){// criado a condição para caso dê um erro e ja tenha uma informação.//
             
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }

        var passwordHasherd = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        // usado para criptografar a senha no banco de dados//

        userModel.setPassword(passwordHasherd);

       var userCreated = this.userRepository.save(userModel);
       return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        
    }
}
