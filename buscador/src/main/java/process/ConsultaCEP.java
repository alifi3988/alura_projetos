package process;

import com.google.gson.Gson;
import model.Endereco;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaCEP {

    public Endereco buscaEndereco(String cep) {

        final String uri = "https://viacep.com.br/ws/" + cep + "/json/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | IllegalStateException e) {
            throw new RuntimeException("Erro: " + e.getMessage() + " \nNão foi possivel localizar o CEP.");
        }
        return new Gson().fromJson(response.body(), Endereco.class);
    }
}
