package com.mycompany.parcial_2;

import lombok.*;

public class Motocicleta extends Vehiculo{
    
    private @Getter @Setter int cilindrada;

    public Motocicleta(int cilindrada, String marca, String modelo, String placa, int horaIngreso, int par) {
        super(marca, modelo, placa, horaIngreso);
        this.cilindrada = cilindrada;
    }
}
