package org.webp;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DepartmentEjb {

    @PersistenceContext
    private EntityManager em;

    public Long createDepartment(String name) {

        Department department = new Department();
        department.setName(name);

        em.persist(department);
        return department.getId();
    }

    public Long createUnit(long parentId, String name) {

        Department department = em.find(Department.class,parentId);
        if(department == null){
            throw new IllegalArgumentException("Department not found with "+parentId+" id does not exist");
        }
        Course course = new Course();
        course.setName(name);
        course.setParent(department);
        em.persist(course);

        department.getCourses().add(course);

        return course.getId();
    }

    public List<Department> getAllDepartments(boolean withCourse) {

        TypedQuery<Department> query = em.createQuery("select d from Department d", Department.class);
        List<Department> departments = query.getResultList();

        if(withCourse) {
            departments.forEach(department -> department.getCourses().size());
        }

        return departments;
    }

    public Course getCourse(long id) {

        return em.find(Course.class, id);
    }

    public Department getDepartment(long id,boolean withCourse) {

        Department department = em.find(Department.class, id);
        if (withCourse && department != null) {
            department.getCourses().size();
        }

        return department;
    }

}