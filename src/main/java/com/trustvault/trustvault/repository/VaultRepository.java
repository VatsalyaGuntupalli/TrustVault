package com.trustvault.trustvault.repository;
import com.trustvault.trustvault.model.Vault;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VaultRepository extends JpaRepository<Vault, Long> {}