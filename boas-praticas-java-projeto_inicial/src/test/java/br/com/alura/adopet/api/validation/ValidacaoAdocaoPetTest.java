package br.com.alura.adopet.api.validation;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ValidacaoAdocaoPetTest {

  @InjectMocks private ValidacaoAdocaoPet validacaoAdocaoPet;

  @Mock private PetRepository petRepository;

  private final Pet mockPet = Mockito.mock(Pet.class);

  @Test
  public void deve_validar_se_pet_nao_foi_adorado_e_nao_retornar_exceptiom() {

    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(mockPet.getAdotado()).thenReturn(false);
    Mockito.when(petRepository.getReferenceById(ArgumentMatchers.anyLong())).thenReturn(mockPet);

    Assertions.assertDoesNotThrow(() -> validacaoAdocaoPet.validar(dto));

    Mockito.verify(petRepository, Mockito.times(1)).getReferenceById(ArgumentMatchers.anyLong());
    Mockito.verify(mockPet, Mockito.times(1)).getAdotado();
  }

  @Test
  public void deve_validar_que_pet_foi_adotado_e_retornar_exception() {
    SolicitacaoAdocaoDto dto = getSolicitacaoAdocaoDto();

    Mockito.when(mockPet.getAdotado()).thenReturn(true);
    Mockito.when(petRepository.getReferenceById(ArgumentMatchers.anyLong())).thenReturn(mockPet);

    ValidacaoException result =
        Assertions.assertThrows(ValidacaoException.class, () -> validacaoAdocaoPet.validar(dto));

    Mockito.verify(petRepository, Mockito.times(1)).getReferenceById(ArgumentMatchers.anyLong());
    Mockito.verify(mockPet, Mockito.times(1)).getAdotado();
    Assertions.assertEquals("Pet já foi adotado!", result.getMessage());
  }

  private SolicitacaoAdocaoDto getSolicitacaoAdocaoDto() {
    return new SolicitacaoAdocaoDto(0L, 0L, "Teste");
  }
}
