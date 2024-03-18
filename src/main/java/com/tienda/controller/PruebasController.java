/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;

/**
 *
 * @author Laboratorios
 */
import com.tienda.domain.Categoria;
import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
import com.tienda.service.impl.FirebaseStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Laboratorios
 */
@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listado(Model model, Categoria categoria) {
        categoria = categoriaService.getCategoria(categoria);
        var productos = categoriaService.getCategoria(categoria).getProductos();
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado2")
    public String listado2(Model model, Categoria categoria) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "/pruebas/listado2";
    }

    @PostMapping("/query1")
    public String consultaQuery1(
            @RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup,
            Model model) {
        var productos = productoService.meotodoQuery(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query2")
    public String consultaQuery2(
            @RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup,
            Model model) {
        var productos = productoService.meotodoJPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query3")
    public String consultaQuery3(
            @RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup,
            Model model) {
        var productos = productoService.meotodoNativo(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @GetMapping("/listado3")
    public String listado3(Model model, Categoria categoria) {
        var productos = productoService.getProductos(false);
        var categorias=categoriaService.getCategorias(true);
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado3";
    }

    @PostMapping("/buscar")
    public String consultaBuscar(
            @RequestParam(value = "buscar") String buscar,
            Model model) {
        var productos = productoService.buscarProducto(buscar);
        var categorias=categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("buscar", buscar);
        return "/pruebas/listado3";
    }

    @PostMapping("/existencias")
    public String consultaCantidad(
            @RequestParam(value = "cantidad") int cantidad,
            @RequestParam(value = "ordenASC", required=false) String ordenASC,
            Model model) {
        
        boolean orden = false;
        if (ordenASC!=(null) && ordenASC.equals("on")) {
            orden = true;
        }

        var productos = productoService.buscarCantidadDESC(cantidad);
        if (orden) {
            productos.clear();
            productos = productoService.buscarCantidadASC(cantidad);
        }
        var categorias=categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);
        model.addAttribute("cantidad", cantidad);
        model.addAttribute("ordenASC", orden);
        return "/pruebas/listado3";
    }

    @PostMapping("/categorias")
    public String consultaProductosPorCategoria(
            @RequestParam(value = "idCategoria") int categoria,
            Model model) {
        var categorias=categoriaService.getCategorias(true);
        var productos = productoService.buscarProductosPorCategoria(categoria);
        model.addAttribute("productos", productos);
        model.addAttribute("idCategoria", categoria);
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado3";
    }
}
