package webhome2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjaxFileShare
 */
@WebServlet("/AjaxFileShare")
public class AjaxFileShare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AjaxFileShare() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection c = null;
		 int userId=0;
		 
		 String shareUser = request.getParameter("userName");
		 String fileId = request.getParameter("id");
	        try
	        {
	        	String url = "jdbc:mysql://localhost/cs3220";
	            String username = "root";
	            String password = "123456";
	            
	            String sqlQuery = "select id from users where name='"+shareUser+"'";
	            c = DriverManager.getConnection( url, username, password );
	            
	           
	            Statement stmt = c.createStatement();
	            ResultSet rs = stmt.executeQuery( sqlQuery );
	            
	            while(rs.next())
	            {
	            	userId = rs.getInt(1);
	            	
	            }
	            
	            if(userId!=0)
	            {
	            	String sqlQuery2 = "insert into fileUser values('"+fileId+"','"+userId+"')";
	            	
	            	stmt.executeUpdate(sqlQuery2);
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
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
