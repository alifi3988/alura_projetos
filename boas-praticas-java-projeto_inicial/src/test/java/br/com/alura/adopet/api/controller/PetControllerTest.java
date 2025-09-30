package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private PetService service;

  @Test
  public void deve_listar_todos_os_pets_disponiveis_com_sucesso() throws Exception {
    // ARRANGE

    // ACTION
    MockHttpServletResponse response = mvc.perform(get("/pets")).andReturn().getResponse();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
  }
}
