package org.Dengue.com.DAO.Implementos;

import org.Dengue.com.DAO.Conexion;
import org.Dengue.com.DAO.daoUsuario;
import org.Dengue.com.Model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ImplUsuario implements daoUsuario {

    private final Conexion con;

    public ImplUsuario() {
        this.con = new Conexion();
    }

    @Override
    public boolean verificarAcceso(Usuario u) {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT correo, contrasenia\n" +
                    "\tFROM public.personal where correo = ? and contrasenia=?;");
            ps.setString(1, u.getCorreo());
            ps.setString(2, u.getContra());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                return true;
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            try{
                con.Cerrar();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public int cantidadPersonal() {
        int cantidad =0;
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT COUNT(*) AS cantidad\n" +
                    "\tFROM public.personal;\n");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cantidad = rs.getInt(1);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            try{
                con.Cerrar();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return cantidad;
    }

    @Override
    public Usuario BuscarUsuario(Usuario u) {
        Usuario usuario = new Usuario();
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, apellido, tdocumento, ndocumento, direccion, telefono, correo, contrasenia, cargo\n" +
                    "\tFROM public.personal where correo=? and contrasenia=?;");
            ps.setString(1, u.getCorreo());
            ps.setString(2, u.getContra());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            try{
                con.Cerrar();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return usuario;
    }

    @Override
    public void agregar(Usuario usuario) {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.personal(\n" +
                    "\tnombre, apellido, tdocumento, direccion, cargo, telefono, ndocumento, correo, contrasenia)\n" +
                    "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTdocumento());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getCargo());
            ps.setInt(6, usuario.getTelefono());
            ps.setInt(7,usuario.getNdocumento());
            ps.setString(8,usuario.getCorreo());
            ps.setString(9, usuario.getContra());
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
    public void actualizar(Usuario usuario) {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.personal\n" +
                    "\tSET  nombre=?, apellido=?, tdocumento=?, direccion=?, cargo=?, telefono=?, ndocumento=?, correo=?, contrasenia=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTdocumento());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getCargo());
            ps.setInt(6, usuario.getTelefono());
            ps.setInt(7,usuario.getNdocumento());
            ps.setString(8,usuario.getCorreo());
            ps.setString(9,usuario.getContra());
            ps.setInt(10, usuario.getId());
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
    public List<Usuario> listTodo() {
        List<Usuario> lst = new ArrayList<Usuario>();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, apellido, tdocumento, direccion, cargo, telefono, ndocumento, correo\n" +
                    "\tFROM public.personal order by id asc ;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Usuario u = new Usuario();
                u.setId(rs.getInt(1));
                u.setNombre(rs.getString(2));
                u.setApellido(rs.getString(3));
                u.setTdocumento(rs.getString(4));
                u.setDireccion(rs.getString(5));
                u.setCargo(rs.getString(6));
                u.setTelefono(rs.getInt(7));
                u.setNdocumento(rs.getInt(8));
                u.setCorreo(rs.getString(9));
                lst.add(u);
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
    public Usuario buscar(int id) {
        Usuario u = new Usuario();
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombre, apellido, tdocumento, direccion, cargo, telefono, ndocumento, correo, contrasenia\n" +
                    "\tFROM public.personal where id =?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                u.setId(rs.getInt(1));
                u.setNombre(rs.getString(2));
                u.setApellido(rs.getString(3));
                u.setTdocumento(rs.getString(4));
                u.setDireccion(rs.getString(5));
                u.setCargo(rs.getString(6));
                u.setTelefono(rs.getInt(7));
                u.setNdocumento(rs.getInt(8));
                u.setCorreo(rs.getString(9));
                u.setContra(rs.getString(10));
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
        return u;
    }

    @Override
    public void eliminar(int id) {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("DELETE FROM public.personal\n" +
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
