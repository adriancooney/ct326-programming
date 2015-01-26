package com.labs.five;

import java.io.*;

/**
 * Adrian Cooney (12394581)
 * 21/10/14 com.labs.five
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Create the simple student objects (Part 1)
            StudentData[] students = {
                    new StudentData("Adrian Cooney", "3BCT", "12394581"),
                    new StudentData("Brian Louis", "3BCT", "15673021"),
                    new StudentData("Agent 11", "REDACTED", "UNKNOWN")
            };

            // Do the simple serialization/deserialization
            System.out.println("Doing simple serialization/deserialization without overriding (read|write)Object.");
            String filename = "students-simple.txt";
            simpleSerialization(students, filename);
            simpleDeserialization(filename);

            // Create the studentData objects with the overriden methods and write them (Part 2)
            StudentDataComplex[] studentsComplex = {
                    new StudentDataComplex("Joel Zimmerman", "MAU5", "80085"),
                    new StudentDataComplex("Gordon Ramsay", "F*CK", "NEHCTIK"),
                    new StudentDataComplex("Elon Musk", "GOD", "T3514")
            };

            System.out.println("Doing serialization/deserialization with overriding (read|write)Object.");
            filename = "students-complex.txt";
            simpleSerialization(studentsComplex, filename); // Serialize
            simpleDeserialization(filename); // Deserialize

            // And for my final trick, run the RandomAccessFile example (Part 3)
            randomAccessFileExample();

        } catch (IOException e) {
            System.out.println("IO Error.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void simpleSerialization(Object[] students, String filename) throws IOException {

        System.out.printf("Opening '%s' for writing student data.\n", filename);

        // Create the output streams
        FileOutputStream output = new FileOutputStream(filename);
        ObjectOutputStream objects = new ObjectOutputStream(output);

        // Loop over each student and write it out
        for(Object student : students) {
            System.out.printf("Writing %s to the file.\n", student);
            objects.writeObject(student);
        }

        System.out.println("Finished writing students. Closing file.");

        // Safely close the streams
        objects.flush();
        objects.close();
    }

    public static void simpleDeserialization(String filename) throws IOException, ClassNotFoundException {
        System.out.printf("Opening '%s' for reading student data.\n", filename);

        // Create the input streams
        FileInputStream input = new FileInputStream(filename);
        ObjectInputStream objects = new ObjectInputStream(input);
        StudentData student;

        // Print out all the students
        while(input.available() > 0 && (student = ((StudentData) objects.readObject())) != null) {
            System.out.println(student);
        }

        System.out.println("Students read.");
    }

    public static void randomAccessFileExample() throws IOException {
        RandomAccessFile file = new RandomAccessFile("randomly-accessed-file.txt", "rw");

        System.out.println("\nWriting to randomly accessed file..");

        // Seek to the end of the file
        file.seek(file.length());
        // Write to file the raw bytes
        file.write(("Hello world!").getBytes());
        // And close the stream
        file.close();
    }
}
