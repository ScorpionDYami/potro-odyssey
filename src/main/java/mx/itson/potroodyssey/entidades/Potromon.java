/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.potroodyssey.entidades;

/**
 *
 * @author user
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.potroodyssey.persistencia.Conexion;

public class Potromon {
    
    private int id;
    private String nombre;
    private String descripcion;
    private String imagen; 
    private Entrenador entrenador;
    private List<Habilidad> habilidades;
    private int puntaje;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the entrenador
     */
    public Entrenador getEntrenador() {
        return entrenador;
    }

    /**
     * @param entrenador the entrenador to set
     */
    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    /**
     * @return the habilidades
     */
    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    /**
     * @param habilidades the habilidades to set
     */
    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    /**
     * @return the puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * @param puntaje the puntaje to set
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    public static List<Potromon> getAll(){
        List<Potromon> potromones = new ArrayList<>();
        try {
            Connection conexion = Conexion.obtener();
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, nombre, descripcion, id_entrenador, puntaje FROM potromon");
            while(rs.next()){
                Potromon p = new Potromon();
                p.setId((rs.getInt(1)));
                p.setNombre(rs.getString(2));
                p.setDescripcion(rs.getString(3));
                int idEntrenador = rs.getInt(4);
                String entrenadorQuery = "SELECT nombre FROM entrenador WHERE id = ?";
                try (PreparedStatement entrenadorStatement = conexion.prepareStatement(entrenadorQuery)) {
                    entrenadorStatement.setInt(1, idEntrenador);
                    ResultSet entrenadorRs = entrenadorStatement.executeQuery();
                    if (entrenadorRs.next()) {
                        Entrenador entrenador = new Entrenador();
                        entrenador.setNombre(entrenadorRs.getString("nombre"));
                        p.setEntrenador(entrenador);
                    }
                }
                p.setPuntaje(rs.getInt(5));
                
                List<Habilidad> habilidades = Habilidad.getListById(rs.getInt(1));
                p.setHabilidades(habilidades);
                
                
                potromones.add(p);
            }
        } catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return potromones;
    }
    
    public static Potromon getById(int id){
        Potromon p = new Potromon();
        try {
            Connection conexion = Conexion.obtener();
            String query = "SELECT id, nombre, descripcion, id_entrenador, puntaje FROM potromon WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, id);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setDescripcion(rs.getString(3));
                int idEntrenador = rs.getInt(4);
                String entrenadorQuery = "SELECT nombre FROM entrenador WHERE id = ?";
                try (PreparedStatement entrenadorStatement = conexion.prepareStatement(entrenadorQuery)) {
                    entrenadorStatement.setInt(1, idEntrenador);
                    ResultSet entrenadorRs = entrenadorStatement.executeQuery();
                    if (entrenadorRs.next()) {
                        Entrenador entrenador = new Entrenador();
                        entrenador.setNombre(entrenadorRs.getString("nombre"));
                        p.setEntrenador(entrenador);
                    }
                }
                p.setPuntaje(rs.getInt(5));
            }
        } catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return p;
    }
    
    public static boolean save(String nombre, String descripcion, int entrenador, int puntaje){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "INSERT INTO potromon (nombre, descripcion, id_entrenador, puntaje) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, entrenador);
            statement.setInt(4, puntaje);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
    
    public static boolean edit(int id, String nombre, String descripcion, int entrenador, int puntaje){
        boolean resultado = false;
        try{
            Connection conexion = Conexion.obtener();
            String consulta = "UPDATE potromon SET nombre = ?, descripcion = ?, id_entrenador = ?, puntaje = ? WHERE id = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, entrenador);
            statement.setInt(4, puntaje);
            statement.setInt(5, id);
            
            statement.execute();
            resultado = statement.getUpdateCount() == 1;
            conexion.close();
        }catch(Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return resultado;
    }
    
    public static boolean delete(int id) {
        boolean resultado = false;
        try {
            Connection conexion = Conexion.obtener();
            String consulta = "DELETE FROM potromon WHERE id = ?;";
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

