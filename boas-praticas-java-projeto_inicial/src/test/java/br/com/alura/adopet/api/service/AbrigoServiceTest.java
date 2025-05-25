package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.AbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @Mock
    private AbrigoRepository abrigoRepositoryMock;

    @Mock
    private PetRepository petRepositoryMock;

    @Mock
    private List<Pet> listaPetMock;

    @Mock
    private Pet petMock;

    @Mock
    private Abrigo abrigoMock;

    @Mock
    private List<Abrigo> listaAbrigoMock;

    @Mock
    private Optional<Abrigo> abrigoOptionalMock;

    @Mock
    private CadastroAbrigoDto cadastroAbrigoDtoMock;

    @InjectMocks
    private AbrigoService abrigoService;

    @Test
    void deve_listar_todos_abrigos() {
        Mockito.when(abrigoRepositoryMock.findAll()).thenReturn(listaAbrigoMock);
        List<AbrigoDto> retorno = abrigoService.listarTodosAbrigos();

        Assertions.assertNotNull(retorno);
    }

    @Test
    void deve_cadastrar_abrigo() {
        Mockito.when(abrigoRepositoryMock.existsByNomeOrTelefoneOrEmail(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> abrigoService.cadastrarAbrigo(cadastroAbrigoDtoMock));
    }

    @Test
    void deve_acionar_uma_exception_ao_tentar_cadastrar_abrigo() {
        Mockito.when(abrigoRepositoryMock.existsByNomeOrTelefoneOrEmail(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);

        ValidacaoException exceptionLancada = Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.cadastrarAbrigo(cadastroAbrigoDtoMock));
        Assertions.assertEquals("Dados já cadastrados para outro abrigo!", exceptionLancada.getMessage());
    }

    @Test
    void deve_listar_pets_por_nome() {
        String nomeAbrigo = "teste";
        Mockito.when(abrigoRepositoryMock.findByNome(nomeAbrigo)).thenReturn(abrigoOptionalMock);
        Mockito.when(petRepositoryMock.findByAbrigo(abrigoOptionalMock.get())).thenReturn(listaPetMock);
        List<PetDto> retorno = abrigoService.listarPetsPorIdOrNomeDoAbrigo(nomeAbrigo);

        Assertions.assertNotNull(retorno);
    }

    @Test
    void deve_listar_pets_por_id() {
        String idAbrigo = "123456";
        Mockito.when(abrigoRepositoryMock.findById(Long.parseLong(idAbrigo))).thenReturn(abrigoOptionalMock);
        Mockito.when(petRepositoryMock.findByAbrigo(abrigoOptionalMock.get())).thenReturn(listaPetMock);
        List<PetDto> retorno = abrigoService.listarPetsPorIdOrNomeDoAbrigo(idAbrigo);

        Assertions.assertNotNull(retorno);
    }

    @Test
    void deve_acionar_exception_pets_por_nome() {
        String nomeAbrigo = "teste";
        Mockito.when(abrigoRepositoryMock.findByNome(nomeAbrigo)).thenReturn(Optional.empty());

        ValidacaoException execaoLancada = Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.listarPetsPorIdOrNomeDoAbrigo(nomeAbrigo));
        Assertions.assertEquals("Abrigo não encontrado", execaoLancada.getMessage());
    }

    @Test
    void deve_acionar_exception_pets_por_id() {
        String idAbrigo = "123456";
        Mockito.when(abrigoRepositoryMock.findById(Long.parseLong(idAbrigo))).thenReturn(Optional.empty());

        ValidacaoException execaoLancada = Assertions.assertThrows(ValidacaoException.class, () -> abrigoService.listarPetsPorIdOrNomeDoAbrigo(idAbrigo));
        Assertions.assertEquals("Abrigo não encontrado", execaoLancada.getMessage());
    }
        // TODO: Preciso revisar esse teste, de acordo com o vídeo, pois não vi como preciso fazer. Esta gerandp erro
//    @Test
//    void deve_cadastrar_pet_pelo_nome_do_abrigo() {
//        String nomeAbrigo = "teste";
//        Mockito.when(abrigoRepositoryMock.findByNome(nomeAbrigo)).thenReturn(abrigoOptionalMock);
//        Mockito.when(petRepositoryMock.findByAbrigo(abrigoOptionalMock.get())).thenReturn(listaPetMock);
//
//        Mockito.verify(abrigoOptionalMock.get(), times(1)).adicionarPetEmAbrigo(petMock);
//        Mockito.verify(abrigoRepositoryMock, times(1)).save(abrigoOptionalMock.get());
//
//        abrigoService.cadastrarPet(nomeAbrigo, petMock);
//    }
}