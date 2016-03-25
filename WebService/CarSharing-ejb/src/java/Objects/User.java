/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Sergio
 */
public class User {
    
    private String name;
    private String surname;
    private String gender;
    private String birth;
    private String email;
    private String password;
    
    public User(String name, String surname, String gender, String birth,
            String email, String password){
            this.name = name;
            this.surname = surname;
            this.birth = birth;
            this.gender = gender;
            this.email = email;
            this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", surname=" + surname + ", gender=" + gender + ", birth=" + birth + ", email=" + email + ", password=" + password + '}';
    }
    
}
