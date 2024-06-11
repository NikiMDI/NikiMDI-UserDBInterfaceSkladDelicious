package com.example.BD8.controllers;

import com.example.BD8.models.Sklad;
import com.example.BD8.repositories.SkladRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;


@EnableMethodSecurity
@Controller
@RequiredArgsConstructor
public class SkladsController
{
    @Autowired
    private final SkladRepository skladRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Sklads")
    public String allSklads(Model model)
    {
        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("Sklads", sklads);
        return "Sklads";
    }
    @GetMapping("/Sklads-find/**")
    public String getSkladBysname(@RequestParam(name = "sname", required = false) String sname, Model model)
    {
        if(sname!=null)
        {
            model.addAttribute("Sklads", skladRepository.findBySname(sname));
            return "Sklads-find";
        }
        else
        {
            model.addAttribute("Sklads", skladRepository.findAll());
            return "Sklads-find";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Sklads/create")
    public String skladCreate(Sklad sklad)
    {
        return "Sklad-create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Sklads/create")
    public String skladCreate(@RequestParam(value = "sname", required = false) String sname, Model model)
    {
        skladRepository.save(new Sklad(sname));
        return "redirect:/Sklads";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Sklads/{id}/edit")
    public String SkladEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Sklad> sklad = skladRepository.findById(id);
        ArrayList<Sklad> res = new ArrayList<>();
        sklad.ifPresent(res::add);
        model.addAttribute("sklad",res);
        return "Sklad-edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Sklads/{id}/delete")
    public String skladDelete(@PathVariable(value="id") Long id)
    {
        Sklad sklad = skladRepository.findById(id).orElseThrow();
        skladRepository.delete(sklad);
        return "redirect:/Sklads";
    }

    @PostMapping("/Sklads/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String skladEdit(@PathVariable(value = "id") Long id,@RequestParam(value = "sname", required = false) String sname)
    {
        Sklad sklad = skladRepository.findById(id).orElseThrow();
        sklad.setSname(sname);
        skladRepository.save(sklad);
        return "redirect:/Sklads";
    }
}
