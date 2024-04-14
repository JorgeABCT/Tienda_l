/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service.impl;

import com.tienda.dao.FacturaDao;
import com.tienda.dao.ProductoDao;
import com.tienda.dao.VentaDao;
import com.tienda.domain.Factura;
import com.tienda.domain.Item;
import com.tienda.domain.Producto;
import com.tienda.domain.Usuario;
import com.tienda.domain.Venta;
import com.tienda.service.ItemService;
import com.tienda.service.UsuarioService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Laboratorios
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> gets() {
        return listaItems;
    }

    //Busca en array, si lo encuentra lo devuelve, sino retorna null
    @Override
    public Item get(Item item) {
        for (Item i : listaItems) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void save(Item item) {
        boolean existe = false;
        for (Item i : listaItems) {
//Busca si ya existe el producto en el carrito
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
//Valida si aun puede colocar un item adicional -segun existencias-
                if (i.getCantidad() < item.getExistencias()) {
//Incrementa en 1 la cantidad de elementos
                    i.setCantidad(i.getCantidad() + 1);
                }

                existe = true;
                break;
            }
        }
        if (!existe) {//Si no está el producto en el carrito se agrega cantidad =1.
            item.setCantidad(1);
            listaItems.add(item);
        }
    }

    @Override
    public void delete(Item item) {
        var posicion = -1;
        var existe = false;
        for (Item i : listaItems) {
            posicion++;
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                existe = true;
                break;
            }
        }
        if (existe) {
            listaItems.remove(posicion);
        }
    }

    @Override
    public void update(Item item) {
        for (Item i : listaItems) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                i.setCantidad(item.getCantidad());
                break;
            }
        }
    }
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FacturaDao facturaDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private ProductoDao productoDao;

    @Override
    public void facturar() {
        String username;
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();

            if (username.isBlank()) {
                return;
            }
            Usuario usuario = usuarioService.getUsuarioPorUsername(username);
            if (usuario == null) {
                return;
            }
            Factura factura = new Factura(usuario.getIdUsuario());
            factura = facturaDao.save(factura);
            double total = 0;
            for (Item i : listaItems) {
                System.out.println("Producto: " + i.getDescripcion()
                        + " Cantidad: " + i.getCantidad()
                        + " Total: " + i.getPrecio() * i.getCantidad());
                Venta venta = new Venta(factura.getIdFactura(), i.getIdProducto(), i.getPrecio(), i.getCantidad());
                ventaDao.save(venta);
                Producto producto = productoDao.getReferenceById(i.getIdProducto());
                producto.setExistencias(producto.getExistencias() - i.getCantidad());
                productoDao.save(producto);
                total += i.getPrecio() * i.getCantidad();

                factura.setTotal(total);
                facturaDao.save(factura);
                listaItems.clear();
            }

        }
    }
}
