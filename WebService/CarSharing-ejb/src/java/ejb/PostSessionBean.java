/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Sergio
 */
@Stateless
@LocalBean
public class PostSessionBean {

    @EJB
    private PostEntityFacade postEntityFacade;

    
    public void insertCarPost(String username, String date, String departure, 
            String destination, String carType, String carYear){
        PostEntity post = new PostEntity();
        post.setPostType("car");
        post.setUsername(username);
        post.setDate(date);
        post.setDeparture(departure);
        post.setDestination(destination);
        post.setCarType(carType);
        post.setCarYear(carYear);
        postEntityFacade.create(post);

    }
    
    public void insertTaxiPost(String username, String date, String departure){
        PostEntity post = new PostEntity();
        post.setPostType("taxi");
        post.setUsername(username);
        post.setDate(date);
        post.setDeparture(departure);
        postEntityFacade.create(post);
    }
    
    public List<PostEntity> getPosts(){
        return postEntityFacade.findAll();
    }
}
