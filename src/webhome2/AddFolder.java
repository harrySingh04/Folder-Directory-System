package webhome2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/AddNewFolder")
public class AddFolder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AddFolder() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int currentId = Integer.parseInt(request.getParameter("currentId"));
		request.setAttribute("currentId",currentId);
		request.getRequestDispatcher("/WEB-INF/AddFolder.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		List<UserList> userList = new ArrayList<UserList>();
		List<FileDisplay> fileSystem = new ArrayList<FileDisplay>();
		int userId=0;
		int parentId = 0;
		int currentId = Integer.parseInt(request.getParameter("currentId"));
		String name = request.getParameter("name");
		 if(currentId != 0)
			 parentId = currentId;
			 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
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
	            
	            
	            for(UserList u:userList)
	            {
	            	if(u.getUsername().equals(username))
	            			userId = u.getUserId();
	            	
	            }
	            String sqlQuery2;
	            if(parentId!=0)
	            {
	            sqlQuery2 = "insert into files(name,type,size,date,parent_id,is_folder,owner_id) values('"+name+"','folder',0,'"+dateFormat.format(date)+"',"
	            		+ "'"+parentId+"','"+1+"','"+userId+"')";
	            }
	            else
	            {
	             sqlQuery2 = "insert into files(name,type,size,date,is_folder,owner_id) values('"+name+"','folder',0,"
	             		+ "'"+dateFormat.format(date)+"','"+1+"','"+userId+"')";
	            	
	            }
	            
	            stmt.executeUpdate(sqlQuery2);
	           
	            String sqlQuery3 = "select * from files where owner_id='"+userId+"'";
	            ResultSet rs2 = stmt.executeQuery( sqlQuery3 );
	            
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
		
	        String currentFileName = null;
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
	        
		
			request.setAttribute("file", fileSystem);
			request.setAttribute("currentId",currentId);
			request.setAttribute("parentId", parentId);
			request.setAttribute("currentFileName",currentFileName);
			System.out.println("Hey i am here");
			request.getRequestDispatcher("/WEB-INF/DisplayFolder.jsp").forward(request, response);
			

		
	}

}