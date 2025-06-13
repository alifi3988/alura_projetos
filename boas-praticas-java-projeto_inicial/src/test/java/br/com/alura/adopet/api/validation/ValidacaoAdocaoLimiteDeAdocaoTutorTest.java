package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidacaoAdocaoLimiteDeAdocaoTutorTest {

  @InjectMocks private ValidacaoAdocaoLimiteDeAdocaoTutor validacao;

  @Mock private AdocaoRepository adocaoRepository;

  @Test
  public void
      deve_validar_o_limite_de_adocao_por_tutor_quando_for_menor_que_cinco_e_nao_lancar_exception() {
    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(adocaoRepository.findByTutorIdAndStatus(0L, StatusAdocao.APROVADO))
        .thenReturn(getListAdocaoWithOnlyOneRegistry());

    Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    Mockito.verify(adocaoRepository, Mockito.times(1))
        .findByTutorIdAndStatus(0L, StatusAdocao.APROVADO);
  }

  @Test
  public void
      deve_retornar_exception_quando_o_limite_de_adocao_por_tutor_for_maior_que_cinco_e_lancar_exception() {
    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(adocaoRepository.findByTutorIdAndStatus(0L, StatusAdocao.APROVADO))
        .thenReturn(getListAdocaoWithAnyRegistry());

    ValidacaoException result =
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

    Assertions.assertEquals("Tutor chegou ao limite máximo de 5 adoções!", result.getMessage());
    Mockito.verify(adocaoRepository, Mockito.times(1))
        .findByTutorIdAndStatus(0L, StatusAdocao.APROVADO);
  }

  private SolicitacaoAdocaoDto getSolicitacaoAdocaoDto() {
    return new SolicitacaoAdocaoDto(0L, 0L, "Teste");
  }

  private List<Adocao> getListAdocaoWithOnlyOneRegistry() {
    ArrayList<Adocao> listArrayList = new ArrayList<>();
    listArrayList.add(new Adocao());
    return listArrayList;
  }

  private List<Adocao> getListAdocaoWithAnyRegistry() {
    ArrayList<Adocao> listArrayList = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      listArrayList.add(new Adocao());
    }
    return listArrayList;
  }
}
