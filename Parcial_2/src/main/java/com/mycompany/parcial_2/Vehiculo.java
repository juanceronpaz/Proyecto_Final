package com.mycompany.parcial_2;

import java.io.Serializable;
import lombok.*;

public class Vehiculo implements Serializable{
    
    private @Getter @Setter String marca,  modelo, placa;
    private @Getter @Setter  int horaIngreso, horaSalida = 0;

    public Vehiculo(String marca, String modelo, String placa, int horaIngreso) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.horaIngreso = horaIngreso;
    }
}
