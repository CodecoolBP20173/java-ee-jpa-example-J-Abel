package com.codecool.jpaexample.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Class")
public class Klass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private CCLocation location = null;

    private String name;

    @OneToMany(
            mappedBy = "klass",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Student> students = new HashSet<>();

    public Klass(String name, CCLocation location) {
        this.name = name;
        this.location = location;
    }

    public Klass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
//        student.setKlass(this);
        students.add(student);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CCLocation getLocation() {
        return location;
    }

    public void setLocation(CCLocation location) {
        this.location = location;
    }
}
