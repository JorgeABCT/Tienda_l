/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Producto;
import java.util.List;

/**
 *
 * @author Laboratorios
 */
public interface ProductoService {
    // Se obtiene un listado de productos en un List
    public List<Producto> getProductos(boolean activos);
    
   // Se obtiene un Producto, a partir del id de un producto
    public Producto getProducto(Producto producto);
    
    // Se inserta un nuevo producto si el id del producto esta vacío
    // Se actualiza un producto si el id del producto NO esta vacío
    public void save(Producto producto);
    
    // Se elimina el producto que tiene el id pasado por parámetro
    public void delete(Producto producto);
    
    //Esta consulta ampliada utiliza metodo jQuery
    public List<Producto> meotodoQuery(double precioInf, double precioSup);

     //Esta consulta utiliza metodo lenguaje JPQL
    public List<Producto> meotodoJPQL(double precioInf, double precioSup);
    
    //Esta consulta utiliza metodo lenguaje SQL
    public List<Producto> meotodoNativo(double precioInf, double precioSup);
    
    public List<Producto> buscarProducto(String buscar);
    
    public List<Producto> buscarCantidadASC(int cantidad);
    
    public List<Producto> buscarCantidadDESC(int cantidad);
    
    public List<Producto> buscarProductosPorCategoria(int categoria);
}
