/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.potroodyssey.entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.potroodyssey.persistencia.Conexion;

/**
 *
 * @author user
 */
public class Entrenador {
    
    private int id;
    private String nombre;
    private String apodo;

    /**
     * Obtiene el ID del entrenador.
     * @return El ID del entrenador.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el valor del ID del entrenador.
     * @param id El ID que se desea asignar al entrenador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del entrenador.
     * @return El nombre del entrenador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor del nombre del entrenador.
     * @param nombre El nombre que se desea asignar al entrenador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apodo del entrenador.
     * @return El apodo del entrenador.
     */
    public String getApodo() {
        return apodo;
    }

    /**
     * Establece el valor del apodo del entrenador.
     * @param apodo El apodo que se desea asignar al entrenador.
     */
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }
    
    
    /**
     * Obtiene una lista de todos los entrenadores registrados en la base de
     * datos.
     * @return Una lista de objetos `Entrenador` que contiene todos los
     * entrenadores obtenidos de la base de datos. Si ocurre un error durante el
     * proceso, se devuelve una lista vacía.
     */
    public static List<Entrenador> getAll(){
        List<Entrenador> entrenadores = new ArrayList<>();
        try {
            Connection conexion = Conexion.obtener();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, nombre, apodo FROM entrenador");
            while(rs.next()){
                Entrenador e = new Entrenador();
                e.setId((rs.getInt(1)));
                e.setNombre(rs.getString(2));
                e.setApodo(rs.getString(3));
                entrenadores.add(e);
            }
        } catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return entrenadores;
    }
    
    /**
     * Obtiene un entrenador de la base de datos según el ID proporcionado.
     * @param id El ID del entrenador que se desea obtener.
     * @return Un objeto `Entrenador` con los datos del entrenador
     * correspondiente al ID proporcionado. Si no se encuentra un entrenador con
     * ese ID o ocurre un error, se devuelve un objeto `Entrenador` vacío.
     */
    public static Entrenador getById(int id){
        Entrenador e = new Entrenador();
        try {
            Connection conexion = Conexion.obtener();
            String query = "SELECT id, nombre, apodo FROM entrenador WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                e.setId(rs.getInt(1));
                e.setNombre(rs.getString(2));
                e.setApodo(rs.getString(3));
            }
        } catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return e;
    }
    
    /**
     * Guarda un nuevo entrenador en la base de datos.
     * @param nombre El nombre del entrenador a guardar.
     * @param apodo El apodo del entrenador a guardar.
     * @return `true` si el entrenador se guardó exitosamente; de lo contrario,
     * `false`.
     */
    public static boolean save(String nombre, String apodo){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO entrenador (nombre, apodo) VALUES (?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, apodo);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Actualiza los datos de un entrenador en la base de datos.
     * @param id El ID del entrenador que se desea actualizar.
     * @param nombre El nuevo nombre del entrenador.
     * @param apodo El nuevo apodo del entrenador.
     * @return `true` si los datos del entrenador se actualizaron exitosamente;
     * de lo contrario, `false`.
     */
    public static boolean edit(int id, String nombre, String apodo){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "UPDATE entrenador SET nombre = ?, apodo = ? WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, apodo);
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
     * Elimina un entrenador de la base de datos según el ID proporcionado.
     * @param id El ID del entrenador que se desea eliminar.
     * @return `true` si el entrenador se eliminó exitosamente; de lo contrario,
     * `false`.
     */
    public static boolean delete(int id) {
        boolean resultado = false;
        try {
            Connection conexion = Conexion.obtener();
            String consulta = "DELETE FROM entrenador WHERE id = ?;";
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
