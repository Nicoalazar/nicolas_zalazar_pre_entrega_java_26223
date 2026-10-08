package com.techlab.pedidos;

import com.techlab.productos.Producto;

public class LineaPedido {
    private final Producto producto;
    private final int cantidad;
    private final double precioUnitario;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return String.format("Nombre: %s | Cantidad: %d | Subtotal: %.2f", producto.getNombre(), cantidad, calcularSubtotal());
    }
}
