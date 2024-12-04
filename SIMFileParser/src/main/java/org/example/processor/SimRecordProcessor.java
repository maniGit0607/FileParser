package org.example.processor;

import org.example.entity.Sim;
import org.example.job.SimFileMetadata;
import org.example.repository.SimRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class SimRecordProcessor implements ItemProcessor<Sim, Sim> {

    private final long startIMSI;
    private final long endIMSI;

    @Autowired
    SimRepository simRepository;

    public SimRecordProcessor(SimFileMetadata simFileMetadata) {
        this.startIMSI = simFileMetadata.getStartIMSI();
        this.endIMSI = startIMSI + simFileMetadata.getRange();
    }

    @Override
    public Sim process(Sim item) throws Exception {
        long imsiValue = Long.parseLong(item.getImsi());

        // Check if IMSI is in the valid range
        if (imsiValue < startIMSI || imsiValue > endIMSI) {
            throw new IllegalArgumentException("IMSI out of range: " + imsiValue);
        }

        // Check if IMSI already exists in the database
        long count = simRepository.countByImsi(item.getImsi());

        if (count > 0) {
            throw new IllegalArgumentException("IMSI already exists in database: " + imsiValue);
        }

        return item;
    }
}
