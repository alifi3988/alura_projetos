package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidacaoAdocaoAguardandoAvaliacaoTest {

  @Mock private AdocaoRepository repository;

  @InjectMocks private ValidacaoAdocaoAguardandoAvaliacao validacao;

  @Test
  public void deve_validar_se_pet_NAO_esta_aguardando_para_ser_adotado_e_NAO_lancar_exception() {
    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO))
        .thenReturn(false);

    Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    Mockito.verify(repository, Mockito.times(1))
        .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);
  }

  @Test
  public void deve_validar_se_pet_esta_aguardando_para_ser_adotado_e_lancar_exception() {
    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO))
        .thenReturn(true);

    var result = Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

    Mockito.verify(repository, Mockito.times(1))
        .existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);
    Assertions.assertEquals(
        "Pet já está aguardando avaliação para ser adotado!", result.getMessage());
  }

  private SolicitacaoAdocaoDto getSolicitacaoAdocaoDto() {
    return new SolicitacaoAdocaoDto(0L, 0L, "Teste");
  }
}
