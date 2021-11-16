package autotelega;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.valueOf;

public class PathDocument {
        private String path ="";
        PathDocument(){
        }

        public String getPathDocument() {
            path ="";
            try (FileReader reader = new FileReader("notes4.txt")) {
                int c;
                while ((c = reader.read()) != -1) {
                    path = path + valueOf((char) c);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return path;
        }

        public void setPathDocument(String path) {
            this.path = path;
        }

}




