/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.potroodyssey.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mx.itson.potroodyssey.persistencia.Conexion;

/**
 *
 * @author user
 */
public class Habilidad {
    
    private int id;
    private String nombre;
    private String descripcion;
    
    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    //Creamos una lista para poder colocar todas las habilidades y las conectamos junto a la base de datos para invocarlas dentro de esta//
    public static List<Habilidad> getListById(int idPotromon){
        List<Habilidad> habilidades = new ArrayList<>();
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "SELECT * FROM habilidad WHERE id_potromon = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, idPotromon);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Habilidad h = new Habilidad();
                h.setId(rs.getInt(1));
                h.setNombre(rs.getString(2));
                h.setDescripcion(rs.getString(3));
                habilidades.add(h);
            }
        }catch(Exception ex){
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return habilidades;
    }
    
    //Tomamos las habilidades desde el ID (identificador) y obtenemos sus parametros en el orden impuesto//
    public static Habilidad getById(int id){
        Habilidad h = new Habilidad();
        try {
            Connection conexion = Conexion.obtener();
            String query = "SELECT id, nombre, descripcion FROM habilidad WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                h.setId(rs.getInt(1));
                h.setNombre(rs.getString(2));
                h.setDescripcion(rs.getString(3));
            }
        } catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return h;
    }
    
    //Guardamos dentro de el array nuestros parametros puestos en esta funcion//
    public static boolean save(String nombre, String descripcion, int idPotromon){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO habilidad (nombre, descripcion, id_potromon) VALUES (?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, idPotromon);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
    //Editamos en el array el parametro anteriormente puesto con esta funcion//
    public static boolean edit(int id, String nombre, String descripcion){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "UPDATE habilidad SET nombre = ?, descripcion = ? WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, id);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
    //Eliminamos los parametros mencionados dentro del array list//
    public static boolean delete(int id) {
        boolean resultado = false;
        try {
            Connection conexion = Conexion.obtener();
            String consulta = "DELETE FROM habilidad WHERE id = ?;";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);

            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        } catch (Exception ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return resultado;
    }
}

