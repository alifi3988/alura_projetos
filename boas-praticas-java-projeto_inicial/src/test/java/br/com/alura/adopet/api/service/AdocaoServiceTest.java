package br.com.alura.adopet.api.service;

import static org.mockito.Mockito.when;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.ValidacaoAdocao;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

  @InjectMocks private AdocaoService adocaoServiceMock;

  @Mock private SolicitacaoAdocaoDto solicitacaoAdocaoDtoMock;

  @Mock private AprovacaoAdocaoDto aprovacaoAdocaoDtoMock;

  @Mock private ReprovacaoAdocaoDto reprovacaoAdocaoDtoMock;

  @Mock private Pet petMock;

  @Mock private Tutor tutorMock;

  @Mock private Abrigo abrigoMock;

  @Mock private Adocao adocaoMock;

  @Mock private PetRepository petRepositoryMock;

  @Mock private TutorRepository tutorRepositoryMock;

  @Mock private AdocaoRepository adocaoRepositoryMock;

  @Mock private List<ValidacaoAdocao> validacao;

  @Mock private EmailService emailService;

  @Test
  public void deve_realizar_solicitacao_de_adocao() {
    Long idPet = 0l;
    Long idTutor = 0l;

    when(petRepositoryMock.getReferenceById(idPet)).thenReturn(petMock);
    when(tutorRepositoryMock.getReferenceById(idTutor)).thenReturn(tutorMock);
    when(petRepositoryMock.getReferenceById(idPet)).thenReturn(petMock);
    when(petMock.getAbrigo()).thenReturn(abrigoMock);

    Assertions.assertDoesNotThrow(() -> adocaoServiceMock.solicitar(solicitacaoAdocaoDtoMock));
  }

  @Test
  public void deve_aprovar_solicaitacao_de_adocao() {
    Long idAdocao = 0l;
    when(adocaoRepositoryMock.getReferenceById(idAdocao)).thenReturn(adocaoMock);
    when(adocaoMock.getTutor()).thenReturn(tutorMock);
    when(adocaoMock.getPet()).thenReturn(petMock);
    when(adocaoMock.getData()).thenReturn(LocalDateTime.now());
    when(petMock.getAbrigo()).thenReturn(abrigoMock);
    Assertions.assertDoesNotThrow(() -> adocaoServiceMock.aprovar(aprovacaoAdocaoDtoMock));
  }

  @Test
  public void deve_reprovar_solicitacao_de_adocao() {
    Long idAdocao = 0l;
    when(adocaoRepositoryMock.getReferenceById(idAdocao)).thenReturn(adocaoMock);
    when(adocaoMock.getTutor()).thenReturn(tutorMock);
    when(adocaoMock.getPet()).thenReturn(petMock);
    when(adocaoMock.getData()).thenReturn(LocalDateTime.now());
    when(petMock.getAbrigo()).thenReturn(abrigoMock);
    Assertions.assertDoesNotThrow(() -> adocaoServiceMock.reprovar(reprovacaoAdocaoDtoMock));
  }
}
