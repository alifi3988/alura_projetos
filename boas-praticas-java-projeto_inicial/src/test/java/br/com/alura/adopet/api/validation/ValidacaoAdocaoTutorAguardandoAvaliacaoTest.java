package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidacaoAdocaoTutorAguardandoAvaliacaoTest {

  @InjectMocks private ValidacaoAdocaoTutorAguardandoAvaliacao validacao;

  @Mock private AdocaoRepository repository;

  @Test
  public void deve_validar_se_tutor_ja_esta_aguardando_validacao_e_nao_deve_retornar_exception() {
    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(
            repository.findByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO))
        .thenReturn(List.of());

    Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    Mockito.verify(repository, Mockito.times(1))
        .findByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO);
  }

  @Test
  public void deve_validar_se_tutor_ja_esta_aguardando_validacao_e_deve_retornar_exception() {
    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(
            repository.findByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO))
        .thenReturn(List.of(new Adocao()));

    ValidacaoException result =
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

    Mockito.verify(repository, Mockito.times(1))
        .findByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO);
    Assertions.assertEquals(
        "Tutor já possui outra adoção aguardando avaliação!", result.getMessage());
  }

  private SolicitacaoAdocaoDto getSolicitacaoAdocaoDto() {
    return new SolicitacaoAdocaoDto(0L, 0L, "Teste");
  }
}
