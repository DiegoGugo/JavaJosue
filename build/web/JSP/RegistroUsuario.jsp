<%-- 
    Document   : RegistroUsuario
    Created on : Nov 9, 2019, 8:52:34 PM
    Author     : jela3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Registrar</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="Css/Estilos.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link href="https://file.myfontastic.com/mqb57Y99DoZRtKZYZ7jXPD/icons.css" rel="stylesheet">
        <link rel="icon" href="Img/Techno-bit.png">
    </head>
    <body>
        <script>
            $(document).ready(function () {
                setTimeout(function () {
                    $(".mensaje").fadeOut(3000);
                }, 3000);
            });

            function Registro_Exitoso() {
                window.location = '../../index.html';
                alert("Estas registrado");
            }
            function No_coinciden() {
                alert("Las contraseñas no coinciden");
                window.location = '../../HTML/RegistroUsuario.html';
            }
            function camp_vacios() {
                alert("Hay espacios vacios");
                //window.location = '../HTML/RegistroUsuario.html';
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
                        <li class="menu__item"><a class="menu__link" href="../index.html">Inicio</a></li>
                        <li class="menu_item"><a class="menu__link" href="../HTML/SesionUsuario.html">Iniciar Sesion</a></li>
                        <li class="menu__item"><a class="menu__link select" href="RegistroUsuario.html">Registrarse</a></li>
                    </ul>
                </nav>
            </div>
        </header>
        <form action="..\Cliente" method="post">
            <h1>Llena los siguientes campos</h1>
            <br><br>
            <h3>Nombre</h3>
            <input type=text name="nom_clie"><br>
            <h3>Apellido</h3>
            <input type=text name="ape_clie"><br>
            <h3>Direccion</h3>
            <input type=text name="dir_clie"><br>
            <h3>Correo</h3>
            <input type=text name="cor_clie"><br>
            <h3>N° Telefonico</h3>
            <input type=text name="tel_clie"><br>
            <h3>Contraseña</h3>
            <input type=password name="con_clie"><br>
            <h3>Confirmar contraseña</h3>
            <input type=password name="con2_clie"><br>
            <br>
            <input type="hidden" value="alta" name="accion">
            <input type="hidden" value="pag" name="place">
            <input type=submit value="Aceptar">

        </form>
    </body>
</html>
