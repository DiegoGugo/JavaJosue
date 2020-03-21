<%-- 
    Document   : Menu
    Created on : 22/04/2019, 12:25:02 PM
    Author     : DataOS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
        <link rel="icon" href="Img/Logochido.ico">
        <link rel="stylesheet" href="Css/EstilosMenu.css">
    </head>
    
    <body>
       
        <header class="header">
        <div class="contenedor">
        <h1 class="textolog">TransportOS</h1>
        <span class="icon-menu" id="btn-menu"></span>
        <nav class="nav" id="nav">
        <ul class="menu">
            <li class="menu__item"><a class="menu__link select" href="index.html">Inicio</a></li>
            <li class="menu__item"><a class="menu__link" href="Contacto.html">Contactanos</a></li>
            <li class="menu__item"><a class="menu__link" href="Inicio.html">Miembros</a></li>
            <li class="menu__item"><a class="menu__link" href="Inicio de Sesion.jsp">Iniciar Sesión</a></li>
            <li class="menu__item"><a class="menu__link" href="Registro.html">Registro</a></li>
        </ul>
        </nav>
        </div>
        </header>    
        <script src="Js/Menu.js"></script>
        <center><h3>Selecciona alguna ciudad</h3></center>
        <video src="Mp4/lanight.mov" autoplay loop></video>
        <center>
        <div class="Ciudades">
        <div class="CDMX">
            <img src="Img/cdmx.png" onmouseover="src='Img/cdmx.png'" onmouseout="src='Img/demx 12.png'">
            <h2 class="c1">CDMX</h2>
        </div>
        <div class="MONTEREY">
            <img src="Img/Minerva.png">
            <h2 class="c2">MONTERREY</h2>
        </div>
        <div class="GUADALAJARA">
            <img src="Img/Neptuno.png">
            <h2 class="c3">GUADALAJARA</h2>
        </div>
        </div>
        </center>

         
    </body>
</html>
