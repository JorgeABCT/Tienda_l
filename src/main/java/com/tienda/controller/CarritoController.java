/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;

import com.tienda.domain.Item;
import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ItemService;
import com.tienda.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Laboratorios
 */
@Controller
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/carrito/agregar/{idProducto}")
    public ModelAndView agregarItem(Model model, @PathVariable("idProducto") Long idProducto) {
        Producto getProducto = new Producto();
        getProducto.setIdProducto(idProducto);
        Item item = new Item(getProducto);
        Item item2 = itemService.get(item);
        System.out.println("Dividir aca");
        if (item2 == null) {//No existe el producto en el carrito
            Producto producto = productoService.getProducto(item);
            System.out.println("Dividir aca");
            item2 = new Item(producto);
        }

        itemService.save(item2);

        var lista = itemService.gets();
        var totalCarrito = 0;
        var carritoTotalVenta = 0;

        for (Item i : lista) {
            totalCarrito += i.getCantidad();
            carritoTotalVenta += (i.getCantidad() * i.getPrecio());
        }
        model.addAttribute("listaItems", lista);
        model.addAttribute("listaTotal", totalCarrito);
        model.addAttribute("carritoTotal", carritoTotalVenta);

        return new ModelAndView("/carrito/fragmento :: verCarrito");
    }

    @GetMapping("/carrito/listado")
    public String listado(Model model) {
        var items = itemService.gets();
        model.addAttribute("items", items);

        var carritoTotalVenta = 0;
        for (Item i : items) {
            carritoTotalVenta += (i.getCantidad() * i.getPrecio());
        }
        model.addAttribute("carritoTotal", carritoTotalVenta);
        return "/carrito/listado";
    }

    @GetMapping("/carrito/modificar/{idProducto}")
    public String modificarItem(Item item, Model model) {
        item = itemService.get(item);
        model.addAttribute("item", item);
        return "/carrito/modificar";
    }

    @GetMapping("/carrito/eliminar/{idProducto}")
    public String eliminarItem(Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

//Para actualizar un producto del carrito (cantidad)
    @PostMapping("/carrito/guardar")
    public String guardarItem(Item item) {
        itemService.update(item);
        return "redirect:/carrito/listado";
    }

//Para facturar los productos del carrito ... no implementado ...
    @GetMapping("/facturar/carrito")
    public String facturarCarrito() {
        itemService.facturar();
        return "redirect:/";
    }
}
