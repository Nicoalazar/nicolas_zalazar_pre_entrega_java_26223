package com.techlab.productos;

public class Producto {

    private static int contadorId = 1;

    private final int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        setPrecio(precio);
        setStock(stock);
        this.id = contadorId++;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo: " + precio);
        }
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo: " + stock);
        }
        this.stock = stock;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Precio: %.2f | Stock: %d", id, nombre, precio, stock);
    }
}

