package com.IncuDO.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.IncuDO.appClasses.Courses;
import com.IncuDO.controller.Controller;
import com.IncuDO.appClasses.Reservations;
import com.IncuDO.appClasses.Users;

public class Service {

    public static void printMenu(){
        System.out.println("1: Show all courses;\n" + "----------------------------------------------------\n" 
                            + "2: Book an existing course;\n" + "----------------------------------------------------\n"
                            + "3: cancel the reservation;\n " + "----------------------------------------------------\n"
                            + "4: Add a new User;\n "+ "----------------------------------------------------\n"
                            + "5: Export a file with the available courses;\n " + "----------------------------------------------------\n"
                            + "0: exit App.\n");
    }

    public static void executeProgram() throws IOException{

        System.out.println("\n");  
        
        ArrayList<Courses> courses = Courses.loadCourse("src\\main\\resources\\corsi.csv");
         
        String usersPathName = "src\\main\\resources\\utenti.csv";
        ArrayList<Users> users = Users.loadUser(usersPathName);        

        ArrayList<Reservations> reservations = Reservations.loadReservation("src\\main\\resources\\prenotazioni.csv");
        
        System.out.println("            IncuDO courses reservations\n----------------------------------------------------");       

        boolean endProgram = true;
        Service.printMenu();

        do{  

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();                     
            
            switch(choice) {

                case 1:
                    System.out.println("\nCourses List:");

                    System.out.println("ID ; Name; Description; Date; Hours Length; Available;\n"); 

                    for(Courses item : courses) {
                        System.out.println(item + "\n");                        
                    } 

                    System.out.println("------------------------------------------------------------------------------------------"); 

                    Service.printMenu(); 
                    break;

                case 2:
                    System.out.println("Enter your value,");
                    System.out.println("Enter the course ID,");                    
                    int searchCourseId = scanner.nextInt();

                    System.out.println("Enter your ID,");
                    int searchUserId = scanner.nextInt(); 

                    boolean invalidInput = false; 

                    if(searchCourseId < 0 || searchUserId <0) {
                        System.out.println("One of your input is invalid, please try again.");
                        invalidInput = true;
                    }

                    if(!invalidInput) {
                        if(Controller.createAndUpdateReservation(searchCourseId, searchUserId, courses, users)) {
                           System.out.println("\nSuccessfuly booked a course!"); 
                        }
                    }

                    Service.printMenu();
                    break;

                case 3:
                    System.out.println("Enter your value,");
                    System.out.println("Enter your ID,");
                    int userId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter your Name,");
                    String userName = scanner.nextLine();

                    System.out.println("Enter your Surname,");
                    String userSurname = scanner.nextLine();

                    System.out.println("Enter your Date of birth,");
                    String userDateOfBirth = scanner.nextLine();

                    System.out.println("Enter your Address,");
                    String userAddress = scanner.nextLine();

                    System.out.println("Enter your Document ID.");
                    String userDocumentId = scanner.nextLine();

                    Users newUser = new Users(userId, userName, userSurname, userDateOfBirth, userAddress, userDocumentId);                                        
                    Controller.deleteReservation(users, reservations, courses,newUser);

                    Service.printMenu();
                    break;

                case 4:                    
                    System.out.println("Enter your value,\n");
                    System.out.println("The max user ID is: " + Controller.findMaxId(usersPathName));
                    System.out.println("\nPlease insert \"" + (Controller.findMaxId(usersPathName) + 1) + "\" as your ID,");
                    System.out.println("Enter your ID,");
                    userId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter your Name,");
                    userName = scanner.nextLine();

                    System.out.println("Enter your Surname,");
                    userSurname = scanner.nextLine();

                    System.out.println("Enter your Date of birth,");
                    userDateOfBirth = scanner.nextLine();

                    System.out.println("Enter your Address,");
                    userAddress = scanner.nextLine();

                    System.out.println("Enter your Document ID.");
                    userDocumentId = scanner.nextLine();

                    newUser = new Users(userId, userName, userSurname, userDateOfBirth, userAddress, userDocumentId);
                    users.add(newUser);

                    boolean userExists = false;
                    if(newUser.getId() != (Controller.findMaxId(usersPathName) + 1) ) {                        
                        for(Users user : users) {                        
                            if(userId == user.getId()) { 
                                userExists = true;                            
                            }
                        }

                        if(!userExists) {
                            Controller.writeUserIntoFile(newUser);
                        } else {
                            System.out.println("\nThis User already exists!");
                        }    
                    } else {
                        System.out.println("You entered a wrong ID, please retry.");
                    }    

                    System.out.println("------------------------------------------------------------------------------------------");
                    Service.printMenu();
                    break;

                case 5:
                    Controller.exportCoursesFile(courses);
                    System.out.println("Users has been exported successfuly!");

                    Service.printMenu();
                    break;

                default:
                    System.out.println("Not valid input! try with the ones listed!");
                    Service.printMenu();
                    break;

                case 0: 
                    System.out.println("\nClosing Program, Goodbye!");

                    scanner.close();
                    endProgram = false;                 
            } 

        } while(endProgram);
    }

}
