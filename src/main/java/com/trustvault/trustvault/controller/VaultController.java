package com.trustvault.trustvault.controller;

import com.trustvault.trustvault.model.Vault;
import com.trustvault.trustvault.repository.VaultRepository;
import com.trustvault.trustvault.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    private VaultRepository repo;

    @GetMapping("/add")
    @ResponseBody
    public String addFormDirect() {
        return """
            <!DOCTYPE html>
            <html>
            <head><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"></head>
            <body class="bg-light">
            <div class="container mt-5"><div class="col-md-6 mx-auto">
            <div class="card p-4 shadow">
            <h3>Add New Vault 🔐</h3>
            <form action="/vault/save" method="get">
            <input name="title" class="form-control mb-3" placeholder="Title - Ex: Gmail" required>
            <input name="username" class="form-control mb-3" placeholder="Username" required>
            <input name="encryptedPassword" class="form-control mb-3" placeholder="Password" required>
            <input name="website" class="form-control mb-3" placeholder="Website">
            <button class="btn btn-success w-100">Save Encrypted 🔒</button>
            </form>
            <a href="/dashboard" class="btn btn-secondary w-100 mt-2">Back</a>
            </div></div></div>
            </body></html>
            """;
    }

    @GetMapping("/save")
    public String save(Vault vault) throws Exception {
        String encrypted = EncryptionUtil.encrypt(vault.getEncryptedPassword());
        vault.setEncryptedPassword(encrypted);
        repo.save(vault);
        return "redirect:/dashboard";
    }
    @GetMapping("/view/{id}")
    @ResponseBody
    public String viewVault(@PathVariable Long id) throws Exception {
        Vault v = repo.findById(id).orElse(null);
        if(v == null) return "Not found";
        String decrypted = EncryptionUtil.decrypt(v.getEncryptedPassword());
        return "<div style='margin:50px;font-family:sans-serif'><h2>🔓 Decrypted Vault</h2><p><b>Title:</b> "+v.getTitle()+"<p><b>Username:</b> "+v.getUsername()+"<p><b>Password:</b> <span style='color:green;font-size:20px'>"+decrypted+"</span><p><b>Encrypted in DB:</b> "+v.getEncryptedPassword()+"<br><br><a href=\"/dashboard\">Back to Dashboard</a></div>";
    }
}