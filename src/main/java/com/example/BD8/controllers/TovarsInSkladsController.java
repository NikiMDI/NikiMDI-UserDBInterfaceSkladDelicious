package com.example.BD8.controllers;

import com.example.BD8.models.Sklad;
import com.example.BD8.models.Tovar;
import com.example.BD8.models.TovarsInSklads;
import com.example.BD8.repositories.SkladRepository;
import com.example.BD8.repositories.TovarRepository;
import com.example.BD8.repositories.TovarsInSkladsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@EnableMethodSecurity
@Controller
@RequiredArgsConstructor
public class TovarsInSkladsController {
    @Autowired
    private final TovarsInSkladsRepository tovarsinskladsRepository;
    @Autowired
    private final TovarRepository tovarRepository;
    @Autowired
    private final SkladRepository skladRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/TovarsInSklads")
    public String allTovarsInSkladss(Model model)
    {
        Iterable<TovarsInSklads> tovarsinsklads = tovarsinskladsRepository.findAll();
        model.addAttribute("TovarsInSklads", tovarsinsklads);
        return "TovarsInSklads";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/TovarsInSklads/create")
    public String postavkaCreate(TovarsInSklads tovarsInSklads)
    {
        return "TovarsInSklads-create";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/TovarsInSklads/create")
    public String postavkaCreate(@RequestParam Long ids, @RequestParam Long idt, Model model)
    {
        Sklad sklad = skladRepository.findById(ids).orElseThrow();
        Tovar tovar = tovarRepository.findById(idt).orElseThrow();
        tovarsinskladsRepository.save(new TovarsInSklads(tovar,sklad));
        return "redirect:/TovarsInSklads";
    }
}
