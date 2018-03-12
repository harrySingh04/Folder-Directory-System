package webhome2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DownloadFile() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		List<FileDisplay> fileSystem=new ArrayList<FileDisplay>();
		int currentId = Integer.parseInt(request.getParameter("currentId"));
		String fileName = null;
		
		
		 Connection c = null;
	        try
	        {
	        		String url = "jdbc:mysql://localhost/cs3220";
		            String user = "root";
		            String password = "123456";
	            
	 	          
	            c =  DriverManager.getConnection( url, user, password );
	            Statement stmt = c.createStatement();
	    
	           
	            String sqlQuery2 = "select * from files where id='"+currentId+"'";
	            ResultSet rs2 = stmt.executeQuery( sqlQuery2 );
	            
	            while( rs2.next() )
	                fileSystem.add( new FileDisplay( rs2.getInt( "id" ),
	                    rs2.getString( "name" ),rs2.getString( "type" ),
	                    rs2.getLong("size"),rs2.getTimestamp("date"),
	                    rs2.getInt("parent_id"),
	                    rs2.getBoolean("is_folder"),rs2.getInt("owner_id") ) );

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
		
		
		
		
		
		fileName = fileSystem.get(0).getName();
		String path = getServletContext()
            .getRealPath( "/WEB-INF/files/"+fileName );
        File file = new File( path );

        response.setHeader( "Content-Length", "" + file.length() );
        response.setHeader( "Content-Disposition",  "attachment");

        // Binary files need to read/written in bytes.
        FileInputStream in = new FileInputStream( file );
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[2048];
        int bytesRead;
        while( (bytesRead = in.read( buffer )) > 0 )
            out.write( buffer, 0, bytesRead );
        in.close();
        
     

       
	}

}
