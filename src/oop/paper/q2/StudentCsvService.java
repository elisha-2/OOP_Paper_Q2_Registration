package oop.paper.q2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StudentCsvService {

    private final File csvFile;
    private final Map<Integer, Integer> yearCounters = new HashMap<>();

    public StudentCsvService(String fileName) {
        this.csvFile = new File(fileName);
        loadCounters();
    }

    private void loadCounters() {
        if (!csvFile.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (!line.startsWith("ID:")) continue;

                String idPart = line.split("\\|")[0].trim(); // "ID: 2026-00023"
                String idValue = idPart.replace("ID:", "").trim();

                String[] pieces = idValue.split("-");
                int year = Integer.parseInt(pieces[0]);
                int counter = Integer.parseInt(pieces[1]);

                int currentMax = yearCounters.getOrDefault(year, 0);
                if (counter > currentMax) {
                    yearCounters.put(year, counter);
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

    public String generateId(int year) {
        int next = yearCounters.getOrDefault(year, 0) + 1;
        yearCounters.put(year, next);
        return year + "-" + String.format("%05d", next);
    }

    public void appendRecord(String record) throws IOException {
        boolean fileExists = csvFile.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
            if (!fileExists) {
                bw.write("RECORDS");
                bw.newLine();
            }
            bw.write(record);
            bw.newLine();
        }
    }
}
