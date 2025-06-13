package br.com.alura.adopet.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import br.com.alura.adopet.api.dto.tutor.DadosTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

  @InjectMocks private TutorService tutorServiceMock;

  @Mock private TutorRepository tutorRepositoryMock;

  @Mock private DadosTutorDto dadosTutorDtoMock;

  @Test
  void deve_cadastrar_tutor() {

    Assertions.assertDoesNotThrow(() -> tutorServiceMock.cadastrarTutor(dadosTutorDtoMock));
  }

  @Test
  void deve_acionar_exception_quando_tentar_cadastar_tutor_com_dados_ja_existentes() {

    Mockito.when(tutorRepositoryMock.existsByTelefoneOrEmail(any(), any())).thenReturn(true);

    ValidacaoException retorno =
        assertThrows(
            ValidacaoException.class, () -> tutorServiceMock.cadastrarTutor(dadosTutorDtoMock));
    Assertions.assertEquals("Dados já cadastrados para outro tutor!", retorno.getMessage());
  }

  @Test
  void deve_atualizar_tutor() {}
}
