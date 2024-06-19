package org.Dengue.com.DAO.Implementos;

import org.Dengue.com.DAO.Conexion;
import org.Dengue.com.DAO.daoCiudad;
import org.Dengue.com.Model.Ciudad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplCiudad implements daoCiudad {

    private final Conexion con;

    public ImplCiudad() {
        con = new Conexion();
    }

    @Override
    public void agregar(Ciudad ciudad) {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.\"Ciudad\"(\n" +
                    "\t\"nombre \", \"Descripcion\")\n" +
                    "\tVALUES ( ?, ?);");
            ps.setString(1, ciudad.getNombre());
            ps.setString(2, ciudad.getDescripcion());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
           try{
               con.Cerrar();
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

    @Override
    public void actualizar(Ciudad ciudad) {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.\"Ciudad\"\n" +
                    "\tSET \"nombre \"=?, \"Descripcion\"=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, ciudad.getNombre());
            ps.setString(2, ciudad.getDescripcion());
            ps.setInt(3, ciudad.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                con.Cerrar();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Ciudad> listTodo() {
        List<Ciudad> lst = new ArrayList<>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, \"nombre \", \"Descripcion\"\n" +
                    "\tFROM public.\"Ciudad\" order by id asc ;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Ciudad c= new Ciudad();
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setDescripcion(rs.getString(3));
                lst.add(c);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                con.Cerrar();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return lst;
    }

    @Override
    public Ciudad buscar(int id) {
        Ciudad c = new Ciudad();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, \"nombre \", \"Descripcion\"\n" +
                    "\tFROM public.\"Ciudad\" where  id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                c.setId(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setDescripcion(rs.getString(3));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                con.Cerrar();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return c;
    }

    @Override
    public void eliminar(int id) {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("DELETE FROM public.\"Ciudad\"\n" +
                    "\tWHERE id=?;");
            ps.setInt(1, id);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                con.Cerrar();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
