package Objects;

/**
 * Created by Sergio on 16/2/16.
 */
public class User {

    private String name, surname, birth, gender, email, password;

    public User(String name, String surname, String birth, String gender,
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String toString(){
        return name + ":" + surname + ":" + birth + ":" + gender + ":" + email + ":" + password;
    }
}
