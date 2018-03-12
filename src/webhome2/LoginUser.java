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


@WebServlet("/LoginUser")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginUser() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		
		boolean correct = false;
		List<UserList> userList = new ArrayList<UserList>();
		
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
		
		for(UserList u:userList)
		{
				if(u.getUsername().equals(username) && u.getPassword().equals(password))
				{
					correct = true;
					break;
					
				}			
			
		}
		
		if(!correct)
		{
			
			
			response.sendRedirect("LoginUser");
			return;
			
			
		}
		
		else
		{
			request.getSession().setAttribute("username", username);
			response.sendRedirect("FolderDisplay");
			
		}
	}

}
