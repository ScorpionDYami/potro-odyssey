
package mx.itson.potroodyssey.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author yato_
 */
public class Conexion {
    
    public static Connection obtener(){
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3308/potro_odyssey?user=root&password=Scorpion/2601");
        } catch (Exception ex){
            System.err.println("Ocurrió un error: " + ex.getMessage());
        }
        return conexion;
    }
    
}
