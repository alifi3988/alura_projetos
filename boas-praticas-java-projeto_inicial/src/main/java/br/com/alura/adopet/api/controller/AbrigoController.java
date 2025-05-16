package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DadosDetalhesPet;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

  @Autowired private AbrigoRepository repository;

  @Autowired private AbrigoService service;

  @GetMapping
  public ResponseEntity<List<Abrigo>> listar() {
    return ResponseEntity.ok(repository.findAll());
  }

  @PostMapping
  @Transactional
  public ResponseEntity<String> cadastrar(@RequestBody @Valid Abrigo abrigo) {
    try {
      service.cadastrarAbrigo(abrigo);
      return ResponseEntity.ok("Abrigo cadastrado com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{idOuNome}/pets")
  public ResponseEntity<List<DadosDetalhesPet>> listarPets(@PathVariable String idOuNome) {
    try {
      return ResponseEntity.ok(service.listarPetsPorIdOrNome(idOuNome));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{idOuNome}/pets")
  @Transactional
  public ResponseEntity<String> cadastrarPet(
      @PathVariable String idOuNome, @RequestBody @Valid Pet pet) {
    try {
      service.cadastrarPet(idOuNome, pet);
      return ResponseEntity.ok().body("Pet cadastrado com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
