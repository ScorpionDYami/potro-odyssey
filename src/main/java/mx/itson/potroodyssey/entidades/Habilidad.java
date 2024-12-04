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
     * Obtiene la descripción de la habilidad.
     * @return La descripción de la habilidad.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la habilidad.
     * @param descripcion La descripción que se desea asignar a la habilidad.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el ID de la habilidad.
     * @return El ID de la habilidad.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Establece el ID de la habilidad.
     * @param id El ID que se desea asignar a la habilidad.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Obtiene el nombre de la habilidad.
     * @return El nombre de la habilidad.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre de la habilidad.
     * @param nombre El nombre que se desea asignar a la habilidad.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene una lista de habilidades asociadas a un Potromon, identificado
     * por su ID.
     * @param idPotromon El ID del Potromon para obtener sus habilidades.
     * @return Una lista de habilidades asociadas al Potromon con el ID
     * especificado.
     */
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
    
    /**
     * Obtiene una habilidad por su ID.
     * @param id El ID de la habilidad a obtener.
     * @return La habilidad con el ID especificado.
     */
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
    
    /**
     * Guarda una nueva habilidad en la base de datos.
     * @param nombre El nombre de la habilidad.
     * @param descripcion La descripción de la habilidad.
     * @param idPotromon El ID del Potromon al que se le asociará la habilidad.
     * @return `true` si la habilidad se guardó exitosamente; de lo contrario,
     * `false`.
     */
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
    
    /**
     * Edita una habilidad existente en la base de datos.
     * @param id El ID de la habilidad a editar.
     * @param nombre El nuevo nombre de la habilidad.
     * @param descripcion La nueva descripción de la habilidad.
     * @return `true` si la habilidad se editó exitosamente; de lo contrario,
     * `false`.
     */
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
    
    /**
     * Elimina una habilidad de la base de datos por su ID.
     * @param id El ID de la habilidad a eliminar.
     * @return `true` si la habilidad se eliminó exitosamente; de lo contrario,
     * `false`.
     */
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

