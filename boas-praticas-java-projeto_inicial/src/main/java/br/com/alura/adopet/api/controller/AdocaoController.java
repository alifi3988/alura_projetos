package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

  @Autowired private AdocaoService service;

  @PostMapping
  @Transactional
  public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitacaoAdocaoDto dto) {
    try {
      service.solicitar(dto);
      return ResponseEntity.ok("Adoção solicitada com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body("Erro ao realizar a solicitação. Detalhe: " + e.getMessage());
    }
  }

  @PutMapping("/aprovar")
  @Transactional
  public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDto dto) {
    try {
      service.aprovar(dto);
      return ResponseEntity.ok("Aprovação realizada com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body("Erro ao realizar a aprovação. Detalhe: " + e.getMessage());
    }
  }

  @PutMapping("/reprovar")
  @Transactional
  public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovacaoAdocaoDto dto) {
    try {
      service.reprovar(dto);
      return ResponseEntity.ok("Reprovação realizada com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body("Erro ao realizar a reprovação. Detalhe: " + e.getMessage());
    }
  }
}
