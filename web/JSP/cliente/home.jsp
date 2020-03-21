<%-- 
    Document   : home
    Created on : Mar 6, 2020, 12:05:39 PM
    Author     : jela3
--%>

<%@page import="Clases.Cita"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Clases.Perro"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../../HTML/Css/Estilos.css">
        <style>
            .overlay {
                position: fixed;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                background: rgba(0, 0, 0, 0.7);
                transition: opacity 500ms;
                visibility: hidden;
                opacity: 0;
            }
            .overlay:target {
                visibility: visible;
                opacity: 1;
            }
            #popupBody{
                width: 46%;
                padding: 2%;
                border-radius: 15px;
                box-shadow: 0 0 5px #CCC;
                background: #FFF;
                position: relative;
                transition: all 5s ease-in-out;
                margin: 20% auto;    
            }
            #cerrar{
                position: absolute;
                top: 20px;
                right: 30px;
                transition: all 200ms;
                font-size: 30px;
                font-weight: bold;
                text-decoration: none;
                color: #F00;
            }
        </style>
    </head>
    <body>

        <script type="text/javascript">
            $(document).ready(function () {
                setTimeout(function () {
                    $(".mensaje").fadeOut(3000);
                }, 3000);
            });
            function eliminar() {

            }
        </script>
        <p class="mensaje">
            <%
                if (request.getParameter("mens") != null) {
                    out.println(request.getParameter("mens"));
                }
            %>
        </p>
        <header class="header">
            <div class="contenedor">
                <h1 class="logo">Can-Contento</h1>
                <span class="icon-menu" id="btn-menu"></span>
                <nav class="nav" id="nav">
                    <ul class="menu">
                        <li class="menu__item"><a class="menu__link select" href="home.jsp">Home</a></li>
                        <li class="menu__item"><a class="menu__link" href="Servicios.jsp">Servicios</a></li>
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




        <section id="agregarM">
            <h1>REGISTRAR MASCOTA</h1>
            <form action='..\..\Cliente' method=post enctype="multipart/form-data">
                <br><br><h3>Nombre</h3><input type=text name='nomp'><br>
                <h3>Nacimiento (Una fecha aproximada)</h3>
                <input type="date"  name="fecha">
                <h3>Genero</h3>
                <select name="generop">
                    <option>Macho</option>
                    <option>Hembra</option>
                    <option>Tren</option>
                </select><br>
                <h3>talla</h3>
                <select name="tallap">
                    <option>Chico</option>
                    <option>Mediano</option>
                    <option>Grande</option>
                </select><br>
                <h3>Foto</h3>
                <input type="file" name="imagenp" accept="image/x-png,image/gif,image/jpeg"><br>
                <br>
                <input type="hidden" name="place" value="pag">
                <input type="hidden" name="accion" value="registrarM">
                <input type=submit value='Aceptar'>
            </form>
        </section>
        <section>
            <h1>MIS MASCOTAS</h1>

            <%
                ArrayList<Perro> perro = (ArrayList<Perro>) request.getSession().getAttribute("miniaturaperro");
                if (perro != null) {
                    for (int i = 0; i < perro.size(); i++) {
                        String archivo = perro.get(i).getArchivoimg().toString();
                        String terminacion = archivo.substring(archivo.length() - 4, archivo.length());
                        out.println("<article " + "id=\"" + perro.get(i).getNombre() + "\"" + ">");
                        out.println(perro.get(i).getNombre() + "<br>");
                        out.println("Citas realizadas " + perro.get(i).getCodigos());
                        out.println("<img src= ../../Img/" + request.getSession().getAttribute("correo") + "/" + perro.get(i).getNombre() + terminacion + " width='200' height='100'>");
                        out.println(" <a href=\"home.jsp?id=" + perro.get(i).getNombre() + "#Editar\"><input type=\"button\" value=\"Editar\"></a>");
                        out.println(" <a href=\"home.jsp?id=" + perro.get(i).getNombre() + "#Eliminar\"><input type=\"button\" value=\"Eliminar\"></a>");
                        out.println("  <a href=\"home.jsp?id=" + perro.get(i).getNombre() + "#Agendarcita\"><input type=\"button\" value=\"Agendar cita\"></a>");
                        out.println("<br>");
                        out.println("</article>");
                    }
                } else {
                    out.print("No hay mascotas");
                }

            %>

        </section>
        <section>
            <h1>MIS CITAS</h1>

            <%                ArrayList<Cita> citas = (ArrayList<Cita>) request.getSession().getAttribute("miniaturacitas");
                if (citas != null) {
                    for (int i = 0; i < citas.size(); i++) {
                        out.println("<article " + "id=\"" + citas.get(i).getMascota() + "\"" + ">" + "<br>");
                        out.println("Cita:" + citas.get(i).getMascota() + "<br>");
                        out.println("Fecha: " + citas.get(i).getFecha() + "<br>");
                        out.println("Hora: " + citas.get(i).getHora() + "<br>");
                        out.println("Codigo: " + citas.get(i).getCodigo() + "<br>");
                        if (citas.get(i).isEstado()) {
                            out.println("Confirmada");
                        } else {
                            out.print("No confirmada");
                        }
                        out.println("<a href=\"home.jsp?id=" + citas.get(i).getMascota() + "#Editarcita\"><input type=\"button\" value=\"Editar cita\"></a>");
                        out.println("</article>");
                    }
                } else {
                    out.print("No hay mascotas");
                }

            %>

        </section>
        <div id="Eliminar" class="overlay">
            <div id="popupBody">
                <h2>Eliminar</h2>
                <a id="cerrar" href="#">&times;</a>
                <div class="popupContent">
                    <p>¿DESEA ELIMINAR A <%out.print(request.getParameter("id"));%>?</p>
                    <form action="..\..\Cliente" method="post">
                        <input type="hidden" value="eliminarM" name="accion">
                        <input type="hidden" value="<%out.print(request.getParameter("id"));%>" name="mascota">
                        <input type="hidden" value="pag" name="place">
                        <input type="submit" value="Eliminar">
                    </form>
                    <a href="#"><input type="button" value="Cancelar"></a>
                </div>        
            </div>
        </div>
        <div id="Editar" class="overlay">
            <div id="popupBody">
                <h2>Editar a <%out.print(request.getParameter("id"));%></h2>
                <a id="cerrar" href="#">&times;</a>
                <div class="popupContent">

                    <form action="..\..\Cliente" method="post" enctype="multipart/form-data">
                        <%
                            //Buscamos el objeto del perro seleccionado en el array
                            Perro datos = new Perro();
                            for (int i = 0; i < perro.size(); i++) {
                                if (perro.get(i).getNombre().equals(request.getParameter("id"))) {
                                    datos = perro.get(i);
                                }
                            }
                            out.println("<img src= ../../Img/" + request.getSession().getAttribute("correo") + "/" + datos.getNombre() + ".png width='200' height='100'>");
                        %>

                        <h3>Nombre</h3><input type=text name='nomp' value="<%out.println(datos.getNombre());%>"><br>
                        <h3>Nacimiento (Una fecha aproximada)</h3>
                        <input type="date"  name="fecha"  value="<%out.print(datos.getNac());%>">
                        <%System.out.println(datos.getNac());%>
                        <h3>Genero</h3>
                        <select name="generop">
                            <%if (datos.getGenero()) {
                                    out.print("<option selected>Macho</option>");
                                    out.print("<option>Hembra</option>");
                                } else {
                                    out.print("<option>Macho</option>");
                                    out.print("<option selected>Hembra</option>");
                                }%>
                        </select>
                        <h3>talla</h3>
                        <select name="tallap">
                            <%System.out.println("talla" + datos.getTalla());
                                String talla = datos.getTalla();
                                if (talla != null) {
                                    if (talla.equals("Chico")) {
                                        out.print("<option selected>Chico</option>");
                                        out.print("<option>Mediano</option>");
                                        out.print("<option>Grande</option>");
                                    } else if (talla.equals("Mediano")) {
                                        out.print("<option>Chico</option>");
                                        out.print("<option selected>Mediano</option>");
                                        out.print("<option>Grande</option>");
                                    } else if (talla.equals("Grande")) {
                                        out.print("<option>Chico</option>");
                                        out.print("<option>Mediano</option>");
                                        out.print("<option selected>Grande</option>");
                                    }
                                }
                            %>

                        </select>
                        <h3>Foto</h3>
                        <input type="file" name="imagenp" accept="image/x-png,image/gif,image/jpeg" >
                        <input type="hidden" value="editarM" name="accion">
                        <input type="hidden" value="<%out.print(request.getParameter("id"));%>" name="mascota">
                        <input type="hidden" value="pag" name="place">
                        <br>
                        <input type="submit" value="Editar">
                    </form>
                    <a href="#"><input type="button" value="Cancelar"></a>
                </div>        
            </div>
        </div>
        <div id="Agendarcita" class="overlay">
            <div id="popupBody">
                <h2> Agendar cita para <%out.print(request.getParameter("id"));%></h2>
                <a id="cerrar" href="#">&times;</a>
                <div class="popupContent">

                    <form action="..\..\Cliente" method="post">

                        <input type="date" name='fechacita'>
                        <input type="time" name="horacita">
                        <input type="text" name="servicio" value="caca">
                        <input type="hidden" value="agendarC" name="accion">
                        <input type="hidden" value="<%out.print(request.getParameter("id"));%>" name="mascota">
                        <input type="hidden" value="pag" name="place">
                        <br>
                        <input type="submit" value="Agendar">
                    </form>
                    <a href="#"><input type="button" value="Cancelar"></a>
                </div>        
            </div>
        </div>
        <div id="Editarcita" class="overlay">
            <div id="popupBody">
                <h2>Editar cita de <%out.print(request.getParameter("id"));%></h2>
                <a id="cerrar" href="#">&times;</a>
                <div class="popupContent">

                    <form action="..\..\Cliente" method="post">

                        <input type="date" name='fechacita'>
                        <input type="time" name="horacita">
                        <input type="text" name="servicio" value="caca">
                        <input type="hidden" value="agendarC" name="accion">
                        <input type="hidden" value="<%out.print(request.getParameter("id"));%>" name="mascota">
                        <input type="hidden" value="pag" name="place">
                        <br>
                        <input type="submit" value="Agendar">
                    </form>
                    <a href="#"><input type="button" value="Cancelar"></a>
                </div>        
            </div>
        </div>
    </body>
</html>
