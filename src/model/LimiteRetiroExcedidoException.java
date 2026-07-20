package model;

// Excepción personalizada para controlar topes físicos por operación
public class LimiteRetiroExcedidoException extends Exception {
    public LimiteRetiroExcedidoException(String mensaje) {
        super(mensaje);
    }
}