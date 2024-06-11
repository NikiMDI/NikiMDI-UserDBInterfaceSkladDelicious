package com.example.BD8.controllers;

import com.example.BD8.models.Postavka;
import com.example.BD8.models.Postavshik;
import com.example.BD8.models.Tovar;
import com.example.BD8.repositories.PostavkaRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@EnableMethodSecurity
@Controller
@RequiredArgsConstructor
public class PostavkasController
{
    @Autowired
    private final PostavkaRepository postavkaRepository;
    @Autowired
    private final TovarRepository tovarRepository;
    @Autowired
    private final PostavshikRepository postavshikRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Postavkas")
    public String allPostavkas(Model model)
    {
        Iterable<Postavka> postavkas = postavkaRepository.findAll();
        model.addAttribute("Postavkas", postavkas);
        return "Postavkas";
    }

    @GetMapping("/Postavkas-find/**")
    public String getTovarBypdate(@RequestParam(name = "pdate", required = false) LocalDate pdate, Model model)
    {
        if(pdate!=null)
        {
            model.addAttribute("Postavkas", postavkaRepository.findByPdate(pdate));
            return "Postavkas-find";
        }
        else
        {
            model.addAttribute("Postavkas", postavkaRepository.findAll());
            return "Postavkas-find";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Postavkas/create")
    public String postavkaCreate(Postavka postavka)
    {
        return "Postavka-create";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Postavkas/create")
    public String postavkaCreate(@RequestParam(value = "pdate",required = false) LocalDate pdate, @RequestParam Long idp, @RequestParam Long idt, Model model)
    {
        Postavshik postavshik = postavshikRepository.findById(idp).orElseThrow();
        Tovar tovar = tovarRepository.findById(idt).orElseThrow();
        postavkaRepository.save(new Postavka(pdate,tovar,postavshik));
        return "redirect:/Postavkas";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Postavkas/{id}/delete")
    public String postavkaDelete(@PathVariable(value="id") Long id)
    {
        Postavka postavka = postavkaRepository.findById(id).orElseThrow();
        postavkaRepository.delete(postavka);
        return "redirect:/Postavkas";
    }

    @PostMapping("/Postavkas/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String postavkaEdit(@PathVariable(value = "id") Long id,@RequestParam(value = "pdate", required = false) LocalDate pdate, @RequestParam Long idp, @RequestParam Long idt)
    {
        Postavka postavka = postavkaRepository.findById(id).orElseThrow();
        postavka.setPdate(pdate);
        Postavshik postavshik = postavshikRepository.findById(idp).orElseThrow();
        postavka.setPostavshik(postavshik);
        Tovar tovar = tovarRepository.findById(idt).orElseThrow();
        postavka.setTovar(tovar);
        postavkaRepository.save(postavka);
        return "redirect:/Postavkas";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Postavkas/{id}/edit")
    public String postavkaEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Postavka> postavka = postavkaRepository.findById(id);
        ArrayList<Postavka> res = new ArrayList<>();
        postavka.ifPresent(res::add);
        model.addAttribute("postavka",res);
        return "Postavka-edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/postavkasindiapozon/**")
    public String getpostavkasInDiapozon(@RequestParam(value = "start",required = false) LocalDate start,@RequestParam(value = "end",required = false) LocalDate end,Model model) {
        model.addAttribute("postavki", postavkaRepository.findAllByPdateBetween(start,end));
        return "postavkasindiapozon";
    }
}
