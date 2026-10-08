package com.techlab.productos;

public class Bebida extends Producto {

    private double volumenLitros;

    public Bebida(String nombre, double precio, int stock, double volumenLitros) {
        super(nombre, precio, stock);
        this.volumenLitros = volumenLitros;
    }

    public double getVolumenLitros() {
        return volumenLitros;
    }

    @Override
    public String toString() {
        return super.toString() + " | Volumen: " + volumenLitros + " L";
    }
}
