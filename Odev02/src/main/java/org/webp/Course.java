package org.webp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max=128)
    private String name;



    @NotBlank
    private Long courseCode;


    @ManyToOne
    @NotNull
    private Department parent;

    public Course(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(Long courseCode) {
        this.courseCode = courseCode;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }
}