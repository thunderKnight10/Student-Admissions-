import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admission{

    private static class Student {
        String name;
        String rollId;
        int age;
        String dob;
        String department;
        double gpa;
        String admissionStatus;
        String fatherName;
        String fatherContact;
        String motherName;
        String motherContact;

        Student(String name, String rollId, int age, String dob,
                String department, double gpa, 
                String fatherName, String fatherContact,
                String motherName, String motherContact) {
            this.name = name;
            this.rollId = rollId;
            this.age = age;
            this.dob = dob;
            this.department = department;
            this.gpa = gpa;
            this.admissionStatus = "False";   // set here, not passed in
            this.fatherName = fatherName;
            this.fatherContact = fatherContact;
            this.motherName = motherName;
            this.motherContact = motherContact;
        }
    }


    private static class Node {
        Student data;
        Node next;
 
        Node(Student data) {
            this.data = data;
            this.next = null;
        }
    }
 
    private Node head;
 
    public Admission() {
        this.head = null;
    }
 
    public void addStudent(Student student){    // add all.
        Node newNode = new Node(student);
 
        if (head == null) {
            head = newNode;
            return;
        }
 
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
    

    public void changeAdmissionStatus(List<String> rollIds) {
        Node current = head;
        while (current != null) {
            if (rollIds.contains(current.data.rollId)) {
                current.data.admissionStatus = "True";
                System.out.println("Admission status updated for " + current.data.name
                        + " (Roll/ID: " + current.data.rollId + ")");
            }
            current = current.next;
        }
    }
    
    public void printAll(){   // Prints every student 
        Node current = head;
        int count = 1;
        while (current != null) {
            Student s = current.data;
            System.out.println("\n--- Student " + count + " ---");
            System.out.println("Name: " + s.name);
            System.out.println("Roll/ID: " + s.rollId);
            System.out.println("Age: " + s.age);
            System.out.println("DOB: " + s.dob);
            System.out.println("Department: " + s.department);
            System.out.println("GPA: " + s.gpa);
            System.out.println("Admission Status: " + s.admissionStatus);
            System.out.println("Father: " + s.fatherName + " (" + s.fatherContact + ")");
            System.out.println("Mother: " + s.motherName + " (" + s.motherContact + ")");
            current = current.next;
            count++;
        }
    }
 
    // Takes input from the user and returns a new Student object
    public static Student takeStudentInput(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
 
        System.out.print("Enter roll/ID: ");
        String rollId = sc.nextLine();
 
        System.out.print("Enter age: ");
        int age = Integer.parseInt(sc.nextLine());
 
        System.out.print("Enter date of birth (DD-MM-YYYY): ");
        String dob = sc.nextLine();
 
        System.out.print("Enter department applied for: ");
        String department = sc.nextLine();
 
        System.out.print("Enter GPA: ");
        double gpa = Double.parseDouble(sc.nextLine());
 
        System.out.print("Enter father's name: ");
        String fatherName = sc.nextLine();
 
        System.out.print("Enter father's contact: ");
        String fatherContact = sc.nextLine();
 
        System.out.print("Enter mother's name: ");
        String motherName = sc.nextLine();
 
        System.out.print("Enter mother's contact: ");
        String motherContact = sc.nextLine();
 
        return new Student( name,  rollId,  age, 
         dob,  department,  gpa,   // <-- here
         fatherName,  fatherContact,
         motherName,  motherContact) ;
    }

    public void removeUnadmitted() {
        // Remove matching nodes from the front first
        while (head != null && head.data.admissionStatus.equals("False")) {
            System.out.println("Removing " + head.data.name + " (Roll/ID: " + head.data.rollId + ")");
            head = head.next;
        }
 
        if (head == null) {
            return; // list is now empty
        }
 
        // Walk the rest of the list, unlinking any "False" nodes we find
        Node current = head;
        while (current.next != null) {
            if (current.next.data.admissionStatus.equals("False")) {
                System.out.println("Removing " + current.next.data.name
                        + " (Roll/ID: " + current.next.data.rollId + ")");
                current.next = current.next.next; // skip over the removed node
            } else {
                current = current.next;
            }
        }
    }
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Admission list = new Admission();
 
        System.out.print("How many students do you want to enter? ");
        int nums = Integer.parseInt(sc.nextLine());
 
        for (int i = 0; i < nums; i++) {
            System.out.println("\nEntering details for student " + (i + 1) + ":");
            Student s = takeStudentInput(sc);
            list.addStudent(s);
        }
 
        System.out.println("\n=== Before updating admission status ===");
        list.printAll();
 
        System.out.print("\nHow many students do you want to admit? ");
        int admitCount = Integer.parseInt(sc.nextLine());
 
        List<String> admittedRollIds = new ArrayList<>();
        for (int i = 0; i < admitCount; i++) {
            System.out.print("Enter roll/ID of admitted student " + (i + 1) + ": ");
            admittedRollIds.add(sc.nextLine());
        }
 
        list.changeAdmissionStatus(admittedRollIds);
        list.removeUnadmitted();
        System.out.println("\n=== After updating admission status ===");
        list.printAll();
 
        sc.close();
    }
}
 