package org.webp;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class StudentEjb {

    @PersistenceContext
    private EntityManager em;

    public long createEmployee(long courseId,String name,int age){

        Course course = em.find(Course.class,courseId);

        if(course == null){
            throw new IllegalArgumentException("Course not found. Course "+courseId+" does not exist");
        }

        Student student = new Student();
        student.setCourse(course);
        student.setName(name);
        student.setAge(23);

        em.persist(student);

        return student.getId();
    }

    public List<Student> getStudents(){

        TypedQuery<Student> query = em.createQuery("select e from Student e", Student.class);

        return query.getResultList();
    }

    public Student getStudent(long id){

        return em.find(Student.class, id);

    }





}