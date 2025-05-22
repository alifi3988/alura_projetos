package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbrigoService {

  @Autowired private AbrigoRepository abrigoRepository;

  @Autowired private PetRepository petRepository;

  public List<AbrigoDto> listarTodosAbrigos() {
    return abrigoRepository.findAll().stream().map(AbrigoDto::new).toList();
  }

  public void cadastrarAbrigo(CadastroAbrigoDto dto) {
    if (abrigoRepository.existByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())) {
      throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
    }
    abrigoRepository.save(new Abrigo(dto));
  }

  public List<PetDto> listarPetsPorIdOrNome(String idOuNome) {
    Abrigo abrigo = carregarAbrigo(idOuNome);
    return petRepository.findByAbrigo(abrigo).stream().map(PetDto::new).toList();
  }

  public void cadastrarPet(String idOuNomeAbrigo, Pet pet) {
    Abrigo abrigo = carregarAbrigo(idOuNomeAbrigo);
    pet.adicionarAbrigoEmPet(abrigo);
    abrigo.adicionarPetEmAbrigo(pet);
    abrigoRepository.save(abrigo);
  }

  private Abrigo carregarAbrigo(String idOuNome) {
    Optional<Abrigo> abrigoOptional;
    try {
      Long id = Long.parseLong(idOuNome);
      abrigoOptional = abrigoRepository.findById(id);
    } catch (NumberFormatException e) {
      abrigoOptional = abrigoRepository.findByNome(idOuNome);
    }
    return abrigoOptional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
  }
}
