package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

  @InjectMocks private PetService petServiceMock;

  @Mock private PetRepository petRepositoryMock;

  @Mock private List<Pet> petListMock;

  @Mock private PetDto petDtoMock;

  @Test
  public void deve_listar_pets_disponiveis() {
    List<PetDto> retorno = petServiceMock.listarPetsDisponiveis();
    Assertions.assertNotNull(retorno);
  }

  @Test
  public void deve_listar_pets_disponiveis_mas_vazio() {
    Mockito.when(petRepositoryMock.findByAdotado(false)).thenReturn(petListMock);
    ArrayIndexOutOfBoundsException result =
        Assertions.assertThrows(
            ArrayIndexOutOfBoundsException.class,
            () -> petServiceMock.listarPetsDisponiveis().get(0));
    Assertions.assertEquals("Index 0 out of bounds for length 0", result.getMessage());
  }
}
