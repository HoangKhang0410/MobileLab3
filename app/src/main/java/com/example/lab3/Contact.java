package com.example.lab3;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;

    public Contact(int id, String name, String _phone_number){
        this.id = id;
        this.name = name;
        this.phoneNumber = _phone_number;
    }
    public Contact(String name, String _phone_number){
        this.name = name;
        this.phoneNumber = _phone_number;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString() {
        return "Tên: " + name + ", SĐT: " + phoneNumber ;
    }
}
