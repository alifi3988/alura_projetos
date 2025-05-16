package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.pet.DadosDetalhesPet;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbrigoService {

  @Autowired private AbrigoRepository repository;

  public void cadastrarAbrigo(Abrigo abrigo) {
    if (repository.existByNomeOrTelefoneOrEmail(
        abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail())) {
      throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
    }
    repository.save(abrigo);
  }

  public List<DadosDetalhesPet> listarPetsPorIdOrNome(String idOuNome) {
    List<Pet> ret = new ArrayList<>();
    try {
      Long id = Long.parseLong(idOuNome);
      ret = repository.getReferenceById(id).getPets();
    } catch (NumberFormatException e) {
      try {
        ret = repository.findByNome(idOuNome).getPets();
      } catch (EntityNotFoundException enfe) {
        throw new EntityNotFoundException(enfe.getMessage());
      }
    } catch (EntityNotFoundException enfe) {
      throw new EntityNotFoundException(enfe.getMessage());
    }
    return ret.stream().map(DadosDetalhesPet::new).toList();
  }

  public void cadastrarPet(String idOuNome, Pet pet) {
    Abrigo abrigo = new Abrigo();
    try {
      Long id = Long.parseLong(idOuNome);
      abrigo = repository.getReferenceById(id);
    } catch (EntityNotFoundException enfe) {
      throw new EntityNotFoundException(enfe.getMessage());
    } catch (NumberFormatException nfe) {
      try {
        abrigo = repository.findByNome(idOuNome);
      } catch (EntityNotFoundException enfe) {
        throw new EntityNotFoundException(enfe.getMessage());
      }
    }
    pet.setAbrigo(abrigo);
    pet.setAdotado(false);
    abrigo.getPets().add(pet);
    repository.save(abrigo);
  }
}
