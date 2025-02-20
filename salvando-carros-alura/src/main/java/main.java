import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Veiculo;

public class main {
    public static void main(String[] args) {

        Veiculo veiculo = new Veiculo("Onix", "Chevrolete", 2020, true);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(veiculo);

        System.out.println(json);



    }
}
