package com.assignment.conorspetitions;

import java.util.ArrayList;
import java.util.List;

public class Petition {
    private int id;
    private String title;
    private String description;
    private String sector;
    private List<String> signatures;
    private List<String> emails;

    public Petition(int id, String title, String description, String sector){
        this.id = id;
        this.title = title;
        this.description = description;
        this.sector = sector;
        this.signatures = new ArrayList<>();
        this.emails= new ArrayList<>();
    }

    public int getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getSector(){
        return sector;
    }

    public List<String> getSignatures(){
        return signatures;
    }

    public List<String> getEmails(){
        return emails;
    }

    public void sign(String name){
        this.signatures.add(name);
    }

}
