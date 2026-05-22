package br.pucminas.sistemahospedagem.controller;

import br.pucminas.sistemahospedagem.dto.RegistroDTO;
import br.pucminas.sistemahospedagem.model.Usuario;
import br.pucminas.sistemahospedagem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody RegistroDTO dto) {
        return ResponseEntity.ok(authService.registrar(dto));
    }
}