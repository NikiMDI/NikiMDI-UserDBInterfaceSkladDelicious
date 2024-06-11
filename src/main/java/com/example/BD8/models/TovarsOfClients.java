package com.example.BD8.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "TovarsOfClients")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class TovarsOfClients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "tovar_id", nullable = false)
    private Tovar tovar;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public TovarsOfClients(Tovar tovar,Client client)
    {
        this.tovar = tovar;
        this.client = client;
    }
}
