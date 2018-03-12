package webhome2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public SignUp() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errorMsg="";
		request.getSession().setAttribute("SignUpError", errorMsg);
		request.getRequestDispatcher("/WEB-INF/SignupDisplay.jsp").forward(request, response);
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<UserList> userList = new ArrayList<UserList>();
		
		String username =  request.getParameter("name");
		String password =  request.getParameter("password");
			
		if(password==null || password.equals("")|| password.equals(" "))
		{
			
			response.sendRedirect("SignUp");
			return;
			
		}
		
		Connection c = null;
        try
        {
        		String url = "jdbc:mysql://localhost/cs3220";
	            String user = "root";
	            String pass = "123456";
            
            String sqlQuery = "select * from users";
          
	            c =  DriverManager.getConnection( url, user, pass );
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( sqlQuery );
            
            while(rs.next())
            {
            	userList.add(new UserList(rs.getInt("id"),rs.getString("name"),rs.getString("password")));
            	
            }
           
            c.close();
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }
		
		
		
		
		for(UserList u: userList)
		{
			
			if(u.getUsername().equals(username))
			{
				
				response.sendRedirect("SignUp");
				return;
				
			}
			
		}
		
		c = null;
        try
        {
        		String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu40";
	            String user = "cs3220stu40";
	            String pass = "#F#sOibc";
            
            String sqlQuery = "insert into users(name,password) values('"+username+"','"+password+"')";
          
	        c =  DriverManager.getConnection( url, user, pass );
            Statement stmt = c.createStatement();
            
            stmt.executeUpdate(sqlQuery);            
           
           
            c.close();
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
        }
		
		
		request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
	}

}
