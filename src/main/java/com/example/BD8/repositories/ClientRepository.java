package com.example.BD8.repositories;

import com.example.BD8.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>
{
    List<Client> findByFio(String fio);
}
