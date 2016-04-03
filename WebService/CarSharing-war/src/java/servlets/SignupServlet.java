/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Objects.User;
import ejb.UserSessionBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sergio
 */
public class SignupServlet extends HttpServlet {

    @EJB
    private UserSessionBean userSessionBean;
    
    
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
            throws ServletException, IOException, JSONException {
        //Getting parameters
        String version = request.getParameter("version");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String gender = request.getParameter("gender");
        String birth = request.getParameter("birth");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(name != null && surname != null && gender != null && birth != null
                    && email != null && password != null){
            User user = new User(name,surname,gender,birth,email,password);
            userSessionBean.addUser(user);
            System.err.println(user.toString());
            if(version.equals("web")){
                response.setContentType("text/html;charset=UTF-8");
                Cookie cookie = new Cookie("email",email);
                cookie.setMaxAge(60*60*24*365);
                response.addCookie(cookie);                    
                response.sendRedirect("pages/index.jsp?error=1");
            }
            else if(version.equals("android")){
                response.setContentType("application/json");
                JSONObject confirmation = new JSONObject();
                confirmation.put("result","true");
                response.getWriter().write(confirmation.toString());
            }
        }
        else{
            if(version.equals("web")){
                response.setContentType("text/html;charset=UTF-8");
                response.sendRedirect("pages/index.jsp?error=2");
            }
            else if(version.equals("android")){
                response.setContentType("application/json");
                JSONObject confirmation = new JSONObject();
                confirmation.put("result","false");
                response.getWriter().write(confirmation.toString());
            }
        }

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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
