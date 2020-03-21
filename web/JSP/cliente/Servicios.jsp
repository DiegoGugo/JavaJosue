<%-- 
    Document   : Servicios
    Created on : Nov 11, 2019, 12:14:46 PM
    Author     : jela3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../../HTML/Css/Estilos.css">
    </head>
    <body>
        <header class="header">
            <div class="contenedor">
                <h1 class="logo">Can-Contento</h1>
                <span class="icon-menu" id="btn-menu"></span>
                <nav class="nav" id="nav">
                    <ul class="menu">
                        <li class="menu__item"><a class="menu__link" href="home.jsp">Home</a></li>
                        <li class="menu__item"><a class="menu__link select" href="../../JSP/cliente/Servicios.jsp">Servicios</a></li>
                        <li class="menu__item"><a class="menu__link" href="Perfil.jsp">Mi perfil</a></li>
                        <li class="menu__item"><a class="menu__link" href="Notificaciones.jsp">Notificaciones</a></li>
                    </ul>
                </nav>
            </div>
            <div class="wave" style="height: 150px; overflow: hidden;"><svg viewBox="0 0 500 150" preserveAspectRatio="none"
                                                                            style="height: 100%; width: 100%;">
                <path d="M0.00,49.98 C150.00,150.00 349.20,-50.00 500.00,49.98 L500.00,150.00 L0.00,150.00 Z"
                      style="stroke: none; fill: #fff;"></path>
                </svg></div>

        </header>

        <%@page import="java.sql.*,java.io.*"%>
        <% /*
            Connection c = null;
            Statement stm = null;
            Statement espacia = null;
            ResultSet trae, espaciados = null;
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                c = DriverManager.getConnection("jdbc:mysql://localhost/canContento", "root", "n0m3l0");
                stm = c.createStatement();
                espacia = c.createStatement();
            } catch (SQLException e) {
                out.print(e.toString());
            }
            if (request.getParameter("OK") != null) {
                String serv1 = request.getParameter("producto1");
                String serv2 = request.getParameter("producto2");
                String serv3 = request.getParameter("producto3");
                String serv4 = request.getParameter("producto4");
                String serv5 = request.getParameter("producto5");
                String serv6 = request.getParameter("producto6");
                String serv7 = request.getParameter("producto7");
                String servCombo = request.getParameter("Servicio");

                trae = stm.executeQuery("Select nom_art from articulo where exi_art > 0;");
                int contador = 0;
                while (trae.next()) {
                    contador++;
                }
                if (!serv1.equals("")) {
                    out.print("<br><br>");
                    out.println(serv1);
                }
                if (!serv2.equals("")) {
                    out.print("<br><br>");
                    out.println(serv2);
                }
                if (!serv3.equals("")) {
                    out.print("<br><br>");
                    out.println(serv3);
                }
                if (!serv4.equals("")) {
                    out.print("<br><br>");
                    out.println(serv4);
                }
                if (!serv5.equals("")) {
                    out.print("<br><br>");
                    out.println(serv5);
                }
                if (!serv6.equals("")) {
                    out.print("<br><br>");
                    out.println(serv6);
                }
                if (!serv7.equals("")) {
                    out.print("<br><br>");
                    out.println(serv7);
                }
                if (!servCombo.equals("")) {
                    out.print("<br><br>");
                    out.println(servCombo);
                }

            } else {

                int espacios = 0;
                try {
                    trae = stm.executeQuery("Select nom_art from articulo where exi_art > 0;");
                    out.print("<h1>Selecciona los articulos que deseas comprar</h1>");
                    out.print("<br><br><br>");
                    out.print("<form action='Servicios.jsp' method='post'>");
                    out.print("<table border='2'>");
                    out.print("<tr>");
                    out.print("<td>Productos</td>");
                    while (trae.next()) {
                        out.print("<td>'" + trae.getString("nom_art") + "'</td>");
                        espacios++;
                    }
                    out.print("<td>");
                    out.print("<select name ='Servicio'>");
                    out.print("<option value=null>Selecciona un servicio</option>");
                    out.print("<option value='serv1'>Corte de pelo</option>");
                    out.print("<option value='serv2'>Baño</option>");
                    out.print("<option value='serv3'>Paseo</option>");
                    out.print("</select>");
                    out.print("</td>");
                    out.print("</tr><tr><td>Cantidades</td>");
                    for (int i = 1; i <= espacios + 1; i++) {
                        out.print("<td><Select name='producto'" + i + "''>"
                                + "<option value='0'>0</option>"
                                + "<option value='1'>1</option>"
                                + "<option value='2'>2</option>"
                                + "<option value='3'>3</option>"
                                + "<option value='4'>4</option>"
                                + "<option value='5'>5</option>"
                                + "<option value='6'>6</option>"
                                + "<option value='7'>7</option>"
                                + "<option value='8'>8</option>"
                                + "<option value='9'>9</option>"
                                + "</td>");
                    }
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<input type=submit name=OK value=Agregar>");
                } catch (Exception e) {
                    out.print(e.toString());
                }
                out.print("<form>");
            }
*/
            
        %>
    </body>
</html>
