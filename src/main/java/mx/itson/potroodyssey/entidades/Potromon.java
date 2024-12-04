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
     * Obtiene el ID del Potromon.
     * @return El ID del Potromon.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del Potromon.
     * @param id El ID que se desea asignar al Potromon.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del Potromon.
     * @return El nombre del Potromon.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del Potromon.
     * @param nombre El nombre que se desea asignar al Potromon.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del Potromon.
     * @return La descripción del Potromon.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del Potromon.
     * @param descripcion La descripción que se desea asignar al Potromon.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la imagen asociada al Potromon.
     * @return La ruta de la ubicacion del archivo de la imagen del Potromon.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen asociada al Potromon.
     * @param imagen La ruta de ubicacion del archivo de la imagen del Potromon.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el entrenador asociado al Potromon.
     * @return El entrenador del Potromon.
     */
    public Entrenador getEntrenador() {
        return entrenador;
    }

    /**
     * Establece el entrenador asociado al Potromon.
     * @param entrenador El entrenador que se desea asignar al Potromon.
     */
    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    /**
     * Obtiene la lista de habilidades asociadas al Potromon.
     * @return La lista de habilidades del Potromon.
     */
    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    /**
     * Establece la lista de habilidades asociadas al Potromon.
     * @param habilidades La lista de habilidades que se desea asignar al
     * Potromon.
     */
    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    /**
     * Obtiene el puntaje del Potromon.
     * @return El puntaje del Potromon.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Establece el puntaje del Potromon.
     * @param puntaje El puntaje que se desea asignar al Potromon.
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    /**
     * Obtiene todos los Potromones registrados en la base de datos.
     * @return Una lista con todos los Potromones.
     */
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
    
    /**
     * Obtiene un Potromon por su ID desde la base de datos.
     * @param id El ID del Potromon a obtener.
     * @return El Potromon con el ID especificado.
     */
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
    
    /**
     * Guarda un nuevo Potromon en la base de datos.
     * @param nombre El nombre del Potromon.
     * @param descripcion La descripción del Potromon.
     * @param entrenador El ID del entrenador asociado al Potromon.
     * @param puntaje El puntaje del Potromon.
     * @return `true` si el Potromon se guardó exitosamente; de lo contrario,
     * `false`.
     */
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
    
    /**
     * Edita un Potromon existente en la base de datos.
     * @param id El ID del Potromon a editar.
     * @param nombre El nuevo nombre del Potromon.
     * @param descripcion La nueva descripción del Potromon.
     * @param entrenador El nuevo ID del entrenador asociado al Potromon.
     * @param puntaje El nuevo puntaje del Potromon.
     * @return `true` si el Potromon fue editado exitosamente; de lo contrario,
     * `false`.
     */
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
    
    /**
     * Elimina un Potromon por su ID desde la base de datos.
     * @param id El ID del Potromon a eliminar.
     * @return `true` si el Potromon fue eliminado exitosamente; de lo
     * contrario, `false`.
     */
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

