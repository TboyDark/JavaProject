package com.IncuDO.appClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Users {

    private int id;
    private String name, surname, dateOfBirth, address, documentId;

    @Override
    public boolean equals(Object object) {
        Users user = (Users) object;
        if(id != user.id) {return false;}
        if(!user.getName().equalsIgnoreCase(this.getName())) {return false;}
        if(!user.getSurname().equalsIgnoreCase(this.getSurname())) {return false;}
        if(!user.getDateOfBirth().equalsIgnoreCase(this.getDateOfBirth())) {return false;}
        if(!user.getAddress().equalsIgnoreCase(this.getAddress())) {return false;}
        if(!user.getDocumentId().equalsIgnoreCase(this.getDocumentId())) {return false;}        
        return true;
    }

    public Users( int id, String name, String surname, String dateOfBirth, String address, String documentId ) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.documentId = documentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }    
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getDocumentId() {
        return documentId;
    }  
    
    public static ArrayList<Users> loadUser(String fileName) throws IOException {
        BufferedReader reader = null;
        ArrayList<Users> users = new ArrayList<>();
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
                String surname = data[2];
                String dateOfBirth = data[3];
                String address = data[4];
                String documentId = data[5];
                
                Users user = new Users(id,name,surname,dateOfBirth,address,documentId);
                users.add(user);                                
            }
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            reader.close();
        }        
        return users;
    }

    public String toString() {
        return "ID: " + id + "; Name: " + name + "; Surname: " + surname + "; Date of birth: " + dateOfBirth + "; Address: " + address + "; Document ID: " + documentId + "; ";
    }
}
