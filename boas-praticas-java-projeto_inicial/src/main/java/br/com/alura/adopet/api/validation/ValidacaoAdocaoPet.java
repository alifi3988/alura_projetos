package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoAdocaoPet implements ValidacaoAdocao {

  @Autowired private PetRepository petRepository;

  public void validar(SolicitacaoAdocaoDto dto) {
    if (petRepository.getReferenceById(dto.idPet()).getAdotado()) {
      throw new ValidacaoException("Pet já foi adotado!");
    }
  }
}
