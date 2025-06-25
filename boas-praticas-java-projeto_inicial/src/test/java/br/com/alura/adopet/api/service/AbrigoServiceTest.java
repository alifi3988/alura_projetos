package br.com.alura.adopet.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AbrigoServiceTest {

  @Mock private AbrigoRepository abrigoRepositoryMock;

  @Mock private PetRepository petRepositoryMock;

  @Mock private List<Pet> listaPetMock;

  @Mock private Pet petMock;

  @Mock private Abrigo abrigoMock;

  @Mock private List<Abrigo> listaAbrigoMock;

  @Mock private Optional<Abrigo> abrigoOptionalMock;

  @Mock private CadastroAbrigoDto cadastroAbrigoDtoMock;

  @InjectMocks private AbrigoService abrigoService;

  @Test
  public void deve_listar_todos_abrigos() {
    Mockito.when(abrigoRepositoryMock.findAll()).thenReturn(listaAbrigoMock);
    List<AbrigoDto> retorno = abrigoService.listarTodosAbrigos();

    Assertions.assertNotNull(retorno);
  }

  @Test
  public void deve_cadastrar_abrigo() {
    Mockito.when(abrigoRepositoryMock.existsByNomeOrTelefoneOrEmail(any(), any(), any()))
        .thenReturn(false);

    Assertions.assertDoesNotThrow(() -> abrigoService.cadastrarAbrigo(cadastroAbrigoDtoMock));
  }

  @Test
  public void deve_acionar_uma_exception_ao_tentar_cadastrar_abrigo() {
    Mockito.when(abrigoRepositoryMock.existsByNomeOrTelefoneOrEmail(any(), any(), any()))
        .thenReturn(true);

    ValidacaoException exceptionLancada =
        Assertions.assertThrows(
            ValidacaoException.class, () -> abrigoService.cadastrarAbrigo(cadastroAbrigoDtoMock));
    Assertions.assertEquals(
        "Dados já cadastrados para outro abrigo!", exceptionLancada.getMessage());
  }

  @Test
  public void deve_listar_pets_por_nome() {
    String nomeAbrigo = "teste";
    Mockito.when(abrigoRepositoryMock.findByNome(nomeAbrigo)).thenReturn(abrigoOptionalMock);
    Mockito.when(petRepositoryMock.findByAbrigo(abrigoOptionalMock.get())).thenReturn(listaPetMock);
    List<PetDto> retorno = abrigoService.listarPetsPorIdOrNomeDoAbrigo(nomeAbrigo);

    Assertions.assertNotNull(retorno);
  }

  @Test
  public void deve_listar_pets_por_id() {
    String idAbrigo = "123456";
    Mockito.when(abrigoRepositoryMock.findById(Long.parseLong(idAbrigo)))
        .thenReturn(abrigoOptionalMock);
    Mockito.when(petRepositoryMock.findByAbrigo(abrigoOptionalMock.get())).thenReturn(listaPetMock);
    List<PetDto> retorno = abrigoService.listarPetsPorIdOrNomeDoAbrigo(idAbrigo);

    Assertions.assertNotNull(retorno);
  }

  @Test
  public void deve_acionar_exception_ao_listar_pets_por_nome() {
    String nomeAbrigo = "teste";
    Mockito.when(abrigoRepositoryMock.findByNome(nomeAbrigo)).thenReturn(Optional.empty());

    ValidacaoException execaoLancada =
        Assertions.assertThrows(
            ValidacaoException.class,
            () -> abrigoService.listarPetsPorIdOrNomeDoAbrigo(nomeAbrigo));
    Assertions.assertEquals("Abrigo não encontrado", execaoLancada.getMessage());
  }

  @Test
  public void deve_acionar_exception_pets_por_id() {
    String idAbrigo = "123456";
    Mockito.when(abrigoRepositoryMock.findById(Long.parseLong(idAbrigo)))
        .thenReturn(Optional.empty());

    ValidacaoException execaoLancada =
        Assertions.assertThrows(
            ValidacaoException.class, () -> abrigoService.listarPetsPorIdOrNomeDoAbrigo(idAbrigo));
    Assertions.assertEquals("Abrigo não encontrado", execaoLancada.getMessage());
  }

  @Test
  public void deve_cadastrar_pet_pelo_nome_do_abrigo() {
    String nomeAbrigo = "teste";

    Mockito.when(abrigoRepositoryMock.findByNome(nomeAbrigo)).thenReturn(Optional.of(abrigoMock));

    Assertions.assertDoesNotThrow(() -> abrigoService.cadastrarPet(nomeAbrigo, petMock));

    Mockito.verify(petMock, times(1)).adicionarAbrigoEmPet(abrigoMock);
    Mockito.verify(abrigoMock, times(1)).adicionarPetEmAbrigo(petMock);
    Mockito.verify(abrigoRepositoryMock, times(1)).save(abrigoMock);
  }

  @Test
  public void deve_lancar_excecao_quando_abrigo_nao_encontrado() {
    String nomeAbrigoInexistente = "naoExiste";

    Mockito.when(abrigoRepositoryMock.findByNome(nomeAbrigoInexistente))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(
        ValidacaoException.class, () -> abrigoService.cadastrarPet(nomeAbrigoInexistente, petMock));

    Mockito.verify(abrigoRepositoryMock, never()).save(any());
    Mockito.verify(petMock, never()).adicionarAbrigoEmPet(any());
    Mockito.verify(abrigoMock, never()).adicionarPetEmAbrigo(any());
  }
}
