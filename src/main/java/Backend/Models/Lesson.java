/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Models;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author pola-nasser13
 */
public class Lesson {
    private int lessonId;
    private String title;
    private String content;
    private ArrayList<String>resources;
    public Lesson(int lessonId,String title,String content){
        setLessonId(lessonId);
        setTitle(title);
        setContent(content);
        this.resources=new ArrayList<>();
    }
    public Lesson(JSONObject obj){
       this.lessonId=obj.getInt("lessonId");
       this.title=obj.getString("title");
       this.content=obj.getString("content");
       this.resources=new ArrayList<>();
       JSONArray resourceArr=obj.optJSONArray("resources");
       if(resourceArr!=null){
           for(int i=0;i<resourceArr.length();i++){
               this.resources.add(resourceArr.getString(i));
       }
       
    }
    }
    public boolean addResource(String resource){
        if(resources.contains(resource)){
            return false;
        }
        else{
            resources.add(resource);
            return true;
        }
    }
    public boolean removeResource(String resource){
        boolean removed=false;
        for(int i=0;i<resources.size();i++){
            if(resources.get(i).equals(resource)){
                resources.remove(i);
                removed=true;
                i--;
            }
        }
        return removed;
    }
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("lessonId", lessonId);
        obj.put("title", title);
        obj.put("content", content);

        JSONArray resourceArr = new JSONArray();
        for (int i = 0; i < resources.size(); i++) {
            resourceArr.put(resources.get(i));
        }
        obj.put("resources", resourceArr);

        return obj;
    }
     public int getLessonId() {
    return lessonId; }
    public String getTitle() { 
    return title; }
    public String getContent() {
    return content; }
    public ArrayList<String> getResources() {
    return resources; }
    public void setLessonId(int lessonId){
        if(lessonId<0){
            throw new IllegalArgumentException("lessonId can not be negative.");
        }
        if(lessonId==0){
            throw new IllegalArgumentException("lessonId must be greater than zero.");
        }
        if(lessonId>14){
            throw new IllegalArgumentException("lessonId must not be greater than 14.");
        }
        this.lessonId=lessonId;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setContent(String content){
        this.content=content;
    }
}
