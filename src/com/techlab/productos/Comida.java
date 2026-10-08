package com.techlab.productos;

public class Comida extends Producto {

    private String fechaVencimiento;

    public Comida(String nombre, double precio, int stock, String fechaVencimiento) {
        super(nombre, precio, stock);
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String toString() {
        return super.toString() + " | Fecha de vencimiento: " + fechaVencimiento;
    }
}
