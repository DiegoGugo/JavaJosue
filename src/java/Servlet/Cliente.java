package Servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Clases.Perro;
import Clases.Producto;
import Clases.Usuario;
import Clases.UsuarioBD;
import Conexion.Conexion;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jela3
 */
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class Cliente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ac = request.getParameter("accion");
        if (ac.equals("alta")) {
            altaCliente(request, response);
        } else if (ac.equals("login")) {
            login(request, response);
        } else if (ac.equals("registrarM")) {
            regisrarM(request, response);
        } else if (ac.equals("obtenerP")) {
            productosDisponibles(request, response);
        }

        //
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();

        sesion.invalidate();
        response.sendRedirect("index.html");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void altaCliente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("llegaste al metodo altaCliente");

        String nom_clie = request.getParameter("nom_clie");
        String ape_clie = request.getParameter("ape_clie");
        String dir_clie = request.getParameter("dir_clie");
        String cor_clie = request.getParameter("cor_clie");
        String tel_clie = request.getParameter("tel_clie");
        String con_clie = request.getParameter("con_clie");
        String con2_clie = request.getParameter("con2_clie");
        String place = request.getParameter("place");

        //Campos vacios
        if (nom_clie.equals("")
                || ape_clie.equals("")
                || dir_clie.equals("")
                || cor_clie.equals("")
                || tel_clie.equals("")
                || con_clie.equals("")
                || con2_clie.equals("")) {

            System.out.println("Campos vacios");
            //Respuesta en caso de que sea la pagina la que hizo la peticion
            if (place.equals("pag")) {
                System.out.println("Respuesta pagina");

                String men = "Llena todos los campos";
                response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);

                //Respuesta en caso de que la app haya hecho la peticion    
            } else if (place.equals("app")) {
                System.out.println("Campos vacios app registro");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Registro", "FalloV");
                    PrintWriter pw = response.getWriter();
                    pw.write(jsonObject.toString());
                    pw.print(jsonObject.toString());

                    System.out.println("Registro vacio" + jsonObject.toString());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            // Campos NO vacios
        } else {
            //Las contraseñas coinciden
            if (con_clie.equals(con2_clie)) {
                //Intentamos dar de alta al usuario en la base de datos
                try {
                    Usuario usu = new Usuario();
                    usu.setNom(nom_clie);
                    usu.setApe(ape_clie);
                    usu.setDir(dir_clie);
                    usu.setCor(cor_clie);
                    usu.setTel(Integer.parseInt(tel_clie));
                    usu.setCon(con_clie);
                    usu.setTip(3);

                    UsuarioBD u = new UsuarioBD();
                    boolean registrado = u.registrarUsuario(usu);
                    //En caso de que se haya podido registrar
                    if (registrado == true) {

                        //Peticion hecha desde la pagina
                        if (place.equals("pag")) {
                            String men = "Registro correcto";
                            response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);

                            //Peticion hecha desde la app    
                        } else if (place.equals("app")) {
                            System.out.println("Registro correto app");
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("Registro", "Correcto");
                                PrintWriter pw = response.getWriter();
                                pw.write(jsonObject.toString());
                                pw.print(jsonObject.toString());

                                System.out.println("Registro correcto" + jsonObject.toString());

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        //En caso de que no se haya podido registrar    
                    } else {
                        if (place.equals("pag")) {
                            String men = "Error al registrar, verifica que el usuario no sea repetido";
                            response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                        } else if (place.equals("app")) {
                            System.out.println("Campos vacios app registro");
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("Registro", "Fallo");
                                PrintWriter pw = response.getWriter();
                                pw.write(jsonObject.toString());
                                pw.print(jsonObject.toString());

                                System.out.println("Registro fallo" + jsonObject.toString());

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    //El numero de telefono no es telefono
                } catch (NumberFormatException nfe) {
                    System.out.println("El numero no es numero");
                    if (place.equals("pag")) {
                        System.out.println("Respuesta pagina");

                        String men = "El numero de telefono no es telefono";
                        response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);

                        //Respuesta en caso de que la app haya hecho la peticion    
                    } else if (place.equals("app")) {
                        System.out.println("Campos vacios app registro");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Registro", "FalloN");
                            PrintWriter pw = response.getWriter();
                            pw.write(jsonObject.toString());
                            pw.print(jsonObject.toString());

                            System.out.println("Registro fallo telefono no es numero" + jsonObject.toString());

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

                //Las contrase;as no coinciden    
            } else {
                //Respuesta en caso de que la pagina haya echo la peticion
                if (place.equals("pag")) {
                    System.out.println("Respuesta pagina ");

                    String men = "Contrase;as no coinciden";
                    response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                    //Respuesta en caso de que la app haya hecho la peticion    
                } else if (place.equals("app")) {
                    System.out.println("Contrase;as distintas app registro");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Registro", "FalloC");
                        PrintWriter pw = response.getWriter();
                        pw.write(jsonObject.toString());
                        pw.print(jsonObject.toString());

                        System.out.println("Registro contrase;as no coinciden" + jsonObject.toString());

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }
        }

    }//fin de metodo altaCliente

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correo = request.getParameter("corr_compara");
        String contra = request.getParameter("contra_compara");
        String place = request.getParameter("place");
        HttpSession misesion = request.getSession();
        //HttpSession sesion = request.getSession();
        //campos vacios 
        if (correo.equals("") || contra.equals("")) {
            System.out.println("Campos vacios");
            if (place.equals("pag")) {
                System.out.println("Respuesta pagina");

                String men = "Llena todos los campos";
                response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + men);
            } else if (place.equals("app")) {
                System.out.println("Campos vacios app login");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Login", "FalloV");
                    PrintWriter pw = response.getWriter();
                    pw.write(jsonObject.toString());
                    pw.print(jsonObject.toString());

                    System.out.println("Registro vacio" + jsonObject.toString());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            Usuario usu = UsuarioBD.VerificarUsuario(correo);
            System.out.println(usu.getCor());
            if (correo.equals(usu.getCor())) {
                if ((contra.equals(usu.getCon())) && misesion.getAttribute("usuario") == null) {
                    if (usu.getTip() == 3) {
                        if (place.equals("pag")) {
                            //HttpSession sesion = request.getSession();
                            //sesion.setAttribute("correo", usu.getCor());
                            misesion = request.getSession(true);
                            misesion.setAttribute("correo", usu.getCor());

                            response.sendRedirect("HTML/cliente/home.html");
                        } else if (place == "app") {
                            System.out.println("Inicio Sesion Cliente");
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("Login", "Cliente");
                                PrintWriter pw = response.getWriter();
                                pw.write(jsonObject.toString());
                                pw.print(jsonObject.toString());

                                System.out.println("Login cliente" + jsonObject.toString());

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                             }
                        }
                    } else if (usu.getTip() == 2) {
                        if (place.equals("pag")) {
                            response.sendRedirect("HTML/empleado/home.html");
                        } else if (place == "app") {
                            System.out.println("Inicio Sesion Empleado");
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("Login", "Empleado");
                                PrintWriter pw = response.getWriter();
                                pw.write(jsonObject.toString());
                                pw.print(jsonObject.toString());

                                System.out.println("Login exitoso empleado" + jsonObject.toString());

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    } else if (usu.getTip() == 1) {
                        if (place.equals("pag")) {
                            response.sendRedirect("HTML/encargado/home.html");
                        } else if (place == "app") {
                            System.out.println("Inicio Sesion Encargado");
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("Login", "Encargado");
                                PrintWriter pw = response.getWriter();
                                pw.write(jsonObject.toString());
                                pw.print(jsonObject.toString());

                                System.out.println("Login exitoso Encargado" + jsonObject.toString());

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    if (place == "pag") {
                        String error = "Contrase;a incorrecta";
                        response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + error);
                    } else if (place == "app") {
                        System.out.println("Inicio Sesion contrase;a fail");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Login", "Contra");
                            PrintWriter pw = response.getWriter();
                            pw.write(jsonObject.toString());
                            pw.print(jsonObject.toString());

                            System.out.println("Login fail, contra;a incorrecta" + jsonObject.toString());

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (place == "pag") {
                    String usua = "Usuario no encontrado";
                    response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + usua);

                } else if (place == "app") {
                    System.out.println("Inicio Sesion usuario fail");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("Login", "Usuario");
                        PrintWriter pw = response.getWriter();
                        pw.write(jsonObject.toString());
                        pw.print(jsonObject.toString());

                        System.out.println("Login fail usuario" + jsonObject.toString());

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void regisrarM(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        System.out.println("correo sesion mascota " + correo);
        String place = request.getParameter("place");
        //En caso de no haber iniciado sesion
        if (correo == null) {
            if (place.equals("pag")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            }
        } else {
            //Verificamos que se hayan llenado todos los campos
            String nombre = request.getParameter("nomp");
            String nacimiento = request.getParameter("fecha");//aaaa-mm-dd
            String genero = request.getParameter("generop");
            String talla = request.getParameter("tallap");
            InputStream inputStream = null;
            try {
                Part filePart = request.getPart("imagenp");
                if (filePart.getSize() > 0) {
                    System.out.println(filePart.getName());
                    System.out.println(filePart.getSize());
                    System.out.println(filePart.getContentType());
                    inputStream = filePart.getInputStream();
                }
            } catch (Exception ex) {
                System.out.println("fichero: " + ex.getMessage());
            }

            if (nombre.equals("")
                    || (nacimiento.equals(""))
                    || (genero.equals(""))
                    || (talla.equals(""))
                    || (inputStream == null)) {

                if (place.equals("pag")) {
                    System.out.println("Respuesta pagina");
                    System.out.println(nombre);
                    System.out.println(nacimiento);
                    System.out.println(genero);
                    System.out.println(talla);
                    String men = "Llena todos los campos";
                    response.sendRedirect("JSP/RegistrarMascota.jsp?mens=" + men);
                } else if (place.equals("app")) {

                }
            } else {
                Perro pe = new Perro();
                pe.setNombre(nombre);
                pe.setNac(nacimiento);
                if (genero.equals("Macho")) {
                    pe.setGenero(true);//true macho
                } else {
                    pe.setGenero(false);//false hembra
                }
                pe.setTalla(talla);
                pe.setCodigos(0);
                pe.setDueno(correo);
                pe.setArchivoimg(inputStream);

                UsuarioBD u = new UsuarioBD();
                boolean registrado = u.registrarMascota(pe);
                //En caso de que se haya podido registrar
                if (registrado == true) {

                    //Peticion hecha desde la pagina
                    if (place.equals("pag")) {
                        String men = "Registro correcto";
                        response.sendRedirect("JSP/RegistrarMascota.jsp?mens=" + men);

                        //Peticion hecha desde la app    
                    } else if (place.equals("app")) {
                        //Codigo xdxd
                    }
                    //En caso de que no se haya podido registrar    
                } else {
                    if (place.equals("pag")) {
                        String men = "Error al registrar";
                        response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                    } else if (place.equals("app")) {
                        //Codigo de la app
                    }
                }
            }
        }
    }
//Este metodo solo se ocupa en la app, ya que la consulta de los producto se hace en el jsp de Servicios

    private void productosDisponibles(HttpServletRequest request, HttpServletResponse response) {
        //UsuarioBD u = new UsuarioBD();
        ArrayList<Producto> pros = UsuarioBD.obtenerProductos();

        //Respuesta por la peticion de la app, 
        if (request.getParameter("place") == "app") {
            //Enviar arraylist obtenida como json 
        }
    }
}
