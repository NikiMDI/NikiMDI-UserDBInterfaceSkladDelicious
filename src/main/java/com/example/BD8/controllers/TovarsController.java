package com.example.BD8.controllers;

import com.example.BD8.models.Postavshik;
import com.example.BD8.models.Tovar;
import com.example.BD8.repositories.PostavshikRepository;
import com.example.BD8.repositories.TovarRepository;
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
public class TovarsController
{
    @Autowired
    private final TovarRepository tovarRepository;
    @Autowired
    private final PostavshikRepository postavshikRepository;


    @GetMapping("/Tovars")
    public String allTovars(Model model)
    {
        Iterable<Tovar> tovars = tovarRepository.findAll();
        model.addAttribute("Tovars", tovars);
        return "Tovars";
    }

    @GetMapping("/Tovars-find/**")
    public String getTovarBytname(@RequestParam(name = "tname", required = false) String tname, Model model)
    {
        if(tname!=null)
        {
            model.addAttribute("Tovars", tovarRepository.findByTname(tname));
            return "Tovars-find";
        }
        else
        {
            model.addAttribute("Tovars", tovarRepository.findAll());
            return "Tovars-find";
        }
    }

    @GetMapping("/Tovars/{id}")
    public String getTovar(@PathVariable(value = "id") Long id, Model model)
    {
        Optional<Tovar> tovar = tovarRepository.findById(id);
        ArrayList<Tovar> res = new ArrayList<>();
        tovar.ifPresent(res::add);
        model.addAttribute("tovar", res);
        return "Tovar-info";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Tovars/create")
    public String tovarCreate(Tovar tovar)
    {
        return "Tovar-create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Tovars/create")
    public String tovarCreate(@RequestParam(value = "tname", required = false) String tname,@RequestParam Long id, Model model)
    {
        Postavshik postavshik = postavshikRepository.findById(id).orElseThrow();
        tovarRepository.save(new Tovar(tname,postavshik));
        return "redirect:/Tovars";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Tovars/{id}/delete")
    public String tovarDelete(@PathVariable(value="id") Long id)
    {
        Tovar tovar = tovarRepository.findById(id).orElseThrow();
        tovarRepository.delete(tovar);
        return "redirect:/Tovars";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Tovars/{id}/edit")
    public String TovarEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Tovar> tovar = tovarRepository.findById(id);
        ArrayList<Tovar> res = new ArrayList<>();
        tovar.ifPresent(res::add);
        model.addAttribute("tovar",res);
        return "Tovar-edit";
    }

    @PostMapping("/Tovars/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String tovarEdit(@PathVariable(value = "id") Long id,@RequestParam(value = "tname", required = false) String tname,@RequestParam Long idpostavshik)
    {
        Tovar tovar = tovarRepository.findById(id).orElseThrow();
        tovar.setTname(tname);
        Postavshik postavshik = postavshikRepository.findById(idpostavshik).orElseThrow();
        tovar.setPostavshik(postavshik);
        tovarRepository.save(tovar);
        return "redirect:/Tovars";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/counttovarsinsklads/**")
    public String getcountTovarInSklads(@RequestParam(value = "tname", required = false) String tname,Model model) {
        model.addAttribute("counttovarsinsklads",tovarRepository.tovarcount(tname));
        return "counttovarsinsklads";
    }

}
