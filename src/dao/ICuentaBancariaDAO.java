package dao;

import java.util.List;
import model.CuentaBancaria; 

public interface ICuentaBancariaDAO {
    boolean insertar(CuentaBancaria cuenta);     // CREATE
    List<CuentaBancaria> listar();               // READ
    boolean actualizar(CuentaBancaria cuenta);   // UPDATE
    boolean eliminar(String numeroCuenta);       // DELETE
    List<CuentaBancaria> obtenerTodas();
    boolean actualizarSaldo(String numeroCuenta, double nuevoSaldo);
}
