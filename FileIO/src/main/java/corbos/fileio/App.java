/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author parallels
 */
public class App {

    private static final String DATA_PATH = "data.txt";
    private static final String SIMPLE_PATH = "simple.txt";

    public static void main(String[] args) {
        displayFile(DATA_PATH); // file is relative to the project, so this should work.
        addRecord("Ernie", "Pittman", "ernie.pittman@aol.com", "1111222299998888");
        displayFile(DATA_PATH); // display a second time to confirm our record was added.
        
        simpleWrite();
        System.out.print(simpleRead());
    }

    private static void displayFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine(); // throw out header...
            int recordNumber = 0;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                System.out.printf("Record: %s, Name: %s %s, Email: %s\n", recordNumber++, words[0], words[1], words[2]);

            }
            reader.close(); // files must be closed explicitly.
        } catch (IOException ex) {
            System.out.printf("Error!: %s", ex);
        }
    }

    private static void addRecord(String firstName, String lastName, String email, String creditCard) {
        try (FileWriter fw = new FileWriter(DATA_PATH, true); // the second parameter indicates we append text vs start from scratch
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter writer = new PrintWriter(bw);) {
            writer.printf("\n%s,%s,%s,%s", firstName, lastName, email, creditCard);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.printf("Error!: %s", ex);
        }
    }

    private static String simpleRead() {       
        try {
            return String.join("\n", Files.readAllLines(Paths.get(SIMPLE_PATH)));
        } catch (IOException ex) {
            System.out.printf("Error!: %s", ex);
        }  
        return null;
    }

    private static void simpleWrite() {
        try {
            PrintWriter pw = new PrintWriter(SIMPLE_PATH);
            pw.println("apple");
            pw.println("bananna");
            pw.println("kiwi");
            pw.close();
        } catch (IOException ex) {
            System.out.printf("Error!: %s", ex);
        }
    }
}
