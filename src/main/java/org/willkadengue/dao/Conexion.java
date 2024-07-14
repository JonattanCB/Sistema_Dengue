package org.willkadengue.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    Connection con;
    public Connection crearCNX(){

        String url = "jdbc:postgresql://localhost:5432/Hospital";
        String pasword = "2002";
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url,"postgres",pasword);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    public void cerrar(){
        try {
            if(con !=null){
                con.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
