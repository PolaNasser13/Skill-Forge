package Backend.Services;

import Backend.Database.CourseDatabase;
import Backend.Database.InstructorDatabase;
import Backend.Models.Course;
import Backend.Models.Instructor;
import Backend.Models.Lesson;
import java.util.ArrayList;
import org.json.JSONObject;

public class InstructorService {

    private CourseDatabase courses;
    private InstructorDatabase instructors;
    private Instructor instructor;

    public InstructorService(Instructor instructor) {
        this.instructor = instructor;
        courses = new CourseDatabase("courses.json");
        courses.readFromFile();
        instructors = new InstructorDatabase("instructors.json"); // make sure the filename matches
        instructors.readFromFile();
    }

    public boolean createCourse(int courseId, String title, String description) {
        Course newCourse = new Course(courseId, title, description, instructor.getUserId());
        boolean addStatus = courses.insertRecord(newCourse.toJSON());
        if (addStatus) {
            System.out.println("Added Course successfully!");
            instructor.addCourseId(courseId);
            save(); // persist changes
        }
        return addStatus;
    }

    public boolean editCourse(Course c) {
        boolean updateStatus = courses.updateCourse(c);
        if (updateStatus) {
            System.out.println("Course updated successfully!");
            save();
            return true;
        }
        System.out.println("Failed to edit! Course not found.");
        return false;
    }

    public boolean deleteCourse(Course c) {
        boolean deleteStatus = courses.deleteCourse(c.getCourseId());
        if (deleteStatus) {
            System.out.println("Deleted Course successfully!");
            instructor.getCreatedCourseIds().remove((Integer)c.getCourseId());
            save();
        }
        return deleteStatus;
    }

    public Course getCourseById(int id) {
        Course c = courses.getCourseById(id);
        if (c == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("Course found successfully!");
        }
        return c;
    }

    public boolean addLesson(Course c, int lessonId, String title, String content, ArrayList<String> optionalResources) {
        Lesson newLesson = new Lesson(lessonId, title, content, optionalResources);
        boolean addStatus = courses.addLesson(c.getCourseId(), newLesson);
        if (addStatus) {
            System.out.println("Added Lesson successfully!");
            save();
        }
        return addStatus;
    }

    public boolean deleteLesson(Course c, int lessonId, String title, String content) {
        boolean deleteStatus = courses.deleteLesson(c.getCourseId(), lessonId);
        if (deleteStatus) {
            System.out.println("Deleted Lesson successfully!");
            save();
        }
        return deleteStatus;
    }

    public boolean editLesson(Course c, Lesson l) {
        boolean updateStatus = courses.updateLesson(c.getCourseId(), l);
        if (updateStatus) {
            System.out.println("Lesson updated successfully!");
            save();
            return true;
        }
        System.out.println("Failed to edit! Lesson not found.");
        return false;
    }

    public ArrayList<Integer> getEnrolledStudentsIds(Course c) {
        return c.getStudentIds();
    }

    public ArrayList<Lesson> getLessons(Course c) {
        return c.getLessons();
    }

    public ArrayList<Integer> getCreatedCoursesIds() {
        return instructor.getCreatedCourseIds();
    }

    private void save() {
        courses.saveToFile();
        instructors.saveToFile();
    }

    public void logout() {
        save();
    }
}
