import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.Serializable;
import java.util.ArrayList;



public class CuentaBancaria implements Serializable {
    private Cliente cliente;
    private List<Movimiento> movimientos;
    private double saldo;

    public CuentaBancaria(Cliente cliente) {
        this.cliente = cliente;
        this.movimientos = new ArrayList<>();
        this.saldo = 0.0;
    }

    public void ingresarDinero(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        saldo += cantidad;
        movimientos.add(new Movimiento(cantidad, "Ingreso"));
    }

    public void retirar(double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        if (cantidad > saldo) throw new IllegalArgumentException("Saldo insuficiente");
        saldo -= cantidad;
        movimientos.add(new Movimiento(cantidad, "Retirada"));
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "cliente=" + cliente +
                ", saldo=" + saldo +
                ", movimientos=" + movimientos +
                '}';
    }
}

