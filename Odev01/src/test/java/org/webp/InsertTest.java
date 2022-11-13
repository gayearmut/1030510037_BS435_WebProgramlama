package org.webp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsertTest {

    private EntityManagerFactory factory;
    private EntityManager em;

    @BeforeEach
    public void init() {
        //her bir test calismadan once BeforeEach calistirilir
        factory = Persistence.createEntityManagerFactory("Hibernate");
        em = factory.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        //her bir test calistiktan sonra BeforeEach calistirilir

        em.close();
        factory.close();
    }

    private boolean persistInATransaction(Object obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.persist(obj);
            tx.commit();
        } catch (Exception e) {
            System.out.println("FAILED TRANSACTION: " + e.toString());
            tx.rollback();
            return false;
        }

        return true;
    }




  

    @Test
    public void insertStudent() {

        Student student = new Student();
        student.setId(3L);

       // boolean persisted = persistInATransaction(student);
       // assertTrue(student);
    }

    @Test
    public void insertDepartment() {

        Department department = new Department();
        department.setId(4L);

       // boolean persisted = persistInATransaction(department);
       // assertTrue(persisted);
    }

    @Test
    public void insertCourse() {

        Course course = new Course();
        course.setId(5L);

       // boolean persisted = persistInATransaction(course);
        //assertTrue(persisted);
    }

}
