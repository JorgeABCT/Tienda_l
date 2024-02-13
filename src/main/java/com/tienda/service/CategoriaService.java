/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Categoria;
import java.util.List;

/**
 *
 * @author Laboratorios
 */
public interface CategoriaService {
    //Se obtiene un arraylist de objettos tipo Categoria
    public List<Categoria> getCategorias (boolean activos);
}
