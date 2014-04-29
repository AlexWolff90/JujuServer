package getbill;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LocVer
 */
public class LocVer extends HttpServlet {
	DataBase database;
	private static final long serialVersionUID = 1L;
	Double lat;
	Double lon;
	String restaurant;
	String table;
	Double id;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocVer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String UID;
		database = DataBase.getInstance();
		if(request.getParameter("lat")!=null && request.getParameter("lon")!= null && request.getParameter("rst")!=null && 
				request.getParameter("tbl")!=null && isNumeric(request.getParameter("lat")) && isNumeric(request.getParameter("lon"))){
			lat = Double.valueOf(request.getParameter("lat"));
			lon = Double.valueOf(request.getParameter("lon"));
			String restaurant = request.getParameter("rst");
			String table = request.getParameter("tbl");
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			System.out.println("Got request");
			UID = verify(lat, lon, restaurant, table);
			HttpSession session = request.getSession();
			session.setAttribute("uid", UID);
		}
		else{
			UID = "null";
		}
		//create writer
		PrintWriter pw = response.getWriter();
		//attach headers to allow cross domain ajax
		try {
		    fixHeaders(response);
		} catch (Exception e) {
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    pw.println(buildErrorMessage(e));
		} catch (Throwable e) {
		    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    pw.println(buildErrorMessage(e));
		}
		
		//check if in restaurant
		if(!UID.equalsIgnoreCase("null")){
			//set status
			response.setStatus(HttpServletResponse.SC_OK);
			//return url
			pw.write("http://54.87.17.182:8080/jsp/bill.jsp");
		}
		else{
			//set status
			response.setStatus(HttpServletResponse.SC_OK);
			//return both
			pw.write("http://54.87.17.182/locvererror.html");
		}
		//flush buffer
		pw.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    fixHeaders(response);
	}
	
	//attach cross domain ajax headers
	private void fixHeaders(HttpServletResponse response) {
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, OPTIONS, DELETE");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.addHeader("Access-Control-Max-Age", "86400");
	}
	
	private String verify(double lat, double lon, String restaurant, String table) throws IOException{
			//Earth's radius in kilometers
			final int R = 6371;
			double rLat;
			double rLon;
			String id;
			//Query database for lat long and UID
			rLat = database.getLat(restaurant);
			rLon = database.getLon(restaurant);
			id = database.getID(restaurant, table);
			
			//calculate the difference of the latitudes in radians
	        Double latDistance = degreeToRadian(lat-rLat);
			//calculate the difference of the longitudes in radians
	        Double lonDistance = degreeToRadian(lon-rLon);
	        Double distance = new Double(1);
	        //calculate Haversine distance
	        Double h = Math.sin(latDistance/2) * Math.sin(latDistance/2) + 
	                   Math.cos(degreeToRadian(rLat)) * Math.cos(degreeToRadian(lat)) * 
	                   Math.sin(lonDistance/2) * Math.sin(lonDistance/2);
	        //if h is greater than 1 it will return an imaginary number
	        if(h<=1){
	        	//calculate distance in kilometers
	        	distance = 2 * R * Math.asin(Math.sqrt(h));
	        }
	        else{
	        	return "null";
	        	//throw new IOException();
	        }
	        //if distance is within 100 meters determine that the user is at the restaurante
	        if(distance <= 0.1){
	        	return id;
	        }
	        else{
	        	return "null";
	        }
				
		}
	
		private static Double degreeToRadian(Double degree) {
	        return degree * Math.PI / 180;
	    }
		
		private static boolean isNumeric(String str) {  
		  try  
		  {  
		    double d = Double.parseDouble(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
		}
		
		private static String buildErrorMessage(Exception e) {
		    String msg = e.toString() + "\r\n";
		 
		    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
		        msg += "\t" + stackTraceElement.toString() + "\r\n";
		    }
		 
		     return msg;
		}
		 
		private static String buildErrorMessage(Throwable e) {
		    String msg = e.toString() + "\r\n";
		 
		    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
		        msg += "\t" + stackTraceElement.toString() + "\r\n";
		    }
		 
		    return msg;
		}

}
