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
@Table(name = "Postavshik")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class Postavshik
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "id")
    private Long id;

    @Column(name = "fio")
    private String fio;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "postavshik")
    private List<Tovar> tovars = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "postavshik")
    private List<Postavka> postavkas = new ArrayList<>();

    public Postavshik(String fio)
    {
        this.fio = fio;
    }
}
