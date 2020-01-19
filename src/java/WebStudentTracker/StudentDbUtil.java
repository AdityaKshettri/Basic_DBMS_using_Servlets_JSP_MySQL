
package WebStudentTracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil 
{
    final String driver="org.apache.derby.jdbc.ClientDriver";
    final String DB_URL = "jdbc:derby://localhost:1527/web_student_tracker";
    final String USER = "root";
    final String PASS = "1234";
    public List<Student> getStudents()throws Exception
    {
        List<Student> students=new ArrayList<Student>();
        
        Connection myCon=null;
        Statement myStmt=null;
        ResultSet myRs=null;
        
        try
        {
            Class.forName(driver);
            myCon=DriverManager.getConnection(DB_URL,USER,PASS);
            String sql="select * from student";
            myStmt=myCon.createStatement();
            myRs=myStmt.executeQuery(sql);
            while(myRs.next())
            {
                int id=myRs.getInt("id");
                String firstname=myRs.getString("firstname");
                String lastname=myRs.getString("lastname");
                String email=myRs.getString("email");
                
                Student temp=new Student(id,firstname,lastname,email);
                students.add(temp);
            }
            return students;
        }
        finally
        {
            close(myCon,myStmt,myRs);
        }
    }

    private void close(Connection myCon, Statement myStmt, ResultSet myRs) 
    {
       try
       {
           if(myRs!=null)
               myRs.close();
           if(myStmt!=null)
               myStmt.close();
           if(myCon!=null)
               myCon.close();
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
    }

    void addStudent(Student theStudent)throws Exception
    {
        Connection myCon=null;
        PreparedStatement myStmt=null;
        try
        {
            Class.forName(driver);
            myCon=DriverManager.getConnection(DB_URL,USER,PASS);
            String sql= "insert into student "+"(id,firstname,lastname,email) "+"values(?,?,?,?)";
            myStmt=myCon.prepareStatement(sql);
            myStmt.setInt(1,5);
            myStmt.setString(2,theStudent.getFirstname());
            myStmt.setString(3,theStudent.getLastname());
            myStmt.setString(4,theStudent.getEmail());
            myStmt.execute();
        }
        finally
        {
            close(myCon,myStmt,null);
        }
    }

    Student getStudent(String theStudentID) throws Exception
    {
        Student theStudent=null;
        Connection myCon=null;
        PreparedStatement myStmt=null;
        ResultSet myRs=null;
        int studentID;
        try
        {
            studentID=Integer.parseInt(theStudentID);
            Class.forName(driver);
            myCon=DriverManager.getConnection(DB_URL,USER,PASS);
            String sql="select * from student where id=?";
            myStmt=myCon.prepareStatement(sql);
            myStmt.setInt(1,studentID);
            myRs=myStmt.executeQuery();
            if(myRs.next())
            {
                String firstname=myRs.getString("firstname");
                String lastname=myRs.getString("lastname");
                String email=myRs.getString("email");
                theStudent=new Student(studentID,firstname,lastname,email);
            }
            else
            {
                throw new Exception("Could not find : "+studentID);
            }
            return theStudent;
        }
        finally
        {
            close(myCon,myStmt,myRs);
        }
    }

    void updateStudent(Student theStudent) throws Exception
    {
        Connection myCon=null;
        PreparedStatement myStmt=null;
        try
        {
            Class.forName(driver);
            myCon=DriverManager.getConnection(DB_URL,USER,PASS);
            String sql="update student set firstname=?,lastname=?,email=? where id=?";
            myStmt=myCon.prepareStatement(sql);
            myStmt.setString(1,theStudent.getFirstname());
            myStmt.setString(2,theStudent.getLastname());
            myStmt.setString(3,theStudent.getEmail());
            myStmt.setInt(4,theStudent.getId());
            myStmt.execute();
        }
        finally
        {
            close(myCon,myStmt,null);
        }
    }

    void deleteStudent(String theStudentID) throws Exception
    {
        Connection myCon=null;
        PreparedStatement myStmt=null;
        try
        {
            int studentID=Integer.parseInt(theStudentID);
            Class.forName(driver);
            myCon=DriverManager.getConnection(DB_URL,USER,PASS);
            String sql="delete from student where id=?";
            myStmt=myCon.prepareStatement(sql);
            myStmt.setInt(1,studentID);
            myStmt.execute();
        }
        finally
        {
            close(myCon,myStmt,null);
        }
    }
}
