import model.Endereco;
import process.ConsultaCEP;

public class main {
    public static void main(String[] args) {

        ConsultaCEP consulta = new ConsultaCEP();
        Endereco retorno = consulta.buscaEndereco("0");

        System.out.println("Endereco: " + retorno.logradouro());
    }
}
