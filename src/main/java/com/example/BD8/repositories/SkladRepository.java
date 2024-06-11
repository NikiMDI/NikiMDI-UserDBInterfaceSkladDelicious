package com.example.BD8.repositories;

import com.example.BD8.models.Sklad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkladRepository extends JpaRepository<Sklad, Long>
{
    List<Sklad> findBySname(String sname);
}
