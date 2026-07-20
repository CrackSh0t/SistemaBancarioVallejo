package model;

// Excepción personalizada para controlar sobregiros no autorizados
public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}