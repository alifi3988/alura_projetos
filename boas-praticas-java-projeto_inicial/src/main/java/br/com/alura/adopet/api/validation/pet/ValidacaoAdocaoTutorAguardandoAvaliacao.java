package br.com.alura.adopet.api.validation.pet;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoAdocaoTutorAguardandoAvaliacao implements ValidacaoAdocao {

  @Autowired private AdocaoRepository adocaoRepository;

  public void validar(SolicitacaoAdocaoDto dto) {
    if (!adocaoRepository
        .findByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)
        .isEmpty()) {
      throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
    }
  }
}
