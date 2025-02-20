package process;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    public void write_to_file(String nameFile, String dataFile) {
        try (FileWriter write = new FileWriter(nameFile)) {
            write.write(dataFile);
            System.out.println("Dados salvos com sucesso em arquivo: " + nameFile);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
