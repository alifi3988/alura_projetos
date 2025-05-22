package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.DadosTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

  @Autowired private TutorRepository repository;

  public void cadastrarTutor(DadosTutorDto tutor) {
    if (!repository.existsByTelefoneOrEmail(tutor.telefone(), tutor.email())) {
      throw new ValidacaoException("Dados já cadastrados para outro tutor!");
    }
    repository.save(new Tutor(tutor));
  }

  public void atualizarTutor(DadosTutorDto tutorDto) {
    repository.save(new Tutor(tutorDto));
  }
}
