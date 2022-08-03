
package com.unal.ciclo2grupo28clase22;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class GuardarEnBD {
    
    Connection conn;
    
    GuardarEnBD(){}
    
    public void conectar() {
        String dbURL = "jdbc:mysql://127.0.0.1:3306/ejemploclase17";
        String username = "root";
        String password = "Admin123#";
        // conectar
        try {
            this.conn = DriverManager.getConnection(
                    dbURL, username, password);
            if (conn != null) {
                System.out.println("Conectado");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
    }

    //-------------------------------------------
    public void desconectar() {
        try {
            if (!conn.isClosed()) {
                conn.close();
                System.out.println("Desconectado");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     //--------------------------------------------------
    public boolean guardarTripulante(String nombre, String apellido, 
            Integer grupo ) {
        
        boolean resultado = false;
        this.conectar();
        try {

            String sql = "INSERT INTO tripulantes (nombre,apellido,grupo)"
                   + " VALUES ( ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setInt(3, grupo);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Inserción exitosa !");
                resultado = true;
            }
            this.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    //--------------------------------------------------------
    public String leerTripulantes(Integer grupo) {
        this.conectar();
        String datos="";
        try {

            String sql = "SELECT * FROM tripulantes WHERE grupo = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, grupo);

            ResultSet result = statement.executeQuery();
            int count = 0;
            while (result.next()) {

                String nombre = result.getString(2);
                String apellido = result.getString(3);
                int group = result.getInt(4);

                datos = datos +"\nNombre : " + nombre
                        + "\nApellido: " + apellido
                        + "\nGrupo : " + group+"\n";

            }
            this.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return datos;
    }
    
}
