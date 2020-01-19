<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Student Tracker App</title>
        <link type="text/css" rel="stylesheet" href="style.css">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h2>FooBar</h2>
            </div>
        </div>
        <div id="container">
            <div id="content">
                <input type="button" value="Add Student" 
                       onclick="window.location.href='add-student-form.jsp'; return false" 
                       class="add-student-button">
                <table>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email ID</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach var="temp" items="${Student_list}">
                        <c:url var="tempLink" value="StudentControllerServlet">
                            <c:param name="command" value="LOAD"/>
                            <c:param name="studentID" value="${temp.id}"/>
                        </c:url>
                        <c:url var="deleteLink" value="StudentControllerServlet">
                            <c:param name="command" value="DELETE"/>
                            <c:param name="studentID" value="${temp.id}"/>
                        </c:url>
                        <tr>
                            <td>${temp.firstname}</td>
                            <td>${temp.lastname}</td>
                            <td>${temp.email}</td>
                            <td><a href="${tempLink}">Update</a>
                            | <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>     
</html>