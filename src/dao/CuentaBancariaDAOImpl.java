package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.CuentaBancaria;
// Importa tus subclases si es necesario (Ahorros, Corriente, etc.)

public class CuentaBancariaDAOImpl implements ICuentaBancariaDAO {

    @Override
    public boolean insertar(CuentaBancaria cuenta) {
        String sql = "INSERT INTO cuentas (numero_cuenta, titular, saldo, tipo_cuenta, limite_retiro, linea_credito, comision_sobregiro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cuenta.getNumeroCuenta());
            ps.setString(2, cuenta.getTitular());
            ps.setDouble(3, cuenta.getSaldo());
            
            // Evaluamos dinámicamente qué tipo de cuenta estamos guardando para registrar sus atributos específicos
            if (cuenta.getClass().getSimpleName().equals("CuentaAhorros")) {
                ps.setString(4, "Ahorros");
                // Aquí casteas a tu clase de Ahorros para sacar el límite, ejemplo:
                // ps.setDouble(5, ((CuentaAhorros) cuenta).getLimiteRetiro());
                ps.setDouble(5, 500.0); // Valor de ejemplo
                ps.setNull(6, Types.DOUBLE);
                ps.setNull(7, Types.DOUBLE);
            } else {
                ps.setString(4, "Corriente");
                ps.setNull(5, Types.DOUBLE);
                ps.setDouble(6, 1000.0); // Ejemplo línea crédito
                ps.setDouble(7, 50.0);   // Ejemplo comisión
            }
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar cuenta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<CuentaBancaria> listar() {
        List<CuentaBancaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuentas";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Aquí lees los campos rs.getString("titular"), rs.getDouble("saldo"), etc.
                // Reconstruyes los objetos y los añades a la lista
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cuentas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(CuentaBancaria cuenta) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE numero_cuenta = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, cuenta.getSaldo());
            ps.setString(2, cuenta.getNumeroCuenta());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar saldo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(String numeroCuenta) {
        String sql = "DELETE FROM cuentas WHERE numero_cuenta = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cuenta: " + e.getMessage());
            return false;
        }
    }
    @Override
    public List<CuentaBancaria> obtenerTodas() {
        List<CuentaBancaria> lista = new ArrayList<>();
        String sql = "SELECT numero_cuenta, titular, saldo, tipo_cuenta, limite_retiro, linea_credito, comision_sobregiro FROM cuentas";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String num = rs.getString("numero_cuenta");
                String titular = rs.getString("titular");
                double saldo = rs.getDouble("saldo");
                String tipo = rs.getString("tipo_cuenta");

                CuentaBancaria cuenta = null;

                // Instanciamos la subclase correspondiente según el tipo guardado en la BD
                if ("Ahorros".equalsIgnoreCase(tipo)) {
                    double limiteRetiro = rs.getDouble("limite_retiro");
                    // Modifica los parámetros según los constructores de tus clases model si es necesario
                    cuenta = new model.CuentaAhorros(num, titular, saldo, "15/01/2026", 0.02, limiteRetiro);
                } else if ("Corriente".equalsIgnoreCase(tipo)) {
                    double lineaCredito = rs.getDouble("linea_credito");
                    cuenta = new model.CuentaCorriente(num, titular, saldo, "10/02/2026", lineaCredito);
                } else {
                    // Plazo Fijo o genérico
                    cuenta = new model.CuentaPlazoFijo(num, titular, saldo, "01/03/2026", "01/09/2026", 6);
                }

                if (cuenta != null) {
                    lista.add(cuenta);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cuentas: " + e.getMessage());
        }

        return lista;
    }
    @Override
    public boolean actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE numero_cuenta = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, nuevoSaldo);
            ps.setString(2, numeroCuenta);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó el registro con éxito

        } catch (SQLException e) {
            System.out.println("Error al actualizar saldo en BD: " + e.getMessage());
            return false;
        }
    }
}