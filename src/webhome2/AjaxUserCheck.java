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


@WebServlet("/AjaxUserCheck")
public class AjaxUserCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AjaxUserCheck() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String name = request.getParameter("name");
			
			Connection c = null;
	        try
	        {
	        		String url = "jdbc:mysql://localhost/cs3220";
		            String user = "root";
		            String pass = "123456";
	            
	            String sqlQuery = "select * from users where name='"+name+"'";
	          
		        c =  DriverManager.getConnection( url, user, pass );
	            Statement stmt = c.createStatement();
	            ResultSet rs = stmt.executeQuery( sqlQuery );
	            
	          if(rs.first())
	          {
	        	  response.setContentType( "text/plain" );
	              response.getWriter().print( false );
	        	  
	          }
	          else
	          {
	        	  response.setContentType( "text/plain" );
	              response.getWriter().print( true ); 
	        	  
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
		doGet( request, response );
	}

}
