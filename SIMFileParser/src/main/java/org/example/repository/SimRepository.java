package org.example.repository;

import org.example.entity.Sim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimRepository extends JpaRepository<Sim, Long> {

    long countByImsi(String imsi);
}
