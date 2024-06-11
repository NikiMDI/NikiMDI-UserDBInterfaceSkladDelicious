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
@Table(name = "Sklad")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class Sklad
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sname")
    private String sname;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "sklad")
    private List<TovarsInSklads> tovarsinsklads = new ArrayList<>();

    public Sklad(String sname)
    {
        this.sname = sname;
    }
}
