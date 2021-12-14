package com.viduarte.dbfJPA.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_Dentista")
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", length = 64)
    private String nome;
    
    @Column(unique = true)
    private String telefone;
    
    @Column(unique = true)
    private String email;
    
    @ManyToOne
    private Clinica clinica;
    
    @Column(unique = true)
    private String cpf;
    
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
    @OneToOne
    private EnderecoDentista endereco;
    
    private Boolean isActive;

    public static Dentista parseNote(String line) {
        String[] text = line.split(",");
        Dentista note = new Dentista();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
}
