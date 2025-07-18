package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private TutorService service;

  @Test
  public void deve_cadastrar_tutor_com_sucesso() throws Exception {
    // ARRANGE
    String jsonBodyRequest =
        """
                {
                    "nome": "Teste",
                    "telefone": "(19)99999999",
                    "email": "teste@teste.com"
                }
                """;

    // ACTION
    var response =
        mvc.perform(
                post("/tutores").content(jsonBodyRequest).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  public void nao_deve_cadastrar_tutor_com_erro() throws Exception {
    // ARRANGE
    String jsonBodyRequest =
        """
                {
                    "nome": "Teste Erro",
                    "telefone": "(19)99999999",
                    "email": "teste@teste;com"
                }
                """;

    // ACTION
    var response =
        mvc.perform(
                post("/tutores").content(jsonBodyRequest).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // ASSERTIONS
    Assertions.assertEquals(400, response.getStatus());
    Assertions.assertEquals("Invalid request content.", response.getErrorMessage());
  }

  @Test
  public void deve_atualizar_tutor_com_sucesso() throws Exception {
    // ARRANGE
    String jsonBodyRequest =
        """
                {
                    "nome": "Teste",
                    "telefone": "(19)99999999",
                    "email": "teste_atualizar@teste.com"
                }
                """;

    // ACTION
    var response =
        mvc.perform(
                put("/tutores").content(jsonBodyRequest).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
    Assertions.assertEquals("Dados atualizados com sucesso!", response.getContentAsString());
  }
}
