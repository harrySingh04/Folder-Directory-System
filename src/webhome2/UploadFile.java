package webhome2;

import java.io.File;

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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UploadFile() {
        super();
 
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int currentId = Integer.parseInt(request.getParameter("folderId"));
			
		request.setAttribute("currentId", currentId);
		request.getRequestDispatcher("/WEB-INF/Upload.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		List<UserList> userList = new ArrayList<UserList>();
		List<FileDisplay> fileSystem=new ArrayList<FileDisplay>();
		long fileSize=0;
		int i,length,currentId,userId=0;

		String fileName=null,currentObject=null;
		int parentId = 0;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		 DiskFileItemFactory factory = new DiskFileItemFactory();
	      
	        ServletContext servletContext = this.getServletConfig()
	            .getServletContext();
	        File repository = (File) servletContext
	                .getAttribute( "javax.servlet.context.tempdir" );
	        
	        factory.setRepository( repository );
	        
	        ServletFileUpload upload = new ServletFileUpload( factory );
	        String fileDir = getServletContext().getRealPath( "/WEB-INF/files" );
		
		
	        String fileType="";
	
           
        try
        {
            List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));;
            for( FileItem item : items )
            {
            	
                if( !item.isFormField() )
                {

                     fileName = (new File( item.getName() )).getName();
                    File file = new File( fileDir, fileName );
                    item.write( file );
                    fileSize = file.length();
                    i = fileName.indexOf(".");
                    if(i>0)
                    	fileType = fileName.substring(i+1);
 
         	
                }
                else
                {
                	 String fieldname = item.getFieldName();
                     String fieldvalue = item.getString();
                     currentObject = fieldvalue;          			
             }
                     
                }
                     
            }

      
        catch( Exception e )
        {
            throw new IOException( e );
        }
        
      
       currentId = Integer.parseInt(currentObject);
       if(currentId != 0)
			 parentId = currentId;
       
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
           if(parentId != 0)
           {
            sqlQuery2 = "insert into files(name,type,size,date,parent_id,is_folder,owner_id) values('"+fileName+"','"+fileType+"','"+fileSize+"','"+dateFormat.format(date)+"',"
           		+ "'"+parentId+"','"+0+"','"+userId+"')";
           }
           else
           {
        	   sqlQuery2 = "insert into files(name,type,size,date,is_folder,owner_id) values('"+fileName+"','"+fileType+"','"+fileSize+"',"
        	   		+ "'"+dateFormat.format(date)+"','"+0+"','"+userId+"')"; 
        	   
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
        request.setAttribute("userId", userId);
        request.setAttribute("currentFileName",currentFileName);
		request.getRequestDispatcher("/WEB-INF/DisplayFolder.jsp").forward(request, response);
     
	}

}
