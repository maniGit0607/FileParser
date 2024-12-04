package org.example.job;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SimFileMetadata {
    private long startIMSI;
    private long range;
}