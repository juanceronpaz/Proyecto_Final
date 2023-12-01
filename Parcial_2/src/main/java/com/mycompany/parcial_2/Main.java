package com.mycompany.parcial_2;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.Calendar;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        
        System.out.println("Hello World!");
        
        Gson gson = new Gson();
        
        int valorHoraAuto = 2000;
        
        ObjectVehiculos gestionVehiculos = new ObjectVehiculos();

        LinkedList<Automovil> automoviles   = gestionVehiculos.cargarDesdeArchivo("automoviles.txt");
        LinkedList<Motocicleta> motos       = gestionVehiculos.cargarDesdeArchivo("motocicletas.txt");
 
        get("/motocicletas", (req, res) -> {
            res.type("application/json");
            return gson.toJson(motos);
        });

        get("/automoviles", (req, res) -> {
            res.type("application/json");   
            return gson.toJson(automoviles);
        });

        get("/agregarAutomovil/:marca/:modelo/:placa/:numeroPuertas", (req, res) -> {
            
            res.type("application/json");

            String marca = req.params(":marca");
            String modelo = req.params(":modelo");
            String placa = req.params(":placa");
            
            for (Vehiculo vehiculo : automoviles) {
                if(vehiculo.getPlaca().equalsIgnoreCase(placa)){
                    return gson.toJson("Esta placa ya existe.");
                }
            }

            int numeroPuertas = Integer.parseInt(req.params(":numeroPuertas"));
            
            Calendar rightNow = Calendar.getInstance();
            int hour = rightNow.get(Calendar.HOUR_OF_DAY);
            
            Automovil nuevoAuto = new Automovil(numeroPuertas, marca, modelo,placa, hour, 0);
            automoviles.add(nuevoAuto);
            gestionVehiculos.guardarEnArchivo(automoviles, "automoviles.txt");
            return gson.toJson(nuevoAuto);
        });
        
        get("/agregarMoto/:marca/:modelo/:placa/:cilindrada", (req, res) -> {
            
            res.type("application/json");

            String marca = req.params(":marca");
            String modelo = req.params(":modelo");
            String placa = req.params(":placa");
            
            for (Vehiculo vehiculo : motos) {
                if(vehiculo.getPlaca().equalsIgnoreCase(placa)){
                    return gson.toJson("Esta placa ya existe.");
                }
            }

            int cilindrada = Integer.parseInt(req.params(":cilindrada"));
            
            Calendar rightNow = Calendar.getInstance();
            int hour = rightNow.get(Calendar.HOUR_OF_DAY);
            
            Motocicleta nuevaMoto = new Motocicleta(cilindrada, marca, modelo, placa, hour, 0);
            motos.add(nuevaMoto);
            gestionVehiculos.guardarEnArchivo(motos, "motocicletas.txt");
            return gson.toJson(nuevaMoto);
        });
        

        get("/sacarAutomovil/:placa/:horaSalida", (req, res) -> {
            String placa = (req.params(":placa"));
            int horaSalida = Integer.parseInt(req.params(":horaSalida"));
            res.type("application/json");
            String mensaje = "0";
            for (Vehiculo vehiculo : automoviles) {
                if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                    if(horaSalida < 0|| horaSalida >24)
                        mensaje = "La hora de salida es incorrecta";
                    else{
                        vehiculo.setHoraSalida(horaSalida);
                        mensaje = "Vehiculo sacado con exito";
                    }
                    break;
                } else {
                    mensaje = "No se encontro vehiculo";
                }
            }
            gestionVehiculos.guardarEnArchivo(automoviles, "automoviles.txt");
            return gson.toJson(mensaje);
        });
        
        get("/sacarMoto/:placa/:horaSalida", (req, res) -> {
            String placa = (req.params(":placa"));
            int horaSalida = Integer.parseInt(req.params(":horaSalida"));
            res.type("application/json");
            String mensaje = "0";
            for (Vehiculo vehiculo : motos) { 
                if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                    if(horaSalida < 0|| horaSalida >24)
                        mensaje = "La hora de salida es incorrecta";
                    else{
                        vehiculo.setHoraSalida(horaSalida);
                        mensaje = "Vehiculo sacado con exito";
                    }
                    break;
                } else {
                    mensaje = "No se encontro vehiculo";
                }
            }
            gestionVehiculos.guardarEnArchivo(motos, "motocicletas.txt");
            return gson.toJson(mensaje);
        });
        
        get("/automovilesActuales", (req, res) -> {
            res.type("application/json");
            LinkedList<Automovil> temporalAutomoviles = new LinkedList<>();
            for (Automovil item: automoviles) {
                if(item.getHoraSalida() == 0)
                    temporalAutomoviles.add(item);
            }
            return gson.toJson(temporalAutomoviles);
        });
      
        get("/motosActuales", (req, res) -> {
            res.type("application/json");
            LinkedList<Motocicleta> temporalMotos = new LinkedList<>();
            for (Motocicleta item: motos) {
                if(item.getHoraSalida() == 0)
                    temporalMotos.add(item);
            }
            return gson.toJson(temporalMotos);
        });
        
        get("/automovilesReporte", (req, res) -> {
            res.type("application/json");
            String reporte = "";
            
            for (Vehiculo vehiculo : automoviles) {
                if(vehiculo.getHoraSalida() != 0){
                    reporte +=
                            " Placa: "+ vehiculo.getPlaca() +
                            " Ingreso: " + vehiculo.getHoraIngreso() +
                            " Salida: " + vehiculo.getHoraSalida() +
                            " Ganancia: " +
                            (vehiculo.getHoraSalida() > vehiculo.getHoraIngreso() ? 
                            (vehiculo.getHoraSalida() - vehiculo.getHoraIngreso()) * valorHoraAuto : 
                            ((24 - vehiculo.getHoraIngreso())+vehiculo.getHoraSalida())*valorHoraAuto);
                } else {
                    reporte += 
                            " Placa: "+ vehiculo.getPlaca() +
                            " Ingreso: " + vehiculo.getHoraIngreso() +
                            " Salida: " + vehiculo.getHoraSalida() +
                            " Ganancia: Aun aquí";
                }
            }
            return gson.toJson(reporte);
        });
        
        get("/motosReporte", (req, res) -> {
            res.type("application/json");
            String reporte = "";
            
            for (Vehiculo vehiculo : motos) {
                if(vehiculo.getHoraSalida() != 0){
                    reporte +=
                            " Placa: "+ vehiculo.getPlaca() +
                            " Ingreso: " + vehiculo.getHoraIngreso() +
                            " Salida: " + vehiculo.getHoraSalida() +
                            " Ganancia: " + 
                            (vehiculo.getHoraSalida() > vehiculo.getHoraIngreso() ? 
                            (vehiculo.getHoraSalida() - vehiculo.getHoraIngreso()) * valorHoraAuto : 
                            ((24 - vehiculo.getHoraIngreso())+vehiculo.getHoraSalida())*valorHoraAuto);
                } else {
                    reporte +=
                            " Placa: "+ vehiculo.getPlaca() +
                            " Ingreso: " + vehiculo.getHoraIngreso() +
                            " Salida: " + vehiculo.getHoraSalida() +
                            " Ganancia: Aun aquí";
                }
            }
            return gson.toJson(reporte);
        });
    }
}
