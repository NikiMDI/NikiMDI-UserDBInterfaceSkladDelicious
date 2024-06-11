package com.example.BD8.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TovarCount {
    String tname;
    Long tcount;

    public TovarCount(String tname) {
        this.tname = tname;
    }

    public TovarCount(Long tcount) {
        this.tcount = tcount;
    }
}
