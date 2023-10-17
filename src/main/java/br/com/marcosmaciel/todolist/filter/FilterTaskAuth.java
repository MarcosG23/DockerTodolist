package br.com.marcosmaciel.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.marcosmaciel.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//Usa pq precisa para que o Spring entenda que precisa gerencia essa classe//
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
              
              var servletPath = request.getServletPath();
              System.out.println("PATH: " + servletPath);
              if(servletPath.startsWith("/tasks/")){
                //pegar a autenticalção(usuario e senha)//
                var authorization = request.getHeader("Authorization");
                
                var authEncoded = authorization.substring("Basic".length()).trim();
                //Usado para pegar e falar pro SubString, buscar algo e apartir disso remover os espaços que tem em seguida.(trim = remover o  espaço em branco)//
                byte[] authDecode = Base64.getDecoder().decode(authEncoded);

                var authString = new String(authDecode);
                
                String[] creadentials = authString.split(":");
                String username = creadentials[0];
                String password = creadentials[1];
                
                //validar usuario//
                var user = this.userRepository.findByUsername(username);
                if (user==null){
                  //Se o usuario nao existir no banco de dados, ele passará um erro.//
                    response.sendError(401);
                }else{
                //validar senha//
                   var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                   if(passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                        filterChain.doFilter(request, response);
                   }else{
                     response.sendError(401);
                   }
                    
                
                }
              }else{
                filterChain.doFilter(request, response);
                //comparar se as senhas batem com o banco de dados.//
                //seguir com o processo//
              }
                
    }











    /* @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        System.out.println("Chegou no filtro");
        chain.doFilter(request, response);
        //doFilter - Executar alguma ação (Barrar a requisição ou aceitar a requisição)//
    }
     */
}
