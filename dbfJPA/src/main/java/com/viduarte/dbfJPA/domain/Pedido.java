package com.viduarte.dbfJPA.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_Pedido")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome_Paciente;
    private String nome_Dentista;
    private String descricao;
    private String dataEmissao;
    private String valor;
    private Boolean isActive;

    public static Pedido parseNote(String line) {
        String[] text = line.split(",");
        Pedido note = new Pedido();
        note.setId(Long.parseLong(text[0]));
       // note.setNome(text[1]);
        return note;
    }
}
