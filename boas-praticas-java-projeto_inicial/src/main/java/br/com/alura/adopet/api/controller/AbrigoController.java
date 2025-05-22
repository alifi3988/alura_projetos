package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Pet;
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

  @Autowired private AbrigoService abrigoService;

  @GetMapping
  public ResponseEntity<List<AbrigoDto>> listar() {
    return ResponseEntity.ok(abrigoService.listarTodosAbrigos());
  }

  @PostMapping
  @Transactional
  public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDto dto) {
    try {
      abrigoService.cadastrarAbrigo(dto);
      return ResponseEntity.ok("Abrigo cadastrado com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{idOuNome}/pets")
  public ResponseEntity<List<PetDto>> listarPets(@PathVariable String idOuNome) {
    try {
      return ResponseEntity.ok(abrigoService.listarPetsPorIdOrNome(idOuNome));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{idOuNome}/pets")
  @Transactional
  public ResponseEntity<String> cadastrarPet(
      @PathVariable String idOuNome, @RequestBody @Valid Pet pet) {
    try {
      abrigoService.cadastrarPet(idOuNome, pet);
      return ResponseEntity.ok().body("Pet cadastrado com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
