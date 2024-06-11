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
@Table(name = "Client")
@AllArgsConstructor
@Builder(toBuilder=true)
@NoArgsConstructor
public class Client
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fio")
    private String fio;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "client")
    private List<TovarsOfClients> tovarsofclients = new ArrayList<>();

    public Client(String fio)
    {
        this.fio = fio;
    }
}
