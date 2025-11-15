/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Database;

import Backend.Models.Course;
import Backend.Models.Lesson;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author pola-nasser13
 */
public class CourseDatabase extends Database<Course> {

    public CourseDatabase(String filename) {
        super(filename);
    }

    @Override
    public Course createRecordFrom(JSONObject j) {
        try {
            return new Course(j);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void insertRecord(Course course) {
        if (course == null) return;
        if (contains(course.getCourseId())) return;

        getRecords().put(course.toJson());
        saveToFile();
    }

    public Course getCourseById(int id) {
        for (int i = 0; i < getRecords().length(); i++) {
            JSONObject j = getRecords().getJSONObject(i);
            if (j.getInt("courseId") == id) {
                return createRecordFrom(j);
            }
        }
        return null;
    }

    public boolean contains(int courseId) {
        return getCourseById(courseId) != null;
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> list = new ArrayList<>();

        for (int i = 0; i < getRecords().length(); i++) {
            Course c = createRecordFrom(getRecords().getJSONObject(i));
            if (c != null) list.add(c);
        }

        return list;
    }
    
    public void updateCourse(Course course) {
        if (course == null) return;

        for (int i = 0; i < getRecords().length(); i++) {
            JSONObject j = getRecords().getJSONObject(i);

            if (j.getInt("courseId") == course.getCourseId()) {
                getRecords().put(i, course.toJson());
                saveToFile();
                return;
            }
        }
    }

    public void deleteCourse(int courseId) {

        JSONArray newRecords = new JSONArray();

        for (int i = 0; i < getRecords().length(); i++) {
            JSONObject j = getRecords().getJSONObject(i);
            if (j.getInt("courseId") != courseId) {
                newRecords.put(j);
            }
        }

        clearRecords();
        for (int i = 0; i < newRecords.length(); i++) {
            getRecords().put(newRecords.getJSONObject(i));
        }

        saveToFile();
    }

    private void clearRecords() {
        while (getRecords().length() > 0) {
            getRecords().remove(0);
        }
    }


    public void enrollStudent(int courseId, int studentId) {
        for (int i = 0; i < getRecords().length(); i++) {
            JSONObject j = getRecords().getJSONObject(i);

            if (j.getInt("courseId") == courseId) {

                JSONArray students = j.optJSONArray("students");
                if (students == null) students = new JSONArray();

                boolean exists = false;
                for (int k = 0; k < students.length(); k++) {
                    if (students.getInt(k) == studentId) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) students.put(studentId);

                j.put("students", students);
                getRecords().put(i, j);

                saveToFile();
                return;
            }
        }
    }


    public void addLesson(int courseId, Lesson lesson) {
        if (lesson == null) return;

        for (int i = 0; i < getRecords().length(); i++) {
            JSONObject j = getRecords().getJSONObject(i);

            if (j.getInt("courseId") == courseId) {

                JSONArray lessons = j.optJSONArray("lessons");
                if (lessons == null) lessons = new JSONArray();

                lessons.put(lesson.toJson());

                j.put("lessons", lessons);
                getRecords().put(i, j);

                saveToFile();
                return;
            }
        }
    }

    public void removeLesson(int courseId, int lessonId) {

        for (int i = 0; i < getRecords().length(); i++) {
            JSONObject j = getRecords().getJSONObject(i);

            if (j.getInt("courseId") == courseId) {

                JSONArray lessons = j.optJSONArray("lessons");
                if (lessons == null) return;

                JSONArray newLessons = new JSONArray();

                for (int k = 0; k < lessons.length(); k++) {
                    JSONObject lj = lessons.getJSONObject(k);
                    if (lj.getInt("lessonId") != lessonId) {
                        newLessons.put(lj);
                    }
                }

                j.put("lessons", newLessons);
                getRecords().put(i, j);

                saveToFile();
                return;
            }
        }
    }
}