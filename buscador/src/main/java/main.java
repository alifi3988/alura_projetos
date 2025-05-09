import model.Endereco;
import process.ConsultaCEP;
import process.GeradorDeArquivo;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        System.out.print("Informe o CEP para consulta: ");
        String cep = entrada.nextLine();

        try {
            ConsultaCEP consulta = new ConsultaCEP();
            Endereco retorno = consulta.buscaEndereco(cep);
            System.out.println("Endereco: " + retorno.logradouro());
            GeradorDeArquivo geradorDeArquivo = new GeradorDeArquivo();
            geradorDeArquivo.salvarJson(retorno);
        }catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("Encerrando...");
        }
    }
}