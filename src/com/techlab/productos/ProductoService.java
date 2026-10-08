package com.techlab.productos;

import java.util.ArrayList;

public class ProductoService {
    private final ArrayList<Producto> productos = new ArrayList<>();

    public Producto registrar(String nombre, double precio, int stock) {
        return registrar(new Producto(nombre, precio, stock));
    }

    public ArrayList<Producto> listar() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Producto buscarPorNombre(String nombre) {
        if (nombre.isBlank()) return null;
        String comparador = nombre.toLowerCase();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(comparador)) {
                return p;
            }
        }
        return null;
    }

    public boolean eliminar(Producto p) {
        return productos.remove(p);
    }

    public Producto registrar(Producto producto) {
        productos.add(producto);
        return producto;
    }

}
