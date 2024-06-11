package com.example.BD8.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Postavka")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class Postavka
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pdate;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "tovar_id", nullable = false)
    private Tovar tovar;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "postavshik_id", nullable = false)
    private Postavshik postavshik;

    public Postavka(LocalDate pdate,Tovar tovar,Postavshik postavshik)
    {
        this.pdate = pdate;
        this.tovar = tovar;
        this.postavshik = postavshik;
    }
}
