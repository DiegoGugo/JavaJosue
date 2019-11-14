<%-- 
    Document   : SesionUsuario
    Created on : Nov 10, 2019, 12:36:02 AM
    Author     : jela3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <title>IniciarSesion</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="Css/Estilos.css">
                <link rel="stylesheet" type="text/css" href="css/styles.css">
                <link href="https://file.myfontastic.com/mqb57Y99DoZRtKZYZ7jXPD/icons.css" rel="stylesheet">
                <link rel="icon" href="Img/Techno-bit.png">
    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                setTimeout(function () {
                    $(".mensaje").fadeOut(3000);
                }, 3000);
            });
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
                                    <li class="menu__item"><a class="menu__link select" href="/index.html">Inicio</a>
                                        <ul class="submenu">
                                            <li class="menu__item"><a class="menu__link" herf=""></a>   Perfil   </li> 
                                            <li class="menu__item"><a class="menu__link" herf="">Reportar Problema</a></li>
                                        </ul>
                                    </li>
                                    <li class="menu__item"><a class="menu__link" href="RegistroUsuario.html">¿No tienes cuenta? Registrate</a></li>
                                    <li class="menu__item"><a class="menu__link" href="">                   </a></li>
                                       <li class="menu__item"><a class="menu__link" href="">                   </a></li>
                                </ul>
                                </nav>
                            </div>
                    </header>
        <form action="..\Cliente" method="post">
            <h1>Ingrese sus datos</h1>
            <br><br><br>
            <h3>Ingresa tu correo Electronico</h3>
            <input type="text" name="corr_compara">            
            <h3>Ingresa tu contraseña</h3>
            <input type="text" name="contra_compara">
            <br><br><br>
            <input type="hidden" value="login" name="accion">
            <input type="hidden" value="pag" name="place">
            <input type="submit" value="Acceder">

        </form>
    </body>
</html>
