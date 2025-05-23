package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.repository.PetRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

  @Autowired private PetRepository repository;

  public List<PetDto> listarPetsDisponiveis() {
    return repository.findByAdotado(false).stream().map(PetDto::new).toList();
  }
}
