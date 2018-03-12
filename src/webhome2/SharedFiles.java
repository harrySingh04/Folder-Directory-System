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


@WebServlet("/SharedFiles")
public class SharedFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public SharedFiles() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			int userId =Integer.parseInt( request.getParameter("userId"));
			int currentId =Integer.parseInt( request.getParameter("currentId"));
			Connection c = null;
			List<FileDisplay> fileSystem=new ArrayList<FileDisplay>();
			
			 try
			 {
				 String url = "jdbc:mysql://localhost/cs3220";
		            String user = "root";
		            String password = "123456";
	            
	            String sqlQuery = "select * from files f inner join fileUser fu on f.id = fu.file_Id where user_id="+userId;
	          
	            c =  DriverManager.getConnection( url, user, password );
	            Statement stmt = c.createStatement();
	            ResultSet rs = stmt.executeQuery( sqlQuery );
	            
	            while( rs.next() )
	                fileSystem.add( new FileDisplay( rs.getInt( "id" ),
	                    rs.getString( "name" ),rs.getString( "type" ),
	                    rs.getLong("size"), rs.getTimestamp("date"),
	                    rs.getInt("parent_id"),
	                    rs.getBoolean("is_folder"),rs.getInt("owner_id") ) );
	           
				 
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
			 	
			 	request.setAttribute("currentId", currentId);

		        request.setAttribute( "file", fileSystem );
		        request.getRequestDispatcher( "/WEB-INF/SharedFileDisplay.jsp" )
		            .forward( request, response );
			
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
