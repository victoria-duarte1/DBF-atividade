package com.viduarte.dbfJPA.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_Pedido")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nomePaciente;
    private String nomeDentista;
    
    @ManyToOne
    private Dentista dentista;
    
    private String descricao;
    
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    
    @Temporal(TemporalType.DATE)
    private Date dataEntrega;
    
    private Float valor;
    
    private Boolean isActive;

    public static Pedido parseNote(String line) {
        String[] text = line.split(",");
        Pedido note = new Pedido();
        note.setId(Long.parseLong(text[0]));
       // note.setNomePaciente(text[1]);
       // note.setNomeDentista(text[1]);
        return note;
    }
}
