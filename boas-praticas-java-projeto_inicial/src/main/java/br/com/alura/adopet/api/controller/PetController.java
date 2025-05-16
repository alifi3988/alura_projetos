package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.pet.DadosDetalhesPet;
import br.com.alura.adopet.api.service.PetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
public class PetController {

  @Autowired private PetService service;

  @GetMapping
  public ResponseEntity<List<DadosDetalhesPet>> listarTodosDisponiveis() {
    return ResponseEntity.ok(service.listarPetsDisponiveis());
  }
}
