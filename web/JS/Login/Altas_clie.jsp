<%-- 
    Document   : Altas_clie
    Created on : 23/10/2019, 08:16:07 PM
    Author     : eli_b
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dar de alta</title>
</head>
<script>
    function Registro_Exitoso(){
        window.location='../../HTML/index.html';
        alert("Estas registrado");
    }
    function No_coinciden(){
        alert("Las contraseñas no coinciden");
        window.location='../../HTML/RegistroUsuario.html';
    }
    
    </script>
<body>
<%@page import="java.sql.*,java.io.*" %>
<% 
String nom_clie = request.getParameter("nom_clie");
String ape_clie = request.getParameter("ape_clie");
String dir_clie = request.getParameter("dir_clie");
String cor_clie = request.getParameter("cor_clie");
String tel_clie = request.getParameter("tel_clie");
String con_clie = request.getParameter("con_clie");
String con2_clie = request.getParameter("con2_clie");
int cuenta = 0;
Connection conec = null;
Statement stm = null;
PreparedStatement ID =null;
PreparedStatement st =null;
ResultSet trae = null;
try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
	conec = DriverManager.getConnection("jdbc:mysql://localhost/Ican","root","n0m3l0");
        
}catch(SQLException error){
	out.print(error.toString());
}

try{
    stm = conec.createStatement();
    stm.executeUpdate("insert into Mpersona values('"+2+"','"+nom_clie+" "+ape_clie+"','"+dir_clie+"','"+cor_clie+"','"+tel_clie+"','"+con_clie+"');");
    out.println("<script>Registro_Exitoso();</script>");
}catch(Exception e){
    out.println(e.getLocalizedMessage());
    e.printStackTrace();
}
conec.close();




%>
</body>
</html>
