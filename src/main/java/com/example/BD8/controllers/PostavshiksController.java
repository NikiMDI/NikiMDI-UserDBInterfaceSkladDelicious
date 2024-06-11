package com.example.BD8.controllers;

import com.example.BD8.models.Postavshik;
import com.example.BD8.repositories.PostavkaRepository;
import com.example.BD8.repositories.PostavshikRepository;
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
public class PostavshiksController
{
    @Autowired
    private final PostavshikRepository postavshikRepository;
    @Autowired
    private final PostavkaRepository postavkaRepository;

    @GetMapping("/Postavshiks")
    public String allPostavshiks(Model model)
    {
        Iterable<Postavshik> postavshiks = postavshikRepository.findAll();
        model.addAttribute("Postavshiks", postavshiks);
        return "Postavshiks";
    }

    @GetMapping("/Postavshiks-find/**")
    public String getPostavshikByfio(@RequestParam(name = "fio", required = false) String fio, Model model)
    {
        if(fio!=null)
        {
            model.addAttribute("Postavshiks", postavshikRepository.findByFio(fio));
            return "Postavshiks-find";
        }
        else
        {
            model.addAttribute("Postavshiks", postavshikRepository.findAll());
            return "Postavshiks-find";
        }
    }

    @GetMapping("/Postavshiks/{id}")
    public String getPostavshik(@PathVariable(value = "id") Long id, Model model)
    {
        Optional<Postavshik> postavshik = postavshikRepository.findById(id);
        ArrayList<Postavshik> res = new ArrayList<>();
        postavshik.ifPresent(res::add);
        model.addAttribute("postavshik", res);
        return "Postavshik-info";
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Postavshiks/create")
    public String postavshikCreate(Postavshik postavshik)
    {
        return "Postavshik-create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Postavshiks/create")
    public String postavshikCreate(@RequestParam(value = "fio", required = false) String fio,  Model model)
    {
        postavshikRepository.save(new Postavshik(fio));
        return "redirect:/Postavshiks";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Postavshiks/{id}/edit")
    public String PostavshikEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Postavshik> postavshik = postavshikRepository.findById(id);
        ArrayList<Postavshik> res = new ArrayList<>();
        postavshik.ifPresent(res::add);
        model.addAttribute("postavshik",res);
        return "Postavshik-edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Postavshiks/{id}/delete")
    public String postavshikDelete(@PathVariable(value="id") Long id)
    {
        Postavshik postavshik = postavshikRepository.findById(id).orElseThrow();
        postavshikRepository.delete(postavshik);
        return "redirect:/Postavshiks";
    }

    @PostMapping("/Postavshiks/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String postavshikEdit(@PathVariable(value = "id") Long id,@RequestParam(value = "fio", required = false) String fio)
    {
        Postavshik postavshik = postavshikRepository.findById(id).orElseThrow();
        postavshik.setFio(fio);
        postavshikRepository.save(postavshik);
        return "redirect:/Postavshiks";
    }
}
