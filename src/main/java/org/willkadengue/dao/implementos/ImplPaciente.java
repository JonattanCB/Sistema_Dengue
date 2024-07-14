package org.willkadengue.dao.implementos;

import org.willkadengue.dao.Conexion;
import org.willkadengue.dao.daoCiudad;
import org.willkadengue.dao.daoPaciente;
import org.willkadengue.Dto.CiudadInfectada;
import org.willkadengue.model.Paciente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplPaciente implements daoPaciente {

    private final Conexion con;
    private final daoCiudad daoCiudad;

    public ImplPaciente() {
        con = new Conexion();
        daoCiudad = new ImplCiudad();
    }

    @Override
    public void agregar(Paciente paciente) {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("INSERT INTO public.paciente(\n" +
                    "\t nombres, apellidos, tdocumento, ndocumento, direccion, telefono, estado, \"idCiudad\")\n" +
                    "\tVALUES ( ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, paciente.getNombre());
            ps.setString(2,paciente.getApellido());
            ps.setString(3, paciente.getTdocumento());
            ps.setInt(4,paciente.getNdocumento());
            ps.setString(5, paciente.getDireccion());
            ps.setInt(6, paciente.getTelefono());
            ps.setString(7, paciente.getEstado());
            ps.setInt(8, paciente.getCiudad().getId());
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void actualizar(Paciente paciente) {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.paciente\n" +
                    "\tSET nombres=?, apellidos=?, tdocumento=?, ndocumento=?, direccion=?, telefono=?, \"idCiudad\"=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1, paciente.getNombre());
            ps.setString(2,paciente.getApellido());
            ps.setString(3, paciente.getTdocumento());
            ps.setInt(4, paciente.getNdocumento());
            ps.setString(5, paciente.getDireccion());
            ps.setInt(6, paciente.getTelefono());
            ps.setInt(7, paciente.getCiudad().getId());
            ps.setInt(8, paciente.getId());
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public List<Paciente> listTodo() {
        List<Paciente> lst = new ArrayList<>();
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombres, apellidos, tdocumento, ndocumento, direccion, telefono, estado, \"idCiudad\"\n" +
                    "\tFROM public.paciente order by id asc ;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Paciente p = new Paciente();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setApellido(rs.getString(3));
                p.setTdocumento(rs.getString(4));
                p.setNdocumento(rs.getInt(5));
                p.setDireccion(rs.getString(6));
                p.setTelefono(rs.getInt(7));
                p.setEstado(rs.getString(8));
                p.setCiudad(daoCiudad.buscar(rs.getInt(9)));
                lst.add(p);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
         return  lst;
    }

    @Override
    public Paciente buscar(int id) {
       Paciente p = new Paciente();
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT id, nombres, apellidos, tdocumento, ndocumento, direccion, telefono, estado, \"idCiudad\"\n" +
                    "\tFROM public.paciente where id =?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setApellido(rs.getString(3));
                p.setTdocumento(rs.getString(4));
                p.setNdocumento(rs.getInt(5));
                p.setDireccion(rs.getString(6));
                p.setTelefono(rs.getInt(7));
                p.setEstado(rs.getString(8));
                p.setCiudad(daoCiudad.buscar(rs.getInt(9)));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  p;
    }

    @Override
    public void eliminar(int id) {
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("DELETE FROM public.paciente\n" +
                    "\tWHERE id = ?;");
            ps.setInt(1,id);
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public int cantidadPaciente() {
        int cantidad=0;
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT COUNT(*) AS cantidad\n" +
                    "\tFROM public.paciente;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cantidad = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  cantidad;
    }

    @Override
    public int cantidadInfectados() {
        int cantidad=0;
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT COUNT(*) AS cantidad\n" +
                    "\tFROM public.paciente where estado='infectado';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cantidad = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  cantidad;
    }

    @Override
    public int cantidadCurados() {
        int cantidad=0;
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("SELECT COUNT(*) AS cantidad\n" +
                    "\tFROM public.paciente where estado='curado';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cantidad = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  cantidad;
    }

    @Override
    public List<CiudadInfectada> listInfectada() {
        List<CiudadInfectada> lst = new ArrayList<>();
        try{
            PreparedStatement ps = con.crearCNX().prepareStatement("Select c.\"nombre \" , COUNT(p.id)  from public.\"Ciudad\" c\n" +
                    "\tinner join paciente p on c.id = p.\"idCiudad\"\n " +
                    "\twhere p.estado = 'infectado'\n" +
                    "GROUP BY c.\"nombre \";");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CiudadInfectada ci = new CiudadInfectada();
                ci.setNombre(rs.getString(1));
                ci.setCantidad(rs.getInt(2));
                lst.add(ci);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try{
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return  lst;
    }

    @Override
    public void eliminarPacientexCiudad(int idCiudad) {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("DELETE FROM public.paciente\n" +
                    "\tWHERE \"idCiudad\"=?;");
            ps.setInt(1, idCiudad);
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void cambiarEstado(int id, String estado) {
        try {
            PreparedStatement ps = con.crearCNX().prepareStatement("UPDATE public.paciente\n" +
                    "\tSET estado=?\n" +
                    "\tWHERE id=?;");
            ps.setString(1,estado);
            ps.setInt(2, id);
            ps.execute();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                con.cerrar();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
