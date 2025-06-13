package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock private JavaMailSender emailSenderMock;

  @InjectMocks private EmailService emailServiceMock;

  @Test
  public void deve_enviar_email() {
    Assertions.assertDoesNotThrow(
        () -> emailServiceMock.sendEmail("teste@teste.com", "teste", "teste"));
  }
}
