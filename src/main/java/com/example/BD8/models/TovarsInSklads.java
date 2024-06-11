package com.example.BD8.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TovarsInSklads")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class TovarsInSklads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "sklad_id", nullable = false)
    private Sklad sklad;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "tovar_id", nullable = false)
    private Tovar tovar;

    public TovarsInSklads(Tovar tovar,Sklad sklad)
    {
        this.tovar = tovar;
        this.sklad = sklad;
    }
}
