package org.webp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CourseEjb {

    @PersistenceContext
    private EntityManager em;

    public Course getCourse(long id) {

        return em.find(Course.class, id);
    }

}