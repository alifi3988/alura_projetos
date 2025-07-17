package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private AdocaoService service;

  @Test
  void deve_solicitar_adocao_com_sucesso_200() throws Exception {
    // ARRANGE
    String json =
        """
                {
                    "idPet" : 1,
                    "idTutor": 1,
                    "motivo": "Teste"
                }
                """;

    // ACTION
    MockHttpServletResponse response =
        mvc.perform(post("/adocoes").content(json).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void deve_solicitar_adocao_com_erro_400() throws Exception {
    // ARRANGE
    String json = "{}";

    // ACTION
    MockHttpServletResponse response =
        mvc.perform(post("/adocoes").content(json).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // ASSERTIONS
    Assertions.assertEquals(400, response.getStatus());
  }
}
