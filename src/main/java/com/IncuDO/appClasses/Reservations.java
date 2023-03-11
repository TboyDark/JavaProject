package com.IncuDO.appClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Reservations {
    private int id, courseId, userId;    
    private String beginDate, endDate;
    
    public Reservations( int id, int courseId, int userId, String beginDate, String endDate) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public void setCourseId( int courseId ) {
        this.courseId = courseId;
    }

    public void setUserId( int userId ) {
        this.userId = userId;
    }

    public void setBeginDate( String beginDate ) {
        this.beginDate = beginDate;
    }

    public void setEndDate( String endDate ) {
        this.endDate = endDate;
    }
    
    public int getId() {
        return id;
    }
    public int getCourseId() {
        return courseId;
    }
    public int getUserId() {
        return userId;
    }
    public String getBeginDate() {
        return beginDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public static ArrayList<Reservations> loadReservation(String fileName) throws IOException{
        BufferedReader reader = null;
        ArrayList<Reservations> reservations = new ArrayList<>();
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
                int courseId = Integer.parseInt(data[1]);
                int userId = Integer.parseInt(data[2]);
                String beginDate = data[3];
                String endDate = data[4];

                Reservations reservation = new Reservations(id, courseId, userId, beginDate, endDate);
                reservations.add(reservation);                                
            }
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }finally{
            reader.close();
        }        
        return reservations;
    }
    
    public String toString() {
        return "ID: " + id + "; Course ID: " + courseId + "; User ID: " + userId + "; Begin date: " + beginDate + "; End date: " + endDate + "; ";
    }    
}
