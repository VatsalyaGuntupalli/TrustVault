package com.trustvault.trustvault.controller;
import com.trustvault.trustvault.repository.VaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private VaultRepository vaultRepository;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalVaults", vaultRepository.count());
        model.addAttribute("vaults", vaultRepository.findAll());
        return "dashboard";
    }
}