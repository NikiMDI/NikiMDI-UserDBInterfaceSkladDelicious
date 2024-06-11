package com.example.BD8.controllers;

import com.example.BD8.models.Client;
import com.example.BD8.models.Tovar;
import com.example.BD8.models.TovarsOfClients;
import com.example.BD8.repositories.ClientRepository;
import com.example.BD8.repositories.TovarRepository;
import com.example.BD8.repositories.TovarsOfClientsRepository;
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
public class TovarsOfClientsController {
    @Autowired
    private final TovarsOfClientsRepository tovarsinclientsRepository;
    @Autowired
    private final TovarRepository tovarRepository;
    @Autowired
    private final ClientRepository clientRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/TovarsOfClients")
    public String allTovarsOfClientss(Model model)
    {
        Iterable<TovarsOfClients> tovarsinclients = tovarsinclientsRepository.findAll();
        model.addAttribute("TovarsOfClients", tovarsinclients);
        return "TovarsOfClients";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/TovarsOfClients/create")
    public String postavkaCreate(TovarsOfClients tovarsInSklads)
    {
        return "TovarsOfClients-create";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/TovarsOfClients/create")
    public String postavkaCreate(@RequestParam Long idc, @RequestParam Long idt, Model model)
    {
        Client client = clientRepository.findById(idc).orElseThrow();
        Tovar tovar = tovarRepository.findById(idt).orElseThrow();
        tovarsinclientsRepository.save(new TovarsOfClients(tovar,client));
        return "redirect:/TovarsOfClients";
    }
}
