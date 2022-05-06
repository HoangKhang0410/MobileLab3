package com.example.lab3;

public class Student {
    private int id;
    private String name;
    private String className;

    public Student(int id, String name, String className){
        this.id = id;
        this.name = name;
        this.className = className;
    }
    public Student(String name, String className){
        this.name = name;
        this.className = className;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public String getClassName(){
        return this.className;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setClassName(String className){
        this.className = className;
    }
    @Override
    public String toString() {
        return "Tên: " + name + ", Lớp: " + className ;
    }
}
