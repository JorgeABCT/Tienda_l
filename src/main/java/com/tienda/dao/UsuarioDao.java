package com.tienda.dao;

import com.tienda.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Laboratorios
 */
public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    public Usuario findByUsername(String username);
    
}
