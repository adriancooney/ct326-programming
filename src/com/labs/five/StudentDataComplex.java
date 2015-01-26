package com.labs.five;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Adrian Cooney (12394581)
 * 21/10/14 com.labs.five
 */
public class StudentDataComplex extends StudentData {
    public StudentDataComplex(String name, String courseCode, String ID) {
        super(name, courseCode, ID);
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
        output.writeObject(this.name);
        output.writeObject(this.courseCode);
        output.writeObject(this.ID);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        this.name = (String) input.readObject();
        this.courseCode = (String) input.readObject();
        this.ID = (String) input.readObject();
    }
}
