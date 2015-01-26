package com.labs.five;

import java.io.Serializable;

/**
 * Adrian Cooney (12394581)
 * 21/10/14 com.labs.five
 */
public class StudentData implements Serializable {
    public String name;
    public String courseCode;
    public String ID;

    public StudentData(String name, String courseCode, String ID) {
        this.name = name;
        this.courseCode = courseCode;
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "StudentData{" +
                "name='" + name + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }
}
