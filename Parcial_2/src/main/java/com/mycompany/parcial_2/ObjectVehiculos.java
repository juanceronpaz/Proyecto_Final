package com.mycompany.parcial_2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class ObjectVehiculos {
    
    public <T extends Vehiculo>  void guardarEnArchivo(LinkedList<T> vehiculoArrayList, String file){
        try(FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);){
            
            objectOutputStream.writeObject(vehiculoArrayList);
            System.out.println("Se guardaron correctamente los datos");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public <T extends Vehiculo> LinkedList<T> cargarDesdeArchivo(String nombreArchivo){
        LinkedList<T> vehiculoArrayList = new LinkedList<>();
        try (FileInputStream fileInputStream = new FileInputStream(nombreArchivo); 
                ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
            
                vehiculoArrayList = (LinkedList<T>) in.readObject();                           
                System.out.println("Se cargaron correctamente los datos");
            }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return vehiculoArrayList;
    }
}