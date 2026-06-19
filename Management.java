import java.util.Scanner;
import java.util.ArrayList;
public class Management {

    static class Student{
        String name;
        int roll;
        int id;
        int[] grades;

        Student(String name,int roll,int id,int[] grades){
            this.name=name;
            this.roll=roll;
            this.id=id;
            this.grades=grades;
        }

        //find average
    double getAverage(){
        int sum=0;

        for(int marks:grades){
            sum+=marks;
        }
        return (double)sum/grades.length;
    }
    //highest get 
      int getHighest(){
        int highest=grades[0];
       for(int mark:grades){
            if(mark>highest){
                highest=mark;
            }
        }
        return highest;
      }
      //find lowest marks
      int getLowest(){
        int lowest=grades[0];
        for(int mark:grades){
            if(mark<lowest){
                lowest=mark;
            }
        }
        return lowest;
      }
      String getGrade() {
    double avg = getAverage();

    if (avg >= 90)
        return "A";
    else if (avg >= 80)
        return "B";
    else if (avg >= 70)
        return "C";
    else if (avg >= 60)
        return "D";
    else
        return "F";
}

   void displayStudent(){
    System.out.println("Student name is:"+name);
    System.out.println("Student roll is:"+roll);
    System.out.println("Student id is:"+id);

    System.out.println("marks");

    for(int mark:grades){
    System.out.println("Student marks:"+mark);
   }

   System.out.println("the average marks is:"+getAverage());
   System.out.println("the highest marks is:"+getHighest());
    System.out.println("the lowest marks is:"+getLowest());
     System.out.println("grades get is:"+getGrade());

   }
    }

    //searching student 
   public static Student searchStudent(ArrayList<Student> students, int id) {

        for (Student s : students) {
            if (s.id == id) {
                return s;
            }
        }
        return null;
    }
    public static void main(String[]args){
        Scanner Sc=new Scanner(System.in);

     ArrayList<Student> students = new ArrayList<>();

     int choice;
        do{
            System.out.println("Student Management System");
            System.out.println("1.) Add students ");
            System.out.println("2.) View all students ");
            System.out.println("3.) Search students ");
            System.out.println("4.)Update Student ");
            System.out.println("5.) Delete students ");
            System.out.println("6.) Class Situation ");
            System.out.println("7.) Exit ");

            System.out.print("Enter Choice : ");
            choice = Sc.nextInt();

         switch (choice) {

   case 1://add students details
      System.out.println("enter student id: ");
      int id=Sc.nextInt();
      Sc.nextLine();
 
      System.out.println("enter student name: ");
          String name=Sc.nextLine();

     System.out.println("enter student roll: ");
      int roll=Sc.nextInt();
      Sc.nextLine();

      System.out.println("enter Number of subjects: ");
        int subjects = Sc.nextInt();

        int marks[]=new int[subjects];

         for (int i = 0; i < subjects; i++) {
    System.out.print("Enter Marks of Subject " + (i + 1) + " : ");
        marks[i] = Sc.nextInt();
    }
     students.add(new Student(name,roll,id,marks));

    System.out.println("Student Add Successfully!");
    break;

  case 2://display students 
        if (students.isEmpty()) {
         System.out.println("No Students Found!");
           } else {
            for (Student s : students) {
            s.displayStudent();
        }
        }
          break;

          //search student
    case 3:
        System.out.println("Search student by id");
        int searchId=Sc.nextInt();

        Student found=searchStudent(students,searchId);

        if(found!=null){
           found.displayStudent();
        }else{
            System.out.println("student not found");
        }
        break;
    case 4:
         System.out.print("Enter Student ID : ");
         int updateId = Sc.nextInt();

         Student updateStudent=searchStudent(students,updateId);
         if(updateStudent!=null){
    for(int i=0;i<updateStudent.grades.length;i++){
    System.out.print("Enter New Marks for Subject "+ (i + 1) + " : ");

    updateStudent.grades[i] = Sc.nextInt();
     }

     System.out.println("Marks Updated Successfully!");
        } else {
         System.out.println("Student Not Found!");
         }

         break;

         //delete student
case 5:
     System.out.print("Enter Student ID : ");
        int deleteId = Sc.nextInt();

    Student deleteStudent=searchStudent(students,deleteId);
    
    if(deleteStudent!=null){
        students.remove(deleteStudent);

    System.out.println("Student Deleted Successfully!");
   } else {
     System.out.println("Student Not Found!");
    }
      break;

    //class report
case 6:
    if(students.isEmpty()){
        System.out.println("no data found");
        break;
    }
    double totalAverage=0;
    Student topper=students.get(0);
     for (Student s : students) {

     totalAverage += s.getAverage();

     if (s.getAverage() > topper.getAverage()) {
        topper = s;
     }
       }
    System.out.println("class report");
    System.out.println("class average:"+totalAverage/students.size());

    System.out.println("name of topper:"+topper.name);

    System.out.println("average of topper:"+topper.getAverage());

    break;

    //exit program
    case 7:
        System.out.println("exit the program");
        break;

        default:

         System.out.println("Invalid Choice!");
        }

     }
         while (choice != 7);

        Sc.close();
    }
}



    

