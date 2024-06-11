package com.example.BD8.controllers;

import com.example.BD8.models.Client;
import com.example.BD8.repositories.ClientRepository;
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
public class ClientsController
{
    @Autowired
    private final ClientRepository clientRepository;

    @GetMapping("/Clients")
    public String allClients(Model model)
    {
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("Clients", clients);
        return "Clients";
    }

    @GetMapping("/Clients-find/**")
    public String getClientByfio(@RequestParam(name = "fio", required = false) String fio, Model model)
    {
        if(fio!=null)
        {
            model.addAttribute("Clients", clientRepository.findByFio(fio));
            return "Clients-find";
        }
        else
        {
            model.addAttribute("Clients", clientRepository.findAll());
            return "Clients-find";
        }
    }

    @GetMapping("/Clients/{id}")
    public String getClient(@PathVariable(value = "id") Long id, Model model)
    {
        Optional<Client> client = clientRepository.findById(id);
        ArrayList<Client> res = new ArrayList<>();
        client.ifPresent(res::add);
        model.addAttribute("client", res);
        return "Client-info";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Clients/create")
    public String clientCreate(Client client)
    {
        return "Client-create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Clients/create")
    public String clientCreate(@RequestParam(value = "fio", required = false) String fio, Model model)
    {
        clientRepository.save(new Client(fio));
        return "redirect:/Clients";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/Clients/{id}/delete")
    public String clientDelete(@PathVariable(value="id") Long id)
    {
        Client client = clientRepository.findById(id).orElseThrow();
        clientRepository.delete(client);
        return "redirect:/Clients";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Clients/{id}/edit")
    public String ClientEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Client> client = clientRepository.findById(id);
        ArrayList<Client> res = new ArrayList<>();
        client.ifPresent(res::add);
        model.addAttribute("client",res);
        return "Client-edit";
    }

    @PostMapping("/Clients/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String clientEdit(@PathVariable(value = "id") Long id,@RequestParam(value = "fio", required = false) String fio)
    {
        Client client = clientRepository.findById(id).orElseThrow();
        client.setFio(fio);
        clientRepository.save(client);
        return "redirect:/Clients";
    }
}
