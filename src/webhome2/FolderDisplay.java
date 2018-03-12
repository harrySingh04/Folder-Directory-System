package webhome2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FolderDisplay")
public class FolderDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FolderDisplay() {
		super();

	}

	public void init(ServletConfig config) throws ServletException {
			
		super.init( config );

        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
		

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		List<FileDisplay> fileSystem=new ArrayList<FileDisplay>();
		List<UserList> userList = new ArrayList<UserList>();
		int userId;
		if(username == null)
		{
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			
		}
		else
		{
			 Connection c = null;
		        try
		        {
		        		String url = "jdbc:mysql://localhost/cs3220";
			            String user = "root";
			            String password = "123456";
		            
		            String sqlQuery = "select * from users";
		          
 		            c =  DriverManager.getConnection( url, user, password );
		            Statement stmt = c.createStatement();
		            ResultSet rs = stmt.executeQuery( sqlQuery );
		            
		            while(rs.next())
		            {
		            	userList.add(new UserList(rs.getInt("id"),rs.getString("name"),rs.getString("password")));
		            	
		            }
		           
		            userId=0;
		            
		            for(UserList u:userList)
		            {
		            	if(u.getUsername().equals(username))
		            			userId = u.getUserId();
		            	
		            }
		           
		            String sqlQuery2 = "select * from files where owner_id='"+userId+"'";
		            ResultSet rs2 = stmt.executeQuery( sqlQuery2 );
		            
		            while( rs2.next() )
		                fileSystem.add( new FileDisplay( rs2.getInt( "id" ),
		                    rs2.getString( "name" ),rs2.getString( "type" ),
		                    rs2.getLong("size"), rs2.getTimestamp("date"),
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
			
			
        int currentId = 0,parentId=0;
        
        String  currentObject = request.getParameter("currentId");
        String currentFileName=null;
        if(currentObject==null)
        	currentId = 0;
        else
        	currentId = Integer.parseInt(currentObject);
        
        if(currentId !=0)
        {
        	for(FileDisplay f: fileSystem)
        	{
        		if(f.getId()==currentId)
        		{
        			parentId = f.getParent();
        			currentFileName = f.getName();
        		}
        		
        	}
        	
        }
        
      
        
        request.setAttribute("currentId", currentId);
        request.setAttribute("userId", userId);
        request.setAttribute( "file", fileSystem );
        request.setAttribute("parentId", parentId);
        request.setAttribute("currentFileName",currentFileName);
        request.getRequestDispatcher( "/WEB-INF/DisplayFolder.jsp" )
            .forward( request, response );
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

