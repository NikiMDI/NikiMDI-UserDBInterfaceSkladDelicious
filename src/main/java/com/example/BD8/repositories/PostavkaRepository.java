package com.example.BD8.repositories;

import com.example.BD8.models.Postavka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostavkaRepository extends JpaRepository<Postavka, Long>
{
    List<Postavka> findByPdate(LocalDate pdate);
    List<Postavka> findAllByPdateBetween(LocalDate start, LocalDate end);
}
