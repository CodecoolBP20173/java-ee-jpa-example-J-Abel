package com.codecool.jpaexample.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity(name = "Class")
public class Klass {
    private CCLocation miskolc = null;
    private CCLocation budapest = null;
    private CCLocation krakow = null;
    private String name;
    private Set<Student> students = new HashSet<>();

    public Klass(CCLocation miskolc, CCLocation budapest, CCLocation krakow) {
        this.miskolc = miskolc;
        this.budapest = budapest;
        this.krakow = krakow;
    }

    public Klass(String name) {
        this.name = name;
    }

    public Klass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany
    public Set<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
