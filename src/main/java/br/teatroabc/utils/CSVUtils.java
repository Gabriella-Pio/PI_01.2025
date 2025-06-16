package br.teatroabc.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVUtils {

    static File csvCliente = Paths.get("data/BD/Cliente.csv").toFile();
    static File csvVenda = Paths.get("data/BD/Venda.csv").toFile();
    static File csvItemVenda = Paths.get("data/BD/ItemVenda.csv").toFile();
    static File[] choice = new File[]{
        csvCliente, csvVenda, csvItemVenda
    };

    public static List<String[]> readCSV(int i) throws IOException {
        File Chosen = choice[i];
        try (CSVReader reader = new CSVReader(new FileReader(Chosen))) {
            return reader.readAll();
        } catch (CsvException e) {
            throw new IOException("Error parsing CSV", e);
        }
    }


    public static void writeCSV(Path file, String[] data) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file.toFile()))) {
            writer.writeNext(data); // Writes each String[] as a row
        }
    }


    public static void appendCSV(File file, String[] data, int i) throws IOException {
        File Chosen = choice[i];
        try (CSVWriter writer = new CSVWriter(new FileWriter(Chosen, true))) {
            writer.writeNext(data);
        }
    }
}