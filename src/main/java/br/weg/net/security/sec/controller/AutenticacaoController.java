package br.weg.net.security.sec.controller;

import br.weg.net.security.sec.model.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AutenticacaoController {

    private SecurityContextRepository secRepository;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginDto loginDTO,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDTO.username(), loginDTO.password()
        );
        Authentication auth = authenticationManager.authenticate(token);

        if (auth.isAuthenticated()) {
            SecurityContext secContext = SecurityContextHolder
                    .createEmptyContext();

            secContext.setAuthentication(auth);
            secRepository.saveContext(secContext, request, response);
            response.setStatus(200);
        } else {
            response.setStatus(401);
        }
    }
}




