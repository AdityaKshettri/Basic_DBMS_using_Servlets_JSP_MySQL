package WebStudentTracker;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentControllerServlet extends HttpServlet 
{
    private final StudentDbUtil studentDbUtil=new StudentDbUtil();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String theCommand=request.getParameter("command");
            if(theCommand==null)
            {
                theCommand="LIST";
            }
            switch(theCommand)
            {
                case "LIST":
                    listStudents(request,response);
                    break;
                case "ADD":
                    addStudent(request,response);
                    break;
                case "LOAD":
                    loadStudent(request,response);
                    break;
                case "UPDATE":
                    updateStudent(request,response);
                    break;
                case "DELETE":
                    deleteStudent(request,response);
                    break;
                default:
                    listStudents(request,response);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<Student> students=studentDbUtil.getStudents();
        request.setAttribute("Student_list",students);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request,response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String firstname=request.getParameter("firstname");
        String lastname=request.getParameter("lastname");
        String email=request.getParameter("email");
        Student theStudent=new Student(firstname,lastname,email);
        studentDbUtil.addStudent(theStudent);
        listStudents(request,response);
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String studentID=request.getParameter("studentID");
        Student theStudent=studentDbUtil.getStudent(studentID);
        request.setAttribute("THE_STUDENT",theStudent);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request,response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String studentID=request.getParameter("studentID");
        int id=Integer.parseInt(studentID);
        String firstname=request.getParameter("firstname");
        String lastname=request.getParameter("lastname");
        String email=request.getParameter("email");
        Student theStudent=new Student(id,firstname,lastname,email);
        studentDbUtil.updateStudent(theStudent);
        listStudents(request,response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        String studentID=request.getParameter("studentID");
        studentDbUtil.deleteStudent(studentID);
        listStudents(request,response);
    }
}
