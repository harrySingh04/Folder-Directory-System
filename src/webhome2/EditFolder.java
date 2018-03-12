package webhome2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/EditFolder")
public class EditFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EditFolder() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		List<FileDisplay> fileSystem=new ArrayList<FileDisplay>();
		 int currentId = Integer.parseInt(request.getParameter("folderId"));
		 
		 Connection c = null;
	        try
	        {
	        		String url = "jdbc:mysql://localhost/cs3220";
		            String user = "root";
		            String password = "123456";
		           
	            
		        String sqlQuery = "select * from files where id='"+currentId+"'";
	            c =  DriverManager.getConnection( url, user, password );
	            Statement stmt = c.createStatement();
	            ResultSet rs = stmt.executeQuery( sqlQuery );
	            
	            
	            while( rs.next() )
	                fileSystem.add( new FileDisplay( rs.getInt( "id" ),
	                    rs.getString( "name" ),rs.getString( "type" ),
	                    rs.getLong("size"),rs.getDate("date"),
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
		
		
		
		
		String fileName = null;
	
		fileName  = fileSystem.get(0).getName();
		
	
		request.setAttribute("fileName", fileName);
		request.setAttribute("currentId", currentId);
		request.getRequestDispatcher("/WEB-INF/RenameFolder.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = (String)request.getSession().getAttribute("username");
		List<UserList> userList = new ArrayList<UserList>();
		List<FileDisplay> fileSystem=new ArrayList<FileDisplay>();
		int folderId = Integer.parseInt(request.getParameter("folderId"));
		String fileName = request.getParameter("name");
		int userId,currentId=0,parentId=0;
		 Connection c = null;
	        try
	        {
	        		String url = "jdbc:mysql://localhost/cs3220";
		            String user = "root";
		            String password = "123456";
		           
	            
		       
	            c =  DriverManager.getConnection( url, user, password );
	            Statement stmt = c.createStatement();
	            
	            
	            
	            String sqlQuery = "select * from users where name='"+username+"'";
	            
	            ResultSet rs = stmt.executeQuery( sqlQuery ); 
	            
	            while(rs.next())
	            {
	            	userList.add(new UserList(rs.getInt("id"),rs.getString("name"),rs.getString("password")));
	            	
	            }
	            userId = 0;
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
	                    rs2.getLong("size"),rs2.getTimestamp("date"),
	                    rs2.getInt("parent_id"),
	                    rs2.getBoolean("is_folder"),rs2.getInt("owner_id") ) );
	            
	            
	            stmt.executeUpdate("update files set name = '"+fileName+"'where id = "+folderId);  

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
		
		
	     String oldFileName=null;
		for(FileDisplay f: fileSystem)
		{
			if(f.getId()==folderId)
			{
				oldFileName=f.getName();
				f.setName(fileName);
				currentId = f.getParent();
				
			}
			
		}
		String currentFileName = null;
		for(FileDisplay f: fileSystem)
		{
			if(f.getId()==currentId)
			{	
				parentId = f.getParent();
				currentFileName = f.getName();
				
			}
			
		}
	
			
		String path = getServletContext()
	            .getRealPath( "/WEB-INF/files/"+oldFileName);
	        File file = new File( path );
	        file.renameTo(new File(getServletContext()
		            .getRealPath( "/WEB-INF/files/"+fileName)));
		
	      
	                  
	        request.setAttribute("file", fileSystem);
			request.setAttribute("currentId",currentId);
			request.setAttribute("parentId",parentId);
			request.setAttribute("currentFileName",currentFileName);
			request.setAttribute("userId", userId);
			request.getRequestDispatcher("/WEB-INF/DisplayFolder.jsp").forward(request, response);

	}

}
