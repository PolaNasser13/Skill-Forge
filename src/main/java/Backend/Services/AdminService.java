package Backend.Services;

import Backend.Database.CourseDatabase;
import Backend.Models.Course;
import java.util.ArrayList;

public class AdminService {
    private CourseDatabase courseDB;

    public AdminService() {
        courseDB = new CourseDatabase("courses.json");
    }

    public boolean approveCourse(int courseId, int adminId) {
        Course course = courseDB.getCourseById(courseId);
        if (course != null) {
            course.approveCourse(adminId);
            return courseDB.updateCourse(course);
        }
        return false;
    }

    public boolean rejectCourse(int courseId, int adminId, String reason) {
        Course course = courseDB.getCourseById(courseId);
        if (course != null) {
            course.rejectCourse(adminId, reason);
            return courseDB.updateCourse(course);
        }
        return false;
    }

    public ArrayList<Course> getPendingCourses() {
        return courseDB.getPendingCourses();
    }
}