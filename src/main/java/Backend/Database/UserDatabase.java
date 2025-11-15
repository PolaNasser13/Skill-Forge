/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Database;

import Backend.Models.User;
import Backend.Models.Student;
import Backend.Models.Instructor;
import org.json.JSONObject;
/**
 *
 * @author pola-nasser13
 */
public class UserDatabase extends Database<User> {

    public UserDatabase(String filename) {
        super(filename);
    }

    @Override
    public User createRecordFrom(JSONObject j) {
        try {
            String role = j.getString("role");
            if (role.equalsIgnoreCase("student")) {
                return new Student(j);
            } else if (role.equalsIgnoreCase("instructor")) {
                return new Instructor(j);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void insertRecord(User user) {
        if (user == null) return;
        if (contains(user.getId())) return;

        getRecords().put(user.toJson());
        saveToFile();
    }

    public User getUserById(int id) {
        for (int i = 0; i < getRecords().length(); i++) {
            JSONObject j = getRecords().getJSONObject(i);
            if (j.getInt("userId") == id) return createRecordFrom(j);
        }
        return null;
    }

    public boolean contains(int userId) {
        return getUserById(userId) != null;
    }
}