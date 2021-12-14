package com.viduarte.dbfJPA.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "table_clinica")
public class Clinica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(name = "nome", length = 64)
    private String nome;

    private String cnpj;
    private String telefone;
    @OneToOne
    private EnderecoClinica enderecoclinica;
    private Boolean isActive;

    public static Clinica parseNote(String line) {
        String[] text = line.split(",");
        Clinica note = new Clinica();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
