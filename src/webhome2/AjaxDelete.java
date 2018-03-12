package webhome2;

import java.io.File;
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

@WebServlet("/AjaxDelete")
public class AjaxDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public AjaxDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<FileDisplay> fileSystem = new ArrayList<FileDisplay>();
		int folderId = Integer.parseInt(request.getParameter("id"));
		//System.out.println(folderId);
		//int folderId = 0;
		
		Connection c = null;
        try
        {
        	 String url = "jdbc:mysql://localhost/cs3220";
	         String user = "root";
	         String password = "123456";
            	
	         String sqlQuery = "select * from files where id = '"+folderId+"'";
	         
            c =  DriverManager.getConnection( url, user, password );
            Statement stmt = c.createStatement();               
            
            ResultSet rs = stmt.executeQuery( sqlQuery );
            
            	while( rs.next() )
            		fileSystem.add( new FileDisplay( rs.getInt( "id" ),
                    rs.getString( "name" ),rs.getString( "type" ),
                    rs.getLong("size"),rs.getTimestamp("date"),
                    rs.getInt("parent_id"),
                    rs.getBoolean("is_folder"),rs.getInt("owner_id") ) );
            	
            	
            	stmt.executeUpdate("delete from files where id = "+folderId); 
           
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
        
        
      	
        	if(!fileSystem.get(0).isFolder())
        	{
        	String path = getServletContext()
	            .getRealPath( "/WEB-INF/files/"+fileSystem.get(0).getName() );
	        File file = new File( path );
	        file.delete();	
        	}
        
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doPost(request,response);
	}

}
