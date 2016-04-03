/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.UserEntity;
import ejb.UserSessionBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Sergio
 */
public class LoginServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String version = request.getParameter("version");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("NUEVA PETICION DE " + email + " - " + password + " - " + version);
        try{
            if(email != null && password != null){
                List<UserEntity> list = userSessionBean.getAll();
                boolean found = false;
                int i = 0;
                while(!found && i < list.size()){
                    UserEntity user = list.get(i);
                    System.err.println(user.toString());
                    if(user.getEmail().equals(email) && 
                            user.getPassword().equals(password)){
                        found = true;
                    }
                    i++;
                }
                if(version.equals("web")){
                    if(found){
                        Cookie cookie = createCookie(email);
                        response.addCookie(cookie);
                        response.sendRedirect("pages/index.jsp");
                    }
                    else{
                        response.sendRedirect("pages/index.jsp?error=3");
                    }
                }
                else if(version.equals("android")){
                    response.setContentType("application/json");
                    JSONObject confirmation = new JSONObject();
                    if(found){
                        confirmation.put("result","true");
                    }
                    else{
                        confirmation.put("result","false");
                    }
                    response.getWriter().write(confirmation.toString());
                }
            }
            else{
                response.sendRedirect("pages/index.jsp?error=3");
            }
        }
        catch(Exception ex){
            Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private Cookie createCookie(String email){
        //User cookies should be created
        Cookie userCookie = new Cookie("email",email);
        userCookie.setMaxAge(60*60*24*365);
        return userCookie;
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
        processRequest(request, response);
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
    
    
    
}
