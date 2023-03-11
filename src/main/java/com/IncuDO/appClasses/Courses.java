package com.IncuDO.appClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Courses {
    private int id, hoursLength;
    private String name, description, date, location, available; 

    public Courses(int id, String name, String description, String date, int hoursLength, String location, String available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.hoursLength = hoursLength;
        this.location = location;
        this.available = available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public void setHoursLength(int hoursLength) {
        this.hoursLength = hoursLength;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getHoursLength() {
        return hoursLength;
    }

    public String getLocation() {
        return location;
    }

    public String getAvailable() {
        return available;
    }

    public static ArrayList<Courses> loadCourse(String fileName) throws IOException {
        BufferedReader reader = null;
        ArrayList<Courses> courses = new ArrayList<>();
        try {            
            FileReader fileReader = new FileReader(fileName);
            reader = new BufferedReader(fileReader);
            String line;
            reader.readLine();            
            while((line = reader.readLine())!=null){                
                String[] data = line.split(";");
                if (Arrays.stream(data).allMatch(token -> token.equals(""))) {
                    continue; 
                }
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String description = data[2];
                String date = data[3];
                int hoursLength = Integer.parseInt(data[4]);
                String location = data[5];
                String available = data[6];

                Courses course = new Courses(id, name, description, date, hoursLength, location, available);
                courses.add(course);                                
            }
        }catch (FileNotFoundException e) {            
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            reader.close();
        }        
        return courses;
    }

    public String toString() {
        return "ID: " + id + "; Name: " + name + "; Description: " + description + "; Date: " + date + "; Hours length: " + hoursLength + "; Location: " + location + "; Available: " + available + "; "; 
    }
}


