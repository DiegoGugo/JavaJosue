package Conexion;


import java.sql.*;


public class Conexion {
    String DRIVER = "com.mysql.jdbc.Driver";
    String URL = "jdbc:mysql://localhost:3306/cancontento";
    String USER = "root";
    String PASSWORD = "n0m3l0";
    Connection con;
    public Connection conectar(){
        try{
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar: "+e.getMessage());
        }
        return con;
    }
}
