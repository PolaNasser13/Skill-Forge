/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Services;

import Backend.Database.CourseDatabase;
import Backend.Database.UserDatabase;
import Backend.Models.Certificate;
import Backend.Models.Course;
import Backend.Models.Student;

/**
 *
 * @author HP_Laptop
 */
public class CertificateService {
    
    private Certificate certificate;
    private UserDatabase users;
    private CourseDatabase courses;
    private Course c;
    private CourseService courseService;
    
    public CertificateService(Certificate certificate){
        this.certificate = certificate;
        this.users = new UserDatabase("users.json");
        courses = new CourseDatabase("courses.json");
        c = courses.getCourseById(certificate.getCourseId());
        courseService = new CourseService(c);
    }
    
    public String getStudentName(){
       Student s = courseService.getStudentById(certificate.getStudentId());
       return s.getUsername();
    }
    
    public String getCourseTitle(){
       return c.getTitle();
    
    }
    
    public String getInstructorName(){
        return courseService.getInstructorName();
    }
    
}
