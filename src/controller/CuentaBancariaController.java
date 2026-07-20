package controller;

import dao.CuentaBancariaDAOImpl;
import dao.ICuentaBancariaDAO;
import java.util.List;
import model.CuentaBancaria;

/**
 * Controlador que actúa como intermediario entre la Vista (GUI)
 * y el Modelo / Persistencia (DAO).
 */
public class CuentaBancariaController {
    
    private final ICuentaBancariaDAO cuentaDAO;

    public CuentaBancariaController() {
        // Inicializamos el acceso a datos a través de la interfaz
        this.cuentaDAO = new CuentaBancariaDAOImpl();
    }

    /**
     * Obtiene la lista completa de cuentas registradas en la Base de Datos.
     */
    public List<CuentaBancaria> listarCuentas() {
        return cuentaDAO.obtenerTodas();
    }

    /**
     * Procesa un depósito: actualiza el objeto en memoria y persiste en Laragon.
     */
    public boolean depositar(CuentaBancaria cuenta, double monto) {
        if (cuenta == null || monto <= 0) {
            return false;
        }
        
        // 1. Modifica la entidad en Java (Lógica de Negocio)
        cuenta.depositar(monto);
        
        // 2. Persiste el cambio en la Base de Datos mediante el DAO
        return cuentaDAO.actualizarSaldo(cuenta.getNumeroCuenta(), cuenta.getSaldo());
    }

    /**
     * Procesa un retiro: ejecuta la lógica de retiro y persiste en Laragon.
     * Puede lanzar excepciones personalizadas (SaldoInsuficiente, LimiteRetiro, etc.)
     */
    public boolean retirar(CuentaBancaria cuenta, double monto) throws Exception {
        if (cuenta == null || monto <= 0) {
            return false;
        }
        
        // 1. Modifica la entidad en Java (Lanza excepciones si viola reglas bancarias)
        cuenta.retirar(monto);
        
        // 2. Persiste el cambio en la Base de Datos mediante el DAO
        return cuentaDAO.actualizarSaldo(cuenta.getNumeroCuenta(), cuenta.getSaldo());
    }
/**
     * Registra una nueva cuenta en la base de datos mediante el DAO.
     */
    public boolean registrarCuenta(CuentaBancaria cuenta) {
        if (cuenta == null) {
            return false;
        }
        return cuentaDAO.insertar(cuenta);
    }
}