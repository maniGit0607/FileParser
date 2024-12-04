package org.example.writer;

import lombok.RequiredArgsConstructor;
import org.example.entity.Sim;
import org.example.repository.SimRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class SimWriter implements ItemWriter<Sim> {

    private final SimRepository simRepository;

    public SimWriter(SimRepository simRepository) {
        this.simRepository = simRepository;
    }

    @Override
    public void write(Chunk<? extends Sim> chunk) throws Exception {
        simRepository.saveAll(chunk.getItems());
    }
}
