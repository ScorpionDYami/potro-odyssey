package mx.itson.potroodyssey.ui;

import mx.itson.potroodyssey.entidades.Entrenador;
import mx.itson.potroodyssey.entidades.Habilidad;
import mx.itson.potroodyssey.entidades.Potromon;
import mx.itson.potroodyssey.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PotromonCardView extends JFrame {

    private Potromon potromon;
    private JLabel lblPuntaje;
    private JTextField txtPuntaje;
    private JComboBox<Potromon> cmbPotromons;

    public PotromonCardView() {
        setTitle("Potromon-Dex");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con el nombre e imagen
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel lblNombre = new JLabel("Selecciona un Potromon", JLabel.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        panelSuperior.add(lblNombre, BorderLayout.NORTH);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con la lista de potromons
        JPanel panelCentral = new JPanel(new GridLayout(0, 1));
        cmbPotromons = new JComboBox<>();
        loadPotromonsList();
        cmbPotromons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbPotromons.getSelectedItem() != null) {
                    potromon = (Potromon) cmbPotromons.getSelectedItem();
                    displayPotromonInfo();
                }
            }
        });
        panelCentral.add(new JLabel("Potromons disponibles:"));
        panelCentral.add(cmbPotromons);

        add(panelCentral, BorderLayout.CENTER);

        // Panel para cambiar puntaje
        JPanel panelInferior = new JPanel(new FlowLayout());
        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setFont(new Font("Arial", Font.BOLD, 16));
        panelInferior.add(lblPuntaje);

        txtPuntaje = new JTextField(5);
        panelInferior.add(txtPuntaje);

        JButton btnSumar = new JButton("Sumar Puntaje");
        btnSumar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (potromon != null) {
                    try {
                        int incremento = Integer.parseInt(txtPuntaje.getText());
                        cambiarPuntaje(incremento);
                        updatePotromonInDatabase(potromon); // Actualizar en la base de datos
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
                    }
                }
            }
        });
        panelInferior.add(btnSumar);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadPotromonsList() {
        // Conectar a la base de datos y cargar los potromons
        List<Potromon> potromons = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM potromons"; // Asegúrate de tener esta tabla
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                Entrenador entrenador = new Entrenador(rs.getInt("entrenador_id"), rs.getString("entrenador_nombre"), rs.getString("entrenador_apodo"));
                List<Habilidad> habilidades = new ArrayList<>(); // Aquí deberías cargar las habilidades asociadas
                int puntaje = rs.getInt("puntaje");

                Potromon potromon = new Potromon(id, nombre, descripcion, imagen, entrenador, habilidades, puntaje);
                potromons.add(potromon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Agregar los potromons al JComboBox
        for (Potromon p : potromons) {
            cmbPotromons.addItem(p);
        }
    }

    private void displayPotromonInfo() {
        if (potromon != null) {
            lblPuntaje.setText("Puntaje: " + potromon.getPuntaje());
        }
    }

    private void cambiarPuntaje(int cantidad) {
        int nuevoPuntaje = potromon.getPuntaje() + cantidad;
        potromon.setPuntaje(nuevoPuntaje);
        lblPuntaje.setText("Puntaje: " + nuevoPuntaje);
    }

    private void updatePotromonInDatabase(Potromon potromon) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE potromons SET puntaje = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, potromon.getPuntaje());
            stmt.setInt(2, potromon.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PotromonCardView();
    }
}
