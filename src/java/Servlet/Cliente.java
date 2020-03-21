package Servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Clases.Cita;
import Clases.Perro;
import Clases.Producto;
import Clases.Usuario;
import Clases.UsuarioBD;
import Conexion.Conexion;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        } else if (ac.equals("eliminarM")) {
            eliminarM(request, response);
        } else if (ac.equals("editarM")) {
            editarM(request, response);
        } else if (ac.equals("agendarC")) {
            agendarC(request, response);
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
                //El numero de telefono es invalido
                if (validarTel(tel_clie) == null) {
                    //Peticion hecha desde la pagina
                    if (place.equals("pag")) {
                        String men = "Numero de telefono invalido";
                        response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);

                        //Peticion hecha desde la app    
                    } else if (place.equals("app")) {
                        System.out.println("Registro incorreto app");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("Registro", "TelefonoIn");
                            PrintWriter pw = response.getWriter();
                            pw.write(jsonObject.toString());
                            pw.print(jsonObject.toString());

                            System.out.println("Registro incorrecto" + jsonObject.toString());

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    //El numero de telefono es valido
                } else {
                    //Intentamos dar de alta al usuario en la base de datos
                    try {
                        Usuario usu = new Usuario();
                        usu.setNom(nom_clie);
                        usu.setApe(ape_clie);
                        usu.setDir(dir_clie);
                        usu.setCor(cor_clie);
                        usu.setTel(tel_clie);
                        usu.setCon(con_clie);
                        usu.setTip(3);

                        UsuarioBD u = new UsuarioBD();
                        boolean registrado = u.registrarUsuario(usu);

                        //se crea carpeta de imagenes
                        String context = request.getServletContext().getRealPath("/Img");
                        System.out.println(context);
                        File carpeta = new File(context + "\\" + cor_clie);
                        carpeta.mkdirs();

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
                    } catch (Exception efe) {
                        System.out.println(efe.toString());
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
            System.out.println("correo " + usu.getCor());
            System.out.println("contrasena " + usu.getCon());
            if (correo.equals(usu.getCor())) {
                System.out.println("correo valido si entro");
                if ((contra.equals(usu.getCon()))) {
                    System.out.println("contrase;a valida si entro");
                    if (usu.getTip() == 3) {
                        System.out.println("contrase;a valida 3");
                        misesion = request.getSession(true);
                        misesion.setAttribute("correo", usu.getCor());
                        misesion.setAttribute("miniaturaperro", miniaturaMascota(usu.getCor()));
                        misesion.setAttribute("miniaturacitas", miniaturaCita(usu.getCor()));
                        if (place.equals("pag")) {
                            //HttpSession sesion = request.getSession();
                            //sesion.setAttribute("correo", usu.getCor());
                            response.sendRedirect("JSP/cliente/home.jsp");
                        } else if (place.equals("app")) {
                            System.out.println("Contrase;a bien cliente");
                            JSONObject jsonObject1 = new JSONObject();
                            try {
                                jsonObject1.put("Login", "Cliente");
                                PrintWriter pw = response.getWriter();
                                pw.write(jsonObject1.toString());
                                pw.print(jsonObject1.toString());

                                System.out.println("Cliente inicio sesion" + jsonObject1.toString());

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    } else if (usu.getTip() == 2) {
                        if (place.equals("pag")) {
                            response.sendRedirect("HTML/empleado/home.html");
                        } else if (place.equals("app")) {
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
                        } else if (place.equals("app")) {
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
                    if (place.equals("pag")) {
                        String error = "Contrase;a incorrecta";
                        response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + error);
                    } else if (place.equals("app")) {
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
                if (place.equals("pag")) {
                    String usua = "Usuario no encontrado";
                    response.sendRedirect("JSP/SesionUsuario.jsp?mens=" + usua);

                } else if (place.equals("app")) {
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
            InputStream filecontent = null;
            String context = request.getServletContext().getRealPath("/Img");
            try {
                Part filePart = request.getPart("imagenp");
                if (filePart.getSize() > 0) {
                    System.out.println(filePart.getName());
                    System.out.println(filePart.getSize());
                    System.out.println(filePart.getContentType());
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    filecontent = filePart.getInputStream();

                    OutputStream os = new FileOutputStream(context + "\\" + correo + "\\" + nombre + ".png");
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    filecontent.close();
                    //flush OutputStream to write any buffered data to file
                    os.flush();
                    os.close();

                }
            } catch (Exception ex) {
                System.out.println("fichero: " + ex.getMessage());
            }

            if (nombre.equals("")
                    || (nacimiento.equals(""))
                    || (genero.equals(""))
                    || (talla.equals(""))
                    || (filecontent == null)) {

                if (place.equals("pag")) {
                    System.out.println("Respuesta pagina");
                    System.out.println(nombre);
                    System.out.println(nacimiento);
                    System.out.println(genero);
                    System.out.println(talla);
                    String men = "Llena todos los campos";
                    response.sendRedirect("JSP/cliente/home.jsp?mens=" + men);
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
                pe.setArchivoimg(context + "\\" + correo + "\\" + nombre + ".png");

                UsuarioBD u = new UsuarioBD();
                boolean registrado = u.registrarMascota(pe);
                //En caso de que se haya podido registrar
                if (registrado == true) {
                    misesion.removeAttribute("miniaturaperro");
                    misesion.setAttribute("miniaturaperro", miniaturaMascota(correo));
                    //Peticion hecha desde la pagina
                    if (place.equals("pag")) {
                        String men = "Registro correcto";
                        //misesion.setAttribute("miniaturaperro", miniaturaMascota(request, response));
                        response.sendRedirect("JSP/cliente/home.jsp");

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

    private void productosDisponibles(HttpServletRequest request, HttpServletResponse response) {
        //UsuarioBD u = new UsuarioBD();
        ArrayList<Producto> pros = UsuarioBD.obtenerProductos();

        //Respuesta por la peticion de la app, 
        if (request.getParameter("place") == "app") {
            //Enviar arraylist obtenida como json 
        }
    }

    private String validarTel(String tel_clie) {
        int contador = 0;
        String comparar = "0123456789";
        //System.out.println("antes del for");
        for (int i = 0; i < tel_clie.length(); i++) {
            //System.out.println("primer for");
            //System.out.println("pf " + i);
            for (int j = 0; j < comparar.length(); j++) {
                System.out.println("sf " + j);
                if (tel_clie.charAt(i) == comparar.charAt(j)) {
                    contador++;
                }
            }
        }
        System.out.println(contador + " contador");
        if (contador == 8 || contador == 10) {
            return tel_clie;
        } else {
            return null;
        }
    }

    public ArrayList<Perro> miniaturaMascota(String correo) throws IOException {
        UsuarioBD usu = new UsuarioBD();
        ArrayList<Perro> perro = usu.consutarMiniaturaPerro(correo);
        return perro;

    }

    private void eliminarM(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correo = (String) request.getSession().getAttribute("correo");
        String mascota = request.getParameter("mascota");
        if (mascota != null) {
            UsuarioBD usu = new UsuarioBD();
            boolean eliminado = usu.eliminarMascota(mascota, correo);
            if (eliminado == true) {
                request.getSession().removeAttribute("miniaturaperro");
                request.getSession().setAttribute("miniaturaperro", miniaturaMascota(correo));
                String context = request.getServletContext().getRealPath("/Img");
                File f = new File(context + "\\" + correo + "\\" + mascota + ".png");
                System.out.println(context + "\\" + correo + "\\" + mascota + ".png");
                f.delete();
                if (request.getParameter("place").equals("pag")) {
                    String men = "Se ha eliminado " + mascota;
                    response.sendRedirect("JSP/cliente/home.jsp?=" + men);

                } else if (request.getParameter("place").equals("app")) {

                }
            }
        } else {

        }
    }

    private void editarM(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            InputStream filecontent = null;
            String context = request.getServletContext().getRealPath("/Img");
            try {
                Part filePart = request.getPart("imagenp");
                if (filePart.getSize() > 0) {
                    System.out.println(filePart.getName());
                    System.out.println(filePart.getSize());
                    System.out.println(filePart.getContentType());
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    filecontent = filePart.getInputStream();

                    OutputStream os = new FileOutputStream(context + "\\" + correo + "\\" + nombre + ".png");
                    System.out.println(context + "\\" + correo + "\\" + nombre + ".png");
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    filecontent.close();
                    //flush OutputStream to write any buffered data to file
                    os.flush();
                    os.close();

                }

            } catch (Exception ex) {
                System.out.println("fichero: " + ex.getMessage());
            }

            if (nombre.equals("")
                    || (nacimiento.equals(""))
                    || (genero.equals(""))
                    || (talla.equals(""))) {

                if (place.equals("pag")) {
                    System.out.println("Respuesta pagina");
                    System.out.println(nombre);
                    System.out.println(nacimiento);
                    System.out.println(genero);
                    System.out.println(talla);
                    String men = "Llena todos los campos";
                    response.sendRedirect("JSP/cliente/home.jsp?mens=" + men);
                } else if (place.equals("app")) {

                }
            } else {
                if (filecontent != null) {
                    Perro pe = new Perro();
                    pe.setNombre(nombre);
                    pe.setNac(nacimiento);
                    if (genero.equals("Macho")) {
                        pe.setGenero(true);//true macho
                    } else {
                        pe.setGenero(false);//false hembra
                    }
                    pe.setTalla(talla);
                    pe.setDueno(correo);
                    pe.setArchivoimg(context + "\\" + correo + "\\" + nombre + ".png");
                    File imgcambiar = new File(context + "\\" + correo + "\\" + request.getParameter("mascota") + ".png");
                    imgcambiar.renameTo(new File(context + "\\" + correo + "\\" + nombre + ".png"));

                    UsuarioBD u = new UsuarioBD();
                    boolean editado = u.editarMascota(pe, request.getParameter("mascota"));
                    //En caso de que se haya podido registrar
                    if (editado == true) {
                        misesion.removeAttribute("miniaturaperro");
                        misesion.setAttribute("miniaturaperro", miniaturaMascota(correo));
                        //Peticion hecha desde la pagina
                        if (place.equals("pag")) {
                            String men = "Editado correcto";
                            //misesion.setAttribute("miniaturaperro", miniaturaMascota(request, response));
                            response.sendRedirect("JSP/cliente/home.jsp");

                            //Peticion hecha desde la app    
                        } else if (place.equals("app")) {
                            //Codigo xdxd
                        }
                        //En caso de que no se haya podido registrar    
                    } else {
                        if (place.equals("pag")) {
                            String men = "Error al editar";
                            response.sendRedirect("JSP/RegistroUsuario.jsp?mens=" + men);
                        } else if (place.equals("app")) {
                            //Codigo de la app
                        }
                    }
                } else {
                    //no hay imagen
                    Perro pe = new Perro();
                    pe.setNombre(nombre);
                    pe.setNac(nacimiento);
                    if (genero.equals("Macho")) {
                        pe.setGenero(true);//true macho
                    } else {
                        pe.setGenero(false);//false hembra
                    }
                    pe.setTalla(talla);
                    pe.setDueno(correo);
                    File imgcambiar = new File(context + "\\" + correo + "\\" + request.getParameter("mascota") + ".png");
                    imgcambiar.renameTo(new File(context + "\\" + correo + "\\" + nombre + ".png"));
                    UsuarioBD u = new UsuarioBD();
                    boolean editado = u.editarMascota(pe, request.getParameter("mascota"));

                    //En caso de que se haya podido registrar
                    if (editado == true) {
                        misesion.removeAttribute("miniaturaperro");
                        misesion.setAttribute("miniaturaperro", miniaturaMascota(correo));
                        //Peticion hecha desde la pagina
                        if (place.equals("pag")) {
                            String men = "Registro correcto";
                            //misesion.setAttribute("miniaturaperro", miniaturaMascota(request, response));
                            response.sendRedirect("JSP/cliente/home.jsp?mens=" + men);

                            //Peticion hecha desde la app    
                        } else if (place.equals("app")) {
                            //Codigo app xdxd
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
    }

    private void agendarC(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //vaciar variables
        HttpSession misesion = (HttpSession) request.getSession();
        String correo = (String) misesion.getAttribute("correo");
        String fecha = request.getParameter("fechacita");
        String hora = request.getParameter("horacita");
        String mascota = request.getParameter("mascota");
        String servicio = request.getParameter("servicio");

        //validacion sesion iniciada
        if (correo == null) {
            if (request.getParameter("place").equals("pag")) {
                response.sendRedirect("HTML/SesionUsuario.html");
            } else if (request.getParameter("app").equals("app")) {

            }

        } else {
            if (fecha.equals("") || hora.equals("") || mascota.equals("") || servicio.equals("")) {
                if (request.getParameter("place").equals("pag")) {
                    response.sendRedirect("home.jsp");
                } else if (request.getParameter("app").equals("app")) {

                }
            } else {
                System.out.println("datos completos");
                Cita cita = new Cita();
                cita.setCliente(correo);
                cita.setCodigo(cita.generarCodigo());
                cita.setEstado(false);
                cita.setFecha(fecha);
                cita.setHora(hora);
                cita.setMascota(mascota);
                UsuarioBD usu = new UsuarioBD();
                if (usu.altaCita(cita) == true) {
                    if (request.getParameter("place").equals("pag")) {
                        System.out.println("entro a la respuesta pagina");
                        String men="Cita enviada, espera la respuesta de confirmacion";
                        response.sendRedirect("JSP/cliente/home.jsp?mens=" + men);
                    } else if (request.getParameter("app").equals("app")) {

                    }
                }

            }
        }
    }
    public ArrayList<Cita> miniaturaCita(String correo) throws IOException {
        UsuarioBD usu = new UsuarioBD();
        ArrayList<Cita> citas = usu.consutarMiniaturaCita(correo);
        return citas;

    }
}
