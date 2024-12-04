
package mx.itson.potroodyssey.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author yato_
 */
public class Conexion {
    
    /**
     * Establece y devuelve una conexión a la base de datos MySQL.
     *
     * Este método se utiliza para obtener una conexión a la base de datos
     * `potro_odyssey` que se encuentra en el servidor local de la máquina
     * (localhost) en el puerto 3308. La conexión se establece utilizando el
     * usuario `root` y la contraseña `Scorpion/2601`.
     *
     * @return Una conexión válida a la base de datos si la conexión se
     * establece correctamente, o `null` si ocurre algún error.
     */
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
