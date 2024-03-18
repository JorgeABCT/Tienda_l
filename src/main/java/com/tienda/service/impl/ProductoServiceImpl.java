/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service.impl;

import com.tienda.dao.ProductoDao;
import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Laboratorios
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        var lista = productoDao.findAll();
        if (activos) {
            lista.removeIf(c -> !c.isActivo());
        }
        return lista;
    }

    @Override
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> meotodoQuery(double precioInf, double precioSup) {
        return productoDao.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> meotodoJPQL(double precioInf, double precioSup) {
        return productoDao.meotodoJPQL(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> meotodoNativo(double precioInf, double precioSup) {
        return productoDao.meotodoNativo(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProducto(String buscar) {
        return productoDao.buscarProducto(buscar);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarCantidadASC(int cantidad) {
        return productoDao.buscarCantidadASC(cantidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarCantidadDESC(int cantidad) {
        return productoDao.buscarCantidadDESC(cantidad);
    }

    @Override
    public List<Producto> buscarProductosPorCategoria(int categoria) {
        return productoDao.buscarProductosPorCategoria(categoria);
    }

}
