/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import Objects.User;
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
public class UserSessionBean {

    @EJB
    private UserEntityFacade userEntityFacade;
    
    public void addUser(User user){
        UserEntity newUser = new UserEntity();
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setGender(user.getGender());
        newUser.setBirth(user.getBirth());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        userEntityFacade.create(newUser);
    }
    
    public List<UserEntity> getAll(){
        return userEntityFacade.findAll();
    }
}
