package com.fiap.challengeJava.controller;

import com.fiap.challengeJava.dto.UserDTO;
import com.fiap.challengeJava.dto.auth.LoginRequestDTO;
import com.fiap.challengeJava.dto.auth.RegisterRequestDTO;
import com.fiap.challengeJava.dto.auth.RegisterResponseDTO;
import com.fiap.challengeJava.enums.UserRole;
import com.fiap.challengeJava.service.AuthService;
import com.fiap.challengeJava.service.models.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute LoginRequestDTO data, Model model) {
        this.authService.login(data);
        return new ModelAndView("servicos");
    }

    @PostMapping("/signup")
    public ModelAndView register(@Valid @ModelAttribute RegisterRequestDTO data, Model model) {
        RegisterResponseDTO account = this.authService.signup(data);
        model.addAttribute("message", data.getName() + ", sua conta foi cadastrada com sucesso.");
        return new ModelAndView("success");
    }

    @GetMapping("/role")
    public ResponseEntity<List<UserDTO>> findByRole(@RequestParam UserRole role) {
        List<UserDTO> users = userService.findByRole(role).stream().map(UserDTO::new).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // Endpoint para solicitar a redefinição de senha
    @PostMapping("/forgot-password")
    public ModelAndView forgotPassword(@RequestParam("email") String email, Model model) {
        return new ModelAndView("success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestParam String newName) {
        userService.update(id, newName);
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }
}
