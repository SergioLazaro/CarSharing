/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.PostSessionBean;
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
public class PostServlet extends HttpServlet {

    @EJB
    private PostSessionBean postSessionBean;
    private String postType, date, departure, destination, carType, carYear, email;
    
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
        response.setContentType("text/html;charset=UTF-8");
        
        String version = request.getParameter("version");
        postType = request.getParameter("transport");
        date = request.getParameter("date");
        departure = request.getParameter("departure");
        destination = request.getParameter("destination");
        carType = request.getParameter("cartype");
        carYear = request.getParameter("caryear");
        if(version.equals("web")){
            //Getting parameters
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("email")) {
                    email = cookie.getValue();
                }
            }
            //Add new post
            insertNewPost();
            
            //Generate the response
            try {
                response.sendRedirect("pages/index.jsp?error=0");
            } catch (IOException ex) {
                Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("pages/index.jsp?error=4");
            }
        }
        else if(version.equals("android")){
            email = request.getParameter("username");
            //Add new post
            insertNewPost(); 
            
            //Generate the response
            response.setContentType("application/json");
            JSONObject confirmation = new JSONObject();
            try{
                confirmation.put("result", "true");
                response.getWriter().write(confirmation.toString());
            }
            catch(JSONException ex){
                Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
                confirmation.put("result","false");
                response.getWriter().write(confirmation.toString());
            }
        }       
    }
    
    private void insertNewPost(){
        if(postType.equals("Car")){
            postSessionBean.insertCarPost(email,date,departure,destination,
                        carType,carYear);
        }
        else{
            postSessionBean.insertTaxiPost(email,date,departure);
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
            Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
