import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.valueOf;

public class BotKey {
    private String key="";
    BotKey(){
    }

    public String getKey() {
        key ="";
        try (FileReader reader = new FileReader("notes3.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                key += valueOf((char) c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
