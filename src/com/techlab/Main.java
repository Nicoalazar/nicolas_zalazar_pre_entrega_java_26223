package com.techlab;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.PedidoService;
import com.techlab.productos.Bebida;
import com.techlab.productos.Comida;
import com.techlab.productos.Producto;
import com.techlab.productos.ProductoService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Compartidos por todos los métodos de la clase
    private static final Scanner sc = new Scanner(System.in);
    private static final ProductoService productoService = new ProductoService();
    private static final PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        System.out.println(
                """
                            ===================================
                                SISTEMA DE GESTIÓN - TECHLAB
                            ===================================
                        """
        );

        // Bucle que muestra el menú
        boolean seguir = true;

        while (seguir) {
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Buscar/Actualizar producto");
            System.out.println("4) Eliminar producto");
            System.out.println("5) Crear un pedido");
            System.out.println("6) Listar pedidos");
            System.out.println("7) Salir");

            System.out.print("Elija una opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1" -> agregarProductos();
                case "2" -> listarProductos();
                case "3" -> buscarActualizarProducto();
                case "4" -> eliminarProducto();
                case "5" -> crearPedido();
                case "6" -> listarPedidos();
                case "7" -> {
                    System.out.println("Gracias por su visita");
                    seguir = false;
                }
                default -> System.out.println("Opción inválida");
            }
        }
        sc.close();
    }

    private static void agregarProductos() {
        System.out.print("""
                Seleccione el tipo:
                1- Genérico
                2- Bebida
                3- Comida
                """);
        String tipo = sc.nextLine();
        if (!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("3")) {
            System.out.println("Opción no válida");
            return;
        }
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        double precio = leerDouble("Precio: ");

        int stock = leerEntero("Stock: ");
        try {
            Producto nuevo = null;

            switch (tipo) {
                case "1" -> nuevo = new Producto(nombre, precio, stock);
                case "2" -> {
                    double volumen = leerDouble("Volumen (litros): ");
                    nuevo = new Bebida(nombre, precio, stock, volumen);
                }
                case "3" -> {
                    System.out.print("Fecha de vencimiento: ");
                    String fecha = sc.nextLine();
                    nuevo = new Comida(nombre, precio, stock, fecha);
                }
            }
            productoService.registrar(nuevo);
            System.out.println("Producto agregado: " + nuevo);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static void listarProductos() {
        ArrayList<Producto> listado = productoService.listar();
        if (listado.isEmpty()) {
            System.out.println("No hay productos en la lista");
            return;
        }
        for (Producto p : listado) {
            System.out.println(p);
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("'" + texto + "' no es un número válido");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine();
            String replaced = texto.replace(",", ".");
            try {
                return Double.parseDouble(replaced);
            } catch (NumberFormatException e) {
                System.out.println("'" + texto + "' no es un número válido");
            }
        }

    }

    private static void buscarActualizarProducto() {
        Producto p = null;
        System.out.println("¿Buscar por nombre o por ID?");
        System.out.print(
                """
                            1- ID
                            2- Nombre
                        """);
        String metodo = sc.nextLine();
        switch (metodo) {
            case "1" -> {
                int id = leerEntero("Ingrese ID: ");
                p = productoService.buscarPorId(id);
            }
            case "2" -> {
                System.out.print("Ingrese el nombre: ");
                String nombre = sc.nextLine();
                p = productoService.buscarPorNombre(nombre);
            }
            default -> {
                System.out.println("Opción no válida");
                return;
            }
        }
        if (p == null) {
            System.out.println("El producto no existe");
            return;
        }
        System.out.println(p);
        System.out.println("¿Qué desea actualizar?");
        System.out.print(
                """
                            1- Precio
                            2- Stock
                            3- Nada
                        """);
        String actualiza = sc.nextLine();
        switch (actualiza) {
            case "1" -> {
                double valor = leerDouble("Precio: ");
                try {
                    p.setPrecio(valor);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case "2" -> {
                int valor = leerEntero("Stock: ");
                try {
                    p.setStock(valor);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case "3" -> System.out.println("No se actualizó nada");
            default -> System.out.println("Opción no válida");
        }
        System.out.println(p);

    }

    private static void eliminarProducto() {
        int id = leerEntero("Ingrese ID: ");
        Producto p = productoService.buscarPorId(id);
        if (p == null) {
            System.out.println("El producto no existe");
            return;
        }
        System.out.println(p);
        System.out.print("¿Confirma que desea eliminar? s/n ");
        String respuesta = sc.nextLine();
        switch (respuesta.toLowerCase()) {
            case "s" -> {
                if (!productoService.eliminar(p)) {
                    System.out.println("Producto no pudo ser eliminado");
                    return;
                }
                System.out.println("Producto eliminado");
            }
            case "n" -> System.out.println("Producto no eliminado");
            default -> System.out.println("Opción no válida");
        }
    }

    private static void crearPedido() {
        if (productoService.listar().isEmpty()) {
            System.out.println("No hay productos cargados");
            return;
        }
        Pedido nuevoPedido = new Pedido();
        int cantidadProductos = leerEntero("¿Cuántos productos distintos va a registrar? ");
        for (int i = 1; i <= cantidadProductos; i++) {
            int id = leerEntero("Ingrese el Id del producto: ");
            Producto producto = productoService.buscarPorId(id);
            if (producto == null) {
                System.out.println("El producto no existe");
                continue;
            }
            int cantidad = leerEntero("Ingrese la cantidad: ");
            if (cantidad <= 0) {
                System.out.println("Ingrese una cantidad válida");
                continue;
            }
            int stock = producto.getStock();
            if (cantidad > stock) {
                System.out.println("No hay stock suficiente");
                continue;
            }
            LineaPedido nuevaLinea = new LineaPedido(producto, cantidad);
            nuevoPedido.agregarLinea(nuevaLinea);
        }
        if (nuevoPedido.getLineas().isEmpty()) {
            System.out.println("El pedido está vacío");
            return;
        }
        System.out.println("Pedido creado: " + nuevoPedido);
        System.out.print("¿Es correcto? s/n ");
        String respuesta = sc.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            try {
                pedidoService.registrar(nuevoPedido);
                System.out.println("Pedido confirmado");
            } catch (StockInsuficienteException e) {
                System.out.println("No se pudo completar: " + e.getMessage());
            }
        } else {
            System.out.println("Pedido descartado");
        }
    }

    private static void listarPedidos() {
        ArrayList<Pedido> pedidos = pedidoService.listar();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos creados");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }
}