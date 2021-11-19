package com.viduarte.dbfJPA.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_Dentista")
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;

    private Instant dataNascimento;
    private String cpf;
    private String telefone;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String uf;
    private Boolean isActive;

    public static Dentista parseNote(String line) {
        String[] text = line.split(",");
        Dentista note = new Dentista();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
