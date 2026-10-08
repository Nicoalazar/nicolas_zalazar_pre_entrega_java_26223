package com.techlab.pedidos;

import com.techlab.productos.Producto;

import java.util.ArrayList;

public class Pedido {

    private static int contadorId = 1;

    private final int id;
    private final ArrayList<LineaPedido> lineas = new ArrayList<>();

    public Pedido() {
        this.id = contadorId++;
    }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public ArrayList<LineaPedido> getLineas() {
        return new ArrayList<>(lineas);
    }

    public int getId() {
        return id;
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido l : lineas) {
            total += l.calcularSubtotal();
        }
        return total;
    }

    public int cantidadTotalDe(Producto producto) {
        int total = 0;
        for (LineaPedido l : lineas) {
            if (l.getProducto() == producto) {
                total += l.getCantidad();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id).append("\n");
        for (LineaPedido l : lineas) {
            sb.append("  ").append(l).append("\n");
        }
        sb.append(String.format("  Total: %.2f", calcularTotal()));
        return sb.toString();
    }

}
