

import java.io.File;
import java.util.*;
import java.lang.Exception;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;


@WebServlet("/UploadServelet")
public class UploadServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	   private String filePath;
	   private int maxFileSize = 70* 1024;
	   private File file ;
	   private boolean isMultpart;
	   private int maxMemSize = 4 * 1024;
	  
       
    
    public UploadServelet() {
        super();
        
    }
    public void init( ){
        
        filePath = getServletContext().getInitParameter("file-upload"); 
     }
    
       
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 throw new ServletException("GET method used with " + getClass( ).getName( ) +": POST method required.");
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    try 
	    { 
	    	DiskFileItemFactory factory = new DiskFileItemFactory();
		    
		    factory.setSizeThreshold(maxMemSize);
		 
		    factory.setRepository(new File("c:\\temp"));
		    
		    ServletFileUpload upload = new ServletFileUpload(factory);

		    upload.setSizeMax( maxFileSize );
	   
	          List fileItems = upload.parseRequest(request);
	          Iterator i = fileItems.iterator();

	          System.out.println("<html>");
	          System.out.println("<head>");
	          System.out.println("<title>Servlet upload</title>");  
	          System.out.println("</head>");
	          System.out.println("<body>");
	          while ( i.hasNext () ) 
	          {
	             FileItem fi = (FileItem)i.next();
	             if ( !fi.isFormField () )	
	             {
	                // Get the uploaded file parameters
	                String fieldName = fi.getFieldName();
	                String fileName = fi.getName();
	                String contentType = fi.getContentType();
	                boolean isInMemory = fi.isInMemory();
	                long sizeInBytes = fi.getSize();
	                // Write the file
	                if( fileName.lastIndexOf("\\") >= 0 ){
	                   file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
	                }
	                else{
	                   file = new File( filePath + 
	                   fileName.substring(fileName.lastIndexOf("\\")+1)) ;
	                }
	                fi.write( file ) ;
	                System.out.println("Uploaded Filename: " + fileName + "<br>");
	             }
	          }
	          System.out.println("</body>");
	          System.out.println("</html>");
	    }
	     
	     catch(Exception e) 
	     {
	    	 System.out.println(e);
	     }
	     

		isMultpart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter( );
        if( !isMultpart ){
           out.println("<html>");
           out.println("<head>");
           out.println("<title>Servlet upload</title>");  
           out.println("</head>");
           out.println("<body>");
           out.println("<p>No file uploaded</p>"); 
           out.println("</body>");
           out.println("</html>");
           return;
	}
	}

}
