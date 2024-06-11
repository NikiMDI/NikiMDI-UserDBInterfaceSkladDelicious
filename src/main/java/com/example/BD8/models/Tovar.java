package com.example.BD8.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Tovar")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class Tovar
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tname")
    private String tname;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "tovar")
    private List<TovarsOfClients> tovarsofclients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "tovar")
    private List<Postavka> postavkas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "tovar")
    private List<TovarsInSklads> tovarsinsklads = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "postavshik_id", nullable = false)
    private Postavshik postavshik;


    public Tovar (String tname, Postavshik postavshik)
    {
        this.tname = tname;
        this.postavshik = postavshik;
    }
}
