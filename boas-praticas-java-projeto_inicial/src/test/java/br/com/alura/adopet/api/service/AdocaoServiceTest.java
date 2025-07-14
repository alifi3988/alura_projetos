package br.com.alura.adopet.api.service;

import static org.mockito.Mockito.verify;
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
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

  @InjectMocks
  private AdocaoService service;

  @Mock private SolicitacaoAdocaoDto SolicitacaoAdocaoDto;

  @Mock private AprovacaoAdocaoDto aprovacaoAdocaoDto;

  @Mock private ReprovacaoAdocaoDto reprovacaoAdocaoDto;

  @Mock private Pet pet;

  @Mock private Tutor tutor;

  @Mock private Abrigo abrigo;

  @Mock private Adocao adocao;

  @Mock private PetRepository petRepository;

  @Mock private TutorRepository tutorRepository;

  @Mock private AdocaoRepository adocaoRepository;

  @Spy
  private List<ValidacaoAdocao> validacao = new ArrayList<>();

  @Mock
  private ValidacaoAdocao validacaoPrimeiro;

  @Mock
  private ValidacaoAdocao validacaoSegunda;

  @Mock private EmailService emailService;

  @Captor
  private ArgumentCaptor<Adocao> adocaoCaptor;

  private ArgumentCaptor<AprovacaoAdocaoDto> aprovacaoAdocaoCaptor;

  @Test
  public void deve_realizar_solicitacao_de_adocao_com_sucesso() {
    this.SolicitacaoAdocaoDto = new SolicitacaoAdocaoDto(0L, 0L, "teste");

    when(petRepository.getReferenceById(SolicitacaoAdocaoDto.idPet())).thenReturn(pet);
    when(tutorRepository.getReferenceById(SolicitacaoAdocaoDto.idTutor())).thenReturn(tutor);
    when(petRepository.getReferenceById(SolicitacaoAdocaoDto.idPet())).thenReturn(pet);
    when(pet.getAbrigo()).thenReturn(abrigo);

    Assertions.assertDoesNotThrow(() -> service.solicitar(SolicitacaoAdocaoDto));

    verify(adocaoRepository).save(adocaoCaptor.capture());
    Adocao adocaoSalva = adocaoCaptor.getValue();
    Assertions.assertEquals(pet, adocaoSalva.getPet());
    Assertions.assertEquals(tutor, adocaoSalva.getTutor());
    Assertions.assertEquals(SolicitacaoAdocaoDto.motivo(), adocaoSalva.getMotivo());
  }

  @Test
  public void deve_realizar_solicitacao_de_adocao_com_sucesso_e_validar() {
    this.SolicitacaoAdocaoDto = new SolicitacaoAdocaoDto(0L, 0L, "teste");
    validacao.add(validacaoPrimeiro);
    validacao.add(validacaoSegunda);

    when(petRepository.getReferenceById(SolicitacaoAdocaoDto.idPet())).thenReturn(pet);
    when(tutorRepository.getReferenceById(SolicitacaoAdocaoDto.idTutor())).thenReturn(tutor);
    when(petRepository.getReferenceById(SolicitacaoAdocaoDto.idPet())).thenReturn(pet);
    when(pet.getAbrigo()).thenReturn(abrigo);

    Assertions.assertDoesNotThrow(() -> service.solicitar(SolicitacaoAdocaoDto));

    verify(validacaoPrimeiro).validar(SolicitacaoAdocaoDto);
    verify(validacaoSegunda).validar(SolicitacaoAdocaoDto);
  }

   @Test
   public void deve_aprovar_solicitacao_de_adocao_com_sucesso() {
     this.aprovacaoAdocaoDto = new AprovacaoAdocaoDto(0l);

     when(adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).thenReturn(adocao);
     when(adocao.getTutor()).thenReturn(tutor);
     when(adocao.getPet()).thenReturn(pet);
     when(adocao.getData()).thenReturn(LocalDateTime.now());
     when(pet.getAbrigo()).thenReturn(abrigo);

     Assertions.assertDoesNotThrow(() -> service.aprovar(aprovacaoAdocaoDto));
   }

   @Test
   public void deve_reprovar_solicitacao_de_adocao() {
     Long idAdocao = 0l;
     when(adocaoRepository.getReferenceById(idAdocao)).thenReturn(adocao);
     when(adocao.getTutor()).thenReturn(tutor);
     when(adocao.getPet()).thenReturn(pet);
     when(adocao.getData()).thenReturn(LocalDateTime.now());
     when(pet.getAbrigo()).thenReturn(abrigo);
     Assertions.assertDoesNotThrow(() -> service.reprovar(reprovacaoAdocaoDto));
   }
}
