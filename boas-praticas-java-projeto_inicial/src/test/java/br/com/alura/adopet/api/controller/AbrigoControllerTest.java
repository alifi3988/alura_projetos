package br.com.alura.adopet.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private AbrigoService service;

  @Test
  public void deve_listar_os_abrigos_cadastrados() throws Exception {
    // ARRANGE

    // ACTION
    MockHttpServletResponse response = mvc.perform(get("/abrigos")).andReturn().getResponse();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  public void deve_cadastrar_abrigo_com_sucesso() throws Exception {
    // ARRANGE
    String jsonRequestBody =
        """
                {
                    "nome": "Teste",
                    "telefone": "(19)9999-9999",
                    "email": "teste@teste.com"
                }
                """;

    // ACTION
    MockHttpServletResponse response =
        mvc.perform(
                post("/abrigos").content(jsonRequestBody).contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
    Assertions.assertEquals("Abrigo cadastrado com sucesso!", response.getContentAsString());
  }

  @Test
  public void deve_listar_os_pets_de_um_determinado_abrigo_informado_pelo_id_com_sucesso()
      throws Exception {
    // ARRANGE
    String idPet = "123456";

    // ACTION
    MvcResult result = mvc.perform(get(String.format("/abrigos/%s/pets", idPet))).andReturn();

    var response = result.getResponse();
    var request = result.getRequest();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
    Assertions.assertEquals("/abrigos/123456/pets", request.getRequestURI());
  }

  @Test
  public void deve_listar_os_pets_de_um_determinado_abrigo_informado_pelo_nome_com_sucesso()
      throws Exception {
    // ARRANGE
    String nomePet = "teste";

    // ACTION
    MvcResult result = mvc.perform(get(String.format("/abrigos/%s/pets", nomePet))).andReturn();

    var response = result.getResponse();
    var request = result.getRequest();

    // ASSERTIONS
    Assertions.assertEquals(200, response.getStatus());
    Assertions.assertEquals("/abrigos/teste/pets", request.getRequestURI());
  }

  public void deve_cadastrar_pet_em_abrigo_com_sucesso_pelo_id_do_abrigo()
      throws Exception { // TODO: Preciso revizr esse teste, pois está gerando erro
    // ARRANGE
    String idAbrigo = "123456";

    String jsonBodyRequest =
        """
                {
                    "tipo": "GATO",
                    "nome": "Nome Teste",
                    "raca": "Raça Teste",
                    "idade": 2,
                    "cor": "preto",
                    "peso": 5.00
                }
                """;

    // ACTION
    MvcResult result =
        mvc.perform(
                post(String.format("/abrigos/%s/pets", idAbrigo))
                    .content(jsonBodyRequest)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    var response = result.getResponse();
    var request = result.getRequest();

    // ASSERTIONS
    Assertions.assertEquals("/abrigos/123456/pets", request.getRequestURI());
    System.out.println(request.getContentAsString());
    Assertions.assertEquals(200, response.getStatus());
  }

  public void deve_cadastrar_pet_em_abrigo_com_sucesso_pelo_nome_do_abrigo()
      throws Exception { // TODO: Preciso revizr esse teste, pois está gerando erro
    // ARRANGE
    String nomeAbrigo = "teste";

    String jsonBodyRequest =
        """
                {
                    "tipo": "GATO",
                    "nome": "Nome Teste",
                    "raca": "Raça Teste",
                    "idade": 2,
                    "cor": "preto",
                    "peso": 5.00
                }
                """;

    // ACTION
    MvcResult result =
        mvc.perform(
                post(String.format("/abrigos/%s/pets", nomeAbrigo))
                    .content(jsonBodyRequest)
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn();

    var response = result.getResponse();
    var request = result.getRequest();

    // ASSERTIONS
    Assertions.assertEquals("/abrigos/123456/pets", request.getRequestURI());
    System.out.println(request.getContentAsString());
    Assertions.assertEquals(200, response.getStatus());
  }
}
