package com.mycompany.parcial_2;

import lombok.*;

public class Automovil extends Vehiculo{
    
    private @Getter @Setter int numeroPuertas;

    public Automovil(int numeroPuertas, String marca, String modelo, String placa, int horaIngreso, int par) {
        super(marca, modelo, placa, horaIngreso);
        this.numeroPuertas = numeroPuertas;
    }
}
