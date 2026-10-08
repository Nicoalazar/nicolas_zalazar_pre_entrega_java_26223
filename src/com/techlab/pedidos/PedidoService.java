package com.techlab.pedidos;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.productos.Producto;

import java.util.ArrayList;

public class PedidoService {

    private final ArrayList<Pedido> pedidosRealizados = new ArrayList<>();

    public void registrar(Pedido pedido) throws StockInsuficienteException {
        // Fase 1: validar
        for (LineaPedido l : pedido.getLineas()) {
            Producto p = l.getProducto();
            int cantidadPedida = pedido.cantidadTotalDe(p);
            int stock = p.getStock();
            if (cantidadPedida > stock) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para " + p.getNombre() + " pedido: " + cantidadPedida + ", disponible: " + stock
                );
            }
        }
        // Fase 2: aplicar
        for (LineaPedido l : pedido.getLineas()) {
            Producto p = l.getProducto();
            int restante = p.getStock() - l.getCantidad();
            p.setStock(restante);
        }
        // Guarda el pedido en el historial
        pedidosRealizados.add(pedido);
    }

    public ArrayList<Pedido> listar() {
        return new ArrayList<>(pedidosRealizados);
    }

}
