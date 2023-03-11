package com.IncuDO.controller;

import com.IncuDO.appClasses.Courses;
import com.IncuDO.appClasses.Reservations;
import com.IncuDO.appClasses.Users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller {
    public static void writeUserIntoFile(Users user) throws IOException{        
        BufferedWriter writer = null;
        try {
            FileWriter fileWriter = new FileWriter("src\\main\\resources\\utenti.csv", true);
            writer = new BufferedWriter(fileWriter);
            writer.newLine(); 
            writer.append(user.getId() + ";" + user.getName() + ";" + user.getSurname() + ";" + user.getDateOfBirth() + ";" + user.getAddress() + ";" + user.getDocumentId());            
            writer.close();    
        } catch(FileNotFoundException e) {            
            System.err.println("File not found: " + e.getMessage());
            return;
        } catch(IOException e) {
            System.err.println("IO Exception found: " + e.getMessage());
            return;
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch(IOException e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
        System.out.println("User has been written in the CSV File successfuly!");
    }

    public static String calculateCourseEndDate(int numDays, String date) {                
        LocalDate currentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = currentDate.plusDays(numDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String resultString = endDate.format(formatter);
        return resultString;
    }

    public static void writeReservation(Reservations reservation) throws IOException{
        BufferedWriter writer = null;        
        try {
            FileWriter fileWriter = new FileWriter("src\\main\\resources\\utenti.csv", true);
            writer = new BufferedWriter(fileWriter); 
            writer.newLine();            
            writer.append(reservation.getId() + ";" + reservation.getCourseId() + ";" + reservation.getUserId() + ";" + reservation.getBeginDate() + ";" + reservation.getEndDate());            
            writer.close();    
        } catch (FileNotFoundException e) {            
            System.err.println("File not found: " + e.getMessage());
            return;
        } catch(IOException e) {
            System.err.println("IO Exception found: " + e.getMessage());
            return;
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch(IOException e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
    }

    public static void updateCoursesAvailability(int courseId, String available, ArrayList<Courses> courses) throws IOException{
        
        for(Courses course : courses) {
            if(course.getId() == courseId) {
                if(course.getAvailable().equalsIgnoreCase(available)){
                    System.out.println("The availability is already set to " + available);                    
                }else {
                    course.setAvailable(available);
                }                
                break;
            }
        }
    
        BufferedWriter writer = null;
        try{
            FileWriter fileWriter = new FileWriter("src\\main\\resources\\utenti.csv");
            writer = new BufferedWriter(fileWriter);
            writer.append("ID;Nome;Descrizione;Data;Durata (ore);Luogo;Disponibile");
            writer.newLine();
            for(Courses course : courses) {                
                writer.append(course.getId() + ";" + course.getName() + ";" + course.getDescription() + ";" + course.getDate() + ";" + course.getHoursLength() + ";" + course.getLocation() + ";" + course.getAvailable() + "\n");                
            }
        }catch(FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return;
        }catch(IOException e) {
            System.err.println("IO Exception found: " + e.getMessage());
            return;
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch(IOException e) {
                    System.err.println("Error closing writer: " + e.getMessage());
                }
            }
        }
    }

    public static boolean createAndUpdateReservation(int searchCourseId, int searchUserId, ArrayList<Courses> courses, ArrayList<Users> users) throws IOException{
        int reservationMaxId = Controller.findMaxId("src\\main\\resources\\prenotazioni.csv");
        int courseId = 0;
        String date = "";
        int days = 0;
        String available = "";
        boolean isFound = false;

        for(Courses item : courses) {
            if(searchCourseId == item.getId()) {
                courseId = item.getId();
                date = item.getDate();
                days = item.getHoursLength();
                if( item.getAvailable().equals("SI") ) {
                    available = "NO";
                    isFound = true;
                    break;
                } else {
                    System.out.println("\nThe Course is already Booked!");
                    return false;
                } 
            }
        }

        if(!isFound) {
            System.out.println("The Id you entered does not exist! Try looking the Course List by selecting \"1\" in the menu.");
            return false;
        }

        int userId = 0;
        boolean validUserId = false;
        for(Users item : users) {
            if(searchUserId == item.getId()) {
                validUserId = true;
                userId = item.getId();
                break;
            }
        }  

        if(!validUserId) {
            String endDate = Controller.calculateCourseEndDate(days, date);                  
            Reservations reservation = new Reservations(reservationMaxId + 1, courseId, userId, date, endDate);                  
            Controller.writeReservation(reservation);
            Controller.updateCoursesAvailability(searchCourseId, available, courses);
            return true;
        } else {
            System.out.println("Invalid user ID, please insert a valid user ID\n");
            System.out.println("Reminder of max users ID: " + Controller.findMaxId("src\\main\\resources\\utenti.csv"));
            return false;
        }              
        
    }
    
    public static void deleteReservation(ArrayList<Users> users, ArrayList<Reservations> reservations, ArrayList<Courses> courses, Users newUser) throws IOException {        
        boolean userExists = false;        
        int reservationCourseId = 0;

        for(Users userItem : users) {                        
            if(newUser.getId() == userItem.getId()) {                
                if(newUser.equals(userItem)) {
                    userExists = true;
                    break;     
                } else {
                    System.out.println("\nYou entered invalid value/s, please retry.");
                }                             
            }
        }  

        if(!userExists) {
            System.out.println("This user doesn't exist! Try with an existing user.");            
        }else {
            for(Reservations reservation : reservations) {                
                if(newUser.getId() == reservation.getUserId()) {                    
                    reservationCourseId = reservation.getCourseId();                            
                    reservationCourseId = reservation.getCourseId();
                    reservations.remove(reservation);
                    break;                            
                }
            }
            try(BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\prenotazioni.csv"))) {
                writer.write("ID;ID Attività;ID Utente;Data inizio;Data fine");
                writer.newLine();

                for(Reservations reservation : reservations) {                    
                    writer.write(reservation.getId() + ";" + reservation.getCourseId() + ";" + reservation.getUserId() + ";" + reservation.getBeginDate() + ";" + reservation.getEndDate() + "\n");
                }                
            } catch(FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
                return;
            } catch(IOException e) {
                System.err.println("IO Exception found: " + e.getMessage());
                return;
            }
            Controller.updateCoursesAvailability(reservationCourseId, "SI", courses);
            System.out.println("Successfuly deleted your reservation!");
        }                 
    }

    public static void exportCoursesFile(ArrayList<Courses> courses) throws IOException {         
        LocalDate date = LocalDate.now();
        String filename = "prenotazioni_" + date.getDayOfMonth() + "_" + date.getMonthValue() + "_" + date.getYear() + ".csv";
        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        File csvFile = new File(desktopPath + filename);

        try(FileWriter fileWriter = new FileWriter(csvFile); BufferedWriter writer = new BufferedWriter(fileWriter);) {                        
            writer.write("ID;Nome;Descrizione;Data;Durata (ore);Luogo;Disponibile\n");
            for(Courses course : courses) {
                if(course.getAvailable().equalsIgnoreCase("SI")) {
                    writer.write(course.getId() + ";" + course.getName() + ";" + course.getDescription() + ";" + course.getDate() + ";" + course.getHoursLength() + ";" + course.getLocation() + ";" + course.getAvailable() + "\n");
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return;
        }
    }
    
    public static int findMaxId(String pathName) throws IOException {
        int maxId = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(pathName));
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null) {
                String[] data = line.split(";");                
                int id = Integer.parseInt(data[0]);
                if(id > maxId) {
                    maxId = id;
                }
            }
        } catch(FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch(IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    System.err.println("Error closing reader: " + e.getMessage());
                }
            }
        }
        return maxId;
    } 
}
