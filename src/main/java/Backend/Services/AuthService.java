/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Services;

import Backend.Database.UserDatabase;
import Backend.Models.Instructor;
import Backend.Models.Student;
import Backend.Models.User;
import org.json.JSONObject;
/**
 *
 * @author pola-nasser13
 */
public class AuthService {
    UserDatabase users;
    
    AuthService(){
        users = new UserDatabase("users.json");
        users.readFromFile();
    }
    
    JSONObject login(String email, String enteredPassword){
        JSONObject userJSON = users.getUserByEmail(email);
        String storedPassword = userJSON.getString("password");
        String hashedPassword = User.hash(enteredPassword);
        if(!storedPassword.equals(hashedPassword)){
            return null;
        }
       return userJSON; 
    }
    
    boolean signup(String userId, String role, String username, String password, String id){
         User user;
        if(role.equals("Student")){
             user = new Student(userId, role, username, password,id);
            
        }
        else if(role.equals("Instructor")){
            user = new Instructor(userId, role, username, password,id);
        }
        JSONObject userJSON = user.toJSON();
        boolean insertStatus = users.insertRecord(userJSON);
        return insertStatus;
    }
    
    
}
