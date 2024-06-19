package org.Dengue.com.DAO;

import javax.naming.InitialContext;
import javax.sql.DataSource;
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
            throw new RuntimeException(e);
        }
        return con;
    }

    public void Cerrar(){
        try {
            if(con !=null){
                con.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
