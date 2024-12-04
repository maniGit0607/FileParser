package org.example.FileReader;

import org.example.entity.Sim;
import org.example.job.SimFileMetadata;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SimFileReader implements ItemReader<Sim> {
    private final String filePath;
    private List<Sim> records;
    private int currentIndex;

    private final SimFileMetadata simFileMetadata;

    public SimFileReader(String filePath, SimFileMetadata simFileMetadata) {
        this.filePath = filePath;
        this.records = new ArrayList<>();
        this.currentIndex = 0;
        this.simFileMetadata = simFileMetadata;
        loadRecords();
    }

    private void loadRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String imsi = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Quantity :")) {
                    simFileMetadata.setRange(Long.parseLong(line.split(":")[1].trim()));
                }

                if (line.startsWith("IMSI :")) {
                    simFileMetadata.setStartIMSI(Long.parseLong(line.split(":")[1].trim()));
                }

                if (line.startsWith("var out:")) {
                    reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        String[] fields = line.split(",");
                        Sim record = new Sim();
                        record.setImsi(fields[1].trim());
                        record.setPin1(fields[2].trim());
                        record.setPuk1(fields[3].trim());
                        record.setPin2(fields[4].trim());
                        record.setPuk2(fields[5].trim());
                        record.setAam1(fields[6].trim());
                        record.setKiUmtsEnc(fields[7].trim());
                        record.setAcc(fields[8].trim());
                        records.add(record);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sim read() {
        if (currentIndex < records.size()) {
            return records.get(currentIndex++);
        }
        return null;
    }
}
