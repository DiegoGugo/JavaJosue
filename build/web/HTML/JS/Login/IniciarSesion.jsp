<%-- 
    Document   : IniciarSesion
    Created on : 24/10/2019, 07:57:12 AM
    Author     : Alumno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@page import="java.sql.*,java.io.*"%>
        <%
        String correo = request.getParameter("corr_compara");
        String contra = request.getParameter("contra_compara");
        Connection conecta = null;
        Statement stm = null;
        ResultSet trae = null;
        
        try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conecta = DriverManager.getConnection("jdbc:mysql://localhost/Ican","root","n0m3l0");
        }catch(SQLException error){
            error.toString();
        }
        
        %>
    </body>
</html>
