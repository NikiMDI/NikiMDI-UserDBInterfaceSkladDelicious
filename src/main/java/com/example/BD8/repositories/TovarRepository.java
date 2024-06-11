package com.example.BD8.repositories;

import com.example.BD8.models.ITovarCount;
import com.example.BD8.models.Tovar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TovarRepository extends JpaRepository<Tovar, Long>
{
    List<Tovar> findByTname(String tname);
    @Query(value = " SELECT "+
            " tname AS tname, COUNT(tname) AS tcount "+
            " FROM "+
            " TovarsInSklads inner join Tovar on TovarsInSklads.tovar_id = Tovar.id Where Tovar.tname = :tovarname"+
            " GROUP BY tname ",nativeQuery = true)
    List<ITovarCount> tovarcount(@Param("tovarname") String tovarname);
}
