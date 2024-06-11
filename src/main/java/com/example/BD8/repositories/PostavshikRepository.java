package com.example.BD8.repositories;

import com.example.BD8.models.Postavshik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostavshikRepository extends JpaRepository<Postavshik, Long>
{
    List<Postavshik> findByFio(String fio);
}
