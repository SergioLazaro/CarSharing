/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import ejb.PostEntity;
import ejb.PostSessionBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Sergio
 */
public class ListPostServlet extends HttpServlet {

    @EJB
    private PostSessionBean postSessionBean;

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
        try (PrintWriter out = response.getWriter()) {
            List<PostEntity> array = postSessionBean.getPosts();
            String option = request.getParameter("type");
            JSONArray jsonArray = new JSONArray();
            if(option.equals("Car")){
                jsonArray = populateCar(array);
            }
            else if(option.equals("Taxi")){
                jsonArray = populateTaxi(array);
            }
            JSONObject mainJSON = new JSONObject();
            mainJSON.put("posts", jsonArray);
            response.setContentType("application/json");
            response.getWriter().write(mainJSON.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private JSONArray populateCar(List<PostEntity> array){
        JSONArray jsonArray = new JSONArray();
        try{
            JSONObject jsonObject;
            for(int i = 0; i < array.size(); i++){
                if(array.get(i).getPostType().equals("car")){
                    jsonObject = createJSONObject(array.get(i));
                    jsonArray = jsonArray.put(jsonObject);
                }
                
            }
            return jsonArray;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    private JSONArray populateTaxi(List<PostEntity> array){
        JSONArray jsonArray = new JSONArray();
        try{
            JSONObject jsonObject;
            for(int i = 0; i < array.size(); i++){
                if(array.get(i).getPostType().equals("taxi")){
                    jsonObject = createJSONObject(array.get(i));
                    jsonArray = jsonArray.put(jsonObject);
                }
            }
            return jsonArray;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    private JSONObject createJSONObject(PostEntity post){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("postType", post.getPostType());
            jsonObject.put("username",post.getUsername());
            jsonObject.put("date",post.getDate());
            jsonObject.put("departure",post.getDeparture());
            jsonObject.put("destination",post.getDestination());
            jsonObject.put("carType",post.getCarType());
            jsonObject.put("carYear",post.getCarYear());
            return jsonObject;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
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
