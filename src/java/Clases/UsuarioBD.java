/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author jela3
 */
public class UsuarioBD {

    public static boolean registrarUsuario(Usuario usu) {
        boolean resp = false;

        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();

        try {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO usuario VALUES (?,?,?)");
            ps.setString(1, usu.getCor());
            ps.setString(2, usu.getCon());
            ps.setInt(3, usu.getTip());
            int i = ps.executeUpdate();

            //En caso de que se haya podido agregar el usuario correctamente
            if (i == 1) {

                PreparedStatement ps1 = cn.prepareStatement("INSERT INTO persona (nom_pers,tel_pers,dir_pers,cor_usu) VALUES (?,?,?,?)");
                ps1.setString(1, usu.getNom());
                ps1.setInt(2, usu.getTel());
                ps1.setString(3, usu.getDir());
                ps1.setString(4, usu.getCor());
                int j = ps1.executeUpdate();
                if (j == 1) {
                    resp = true;
                }
            } else {
                resp = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return resp;
    }

    public static Usuario VerificarUsuario(String correo) {
        Usuario usu = new Usuario();

        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();

        try {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM usuario WHERE (cor_usu = ?)");
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                usu.setCor(rs.getString("cor_usu"));
                usu.setCon(rs.getString("con_usu"));
                usu.setTip(Integer.parseInt(rs.getString("id_tip")));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return usu;
    }

    public static boolean registrarMascota(Perro per) {
        boolean resp = false;

        Connection cn;
        Conexion con = new Conexion();
        cn = con.conectar();
        try {
            boolean genero = per.getGenero();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO perro(nom_per,fot_per,nac_per,gen_per,tall_per,cod_per,cor_usu) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, per.getNombre());
            ps.setBlob(2, per.getArchivoimg());
            ps.setString(3, per.getNac());
            if (genero == true) {
                //Macho
                ps.setInt(4, 1);
            } else {
                //Hembra
                ps.setInt(4, 0);
            }
            ps.setString(5, per.getTalla());
            ps.setInt(6, 0);
            ps.setString(7, per.getDueno());
            int i = ps.executeUpdate();
            if (i == 1) {
                resp = true;
            }
        } catch (Exception e) {

        }
        return resp;
    }
}
