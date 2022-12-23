package org.webp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private boolean persistInATransaction(Object... obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for(Object o:obj) {
                em.persist(o);
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println("FAILED TRANSACTION: " + e.toString());
            tx.rollback();
            return false;
        }

        return true;
    }
    @Test
    public void testStudent(){

        Student student = new Student();
        student.setName("Gaye");
        student.setGender("Kadın");
        student.setAge(23);


        boolean persisted = persistInATransaction(student);

        assertFalse(persisted);
    }

    @Test
    public void testStudentWithCourse(){

        Department department = new Department();
        department.setName("Bilgisayar");

        Course course = new Course();
        course.setName("Web Programlama");

        department.getCourses().add(course);
        course.setParent(department);

        Student student = new Student();
        student.setName("Gaye");
        student.setGender("Kadın");
        student.setAge(23);

        student.setCourse(course);

        assertFalse(persistInATransaction(department,course,student));
    }
    @Test
    public void testTooLongName(){

        String name = new String(new char[150]);

        Department department = new Department();
        department.setName(name);

        assertFalse(persistInATransaction(department));

        department.setId(null);
        department.setName("Elektrik");

        assertTrue(persistInATransaction(department));
    }
    @Test
    public void testUniqueName(){

        String name = "Elektrik";

        Department department = new Department();
        department.setName(name);

        assertTrue(persistInATransaction(department));

        Department anotherDepartment = new Department();
        anotherDepartment.setName(name);

        assertFalse(persistInATransaction(anotherDepartment));
    }


    private Course addCourse(Department department, String courseName){
        Course course = new Course();
        course.setName(courseName);

        department.getCourses().add(course);
        course.setParent(department);

        return course;
    }

    private Student createStudent(Course course, String name){

        Student student = new Student();
        student.setName("Gaye");
        student.setGender("Kadın");
        student.setAge(23);

        student.setCourse(course);

        return student;
    }

    @Test
    public void testQueries(){

        Department bilgisayarManagement = new Department();
        bilgisayarManagement.setName("Bilgisayar");

        Course micro = addCourse(bilgisayarManagement, "micro");
        Course yazilim = addCourse(bilgisayarManagement, "yazilim");
        Course lineer = addCourse(bilgisayarManagement, "lineer");


        assertTrue(persistInATransaction(bilgisayarManagement, micro, yazilim, lineer));


        Student a = createStudent(micro,"ali");
        Student b = createStudent(micro,"mehmet");
        Student c = createStudent(yazilim,"pelin");
        Student d = createStudent(yazilim,"zeynep");

        assertTrue(persistInATransaction(a,b,c,d));


        TypedQuery<Student> queryMicro = em.createQuery(
                "select e from Student e where e.course.name='micro'",Student.class);
        List<Student> studentMicro = queryMicro.getResultList();
        assertEquals(2, studentMicro.size());
        assertTrue(studentMicro.stream().anyMatch(e -> e.getName().equals("ali")));
        assertTrue(studentMicro.stream().anyMatch(e -> e.getName().equals("mehmet")));

        TypedQuery<Student> queryBilgisayarManagement = em.createQuery(
                "select e from Student e where e.course.parent.name='Bilgisayar Management'",Student.class);
        List<Student> all = queryBilgisayarManagement.getResultList();
        assertEquals(4, all.size());
    }


}
