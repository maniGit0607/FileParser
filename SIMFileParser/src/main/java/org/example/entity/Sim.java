package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Sim {

    @Id
    private String imsi;

    private String pin1;
    private String puk1;
    private String pin2;
    private String puk2;
    private String aam1;
    private String kiUmtsEnc;
    private String acc;
}
