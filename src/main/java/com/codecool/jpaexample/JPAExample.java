package com.codecool.jpaexample;

import com.codecool.jpaexample.model.Address;
import com.codecool.jpaexample.model.CCLocation;
import com.codecool.jpaexample.model.Klass;
import com.codecool.jpaexample.model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JPAExample {

    public static void populateDb(EntityManager em) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate1 = Calendar.getInstance().getTime();
        Date birthDate2 = Calendar.getInstance().getTime();
        try {
            birthDate1 = sdf.parse("1997-07-21");
            birthDate2 = sdf.parse("1993-12-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Klass classBp2 = new Klass("Budapest 2016-2", CCLocation.BUDAPEST);
        Address address = new Address("Hungary", "1234", "Budapest", "Macskakő út 5.");
        Student student = new Student("Ödön", "odon@tokodon.hu", birthDate1, address, Arrays.asList("1111", "222222"));

        em.persist(student);
        address.setStudent(student);

        em.persist(classBp2);
        classBp2.addStudent(student);
        student.setKlass(classBp2);

        em.persist(student);
        em.persist(address);

        transaction.commit();
        System.out.println("Ödön saved.");

        Address address2 = new Address("Hungary", "6789", "Budapest", "Harap u. 3.");
        Student student2 = new Student("Aladár", "ktyfl@gmail.com", birthDate2, address2, Arrays.asList("1111", "222222"));

        em.persist(student2);
        address2.setStudent(student2);
        em.persist(address2);

        classBp2.addStudent(student2);
        em.persist(classBp2);
        student2.setKlass(classBp2);

        em.persist(student2);

        transaction.begin();
        transaction.commit();
        System.out.println("Aladár saved.");
    }

    public static void main(String[] args) {
        Map<String, Object> configOverrides = usingEnvironVars();

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpaexamplePU", configOverrides);
        EntityManager em = emf.createEntityManager();

        populateDb(em);

        Student foundStudent1 = em.find(Student.class, 1L);
        System.out.println("--Found student #1");
        System.out.println("----name----" + foundStudent1.getName());
        System.out.println("----address of student----" + foundStudent1.getAddress());

        Student foundStudent2 = em.find(Student.class, 2L);
        System.out.println("--Found student #2");
        System.out.println("----name----" + foundStudent2.getName());
        System.out.println("----address of student----" + foundStudent2.getAddress());

        Address foundAddress1 = em.find(Address.class, 1L);
        System.out.println("--Found address #1");
        System.out.println("----address----" + foundAddress1.getAddr());

        Address foundAddress2 = em.find(Address.class, 2L);
        System.out.println("--Found address #2");
        System.out.println("----address----" + foundAddress2.getAddr());

        em.close();
        emf.close();

    }

    /**
     * using environment variables for persistence.xml !
     */
    private static Map<String, Object> usingEnvironVars() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        for (String envName : env.keySet()) {
            if (envName.contains("USER")) {
                configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
            }
            if (envName.contains("PASSWORD")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            }
        }
        return configOverrides;
    }
}
