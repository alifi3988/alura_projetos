package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

  @Autowired private TutorRepository repository;

  public void cadastrarTutor(Tutor tutor) {
    if (!repository.existsByTelefoneOrEmail(tutor.getTelefone(), tutor.getEmail())) {
      throw new ValidacaoException("Dados já cadastrados para outro tutor!");
    }
    repository.save(tutor);
  }
}
