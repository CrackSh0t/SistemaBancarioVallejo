package model;

public abstract class CuentaBancaria implements ProductoFinanciero {
    protected String numeroCuenta;
    protected String titular; //Se agrega titular para el PAC06
    protected double saldo;
    protected String fechaApertura;
    protected boolean estado;

    // CONSTRUCTOR CORREGIDO (Sin espacios en saldoInicial)
    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial, String fechaApertura) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular; // Se asigna titular de la cuenta
        this.saldo = saldoInicial;
        this.fechaApertura = fechaApertura;
        this.estado = true;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            System.out.println("Deposito exitoso de S/. " + monto + " en la cuenta " + numeroCuenta);
        } else {
            System.out.println("Error: El monto a depositar debe ser mayor a cero.");
        }
    }

// (Actualizacion!! El método ahora advierte que puede lanzar excepciones de negocio
    public boolean retirar(double monto) throws SaldoInsuficienteException, LimiteRetiroExcedidoException {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        }
        
        if (validarOperacion(monto)) {
            this.saldo -= monto;
            System.out.println("Retiro exitoso de S/. " + monto + " de la cuenta " + numeroCuenta);
            return true;
        }
        
        // Si la validación general falla, lanzamos una excepción por defecto
        throw new SaldoInsuficienteException("La operación no pudo ser validada por el sistema central.");
    }

    public double consultarSaldo() {
        return this.saldo;
    }

    public boolean transferir(CuentaBancaria cuentaDestino, double monto) throws SaldoInsuficienteException, LimiteRetiroExcedidoException {
        // Al agregar "throws ...", Java sabe qué hacer si el retiro falla por falta de fondos
        if (this.retirar(monto)) {
            cuentaDestino.depositar(monto);
            System.out.println("Transferencia exitosa de la cuenta " + this.numeroCuenta + " a la cuenta " + cuentaDestino.getNumeroCuenta());
            return true;
        }
        return false;
    }

    public abstract double calcularInteres();

    @Override
    public boolean validarOperacion(double monto) {
        return monto > 0 && this.estado;
    }

    @Override
    public double calcularComision() {
        return COMISION_MINIMA;
    }

    @Override
    public String generarEstadoCuenta() {
        return "Cuenta: " + numeroCuenta + " | Saldo Actual: S/. " + saldo + " | Estado: " + (estado ? "Activa" : "Inactiva");
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { 
        if(saldo >= 0) this.saldo = saldo; 
    }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public String getTitular() {
        return this.titular;
    }
}