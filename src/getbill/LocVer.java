package getbill;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LocVer
 */
public class LocVer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Double lat;
	Double lon;
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
		boolean inRest;
		if(request.getParameter("lat")!=null && request.getParameter("lon")!= null && request.getParameter("id")!=null &&
				isNumeric(request.getParameter("lat")) && isNumeric(request.getParameter("lon"))){
			lat = Double.valueOf(request.getParameter("lat"));
			lon = Double.valueOf(request.getParameter("lon"));
			String id = request.getParameter("id");
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			inRest = verify(lat, lon, id);
		}
		else{
			inRest = false;
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
		if(inRest){
			//set status
			response.setStatus(HttpServletResponse.SC_OK);
			//return url
			pw.write("https://alexwolff.proto.io/share/?id=115dcab4-4a17-44cc-9801-afcb3179a450&v=8");
		}
		else{
			//set status
			response.setStatus(HttpServletResponse.SC_OK);
			//return bot
			pw.write("http://184.73.50.25/locvererror.html");
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
	
	private static boolean verify(double lat, double lon, String id) throws IOException{
			//Earth's radius in kilometers
			final int R = 6371;
			double rLat;
			double rLon;
			if(id.equals("1")){
				//Cornell Tech's latitude
				rLat = 40.7414085137589;
				//Cornell Tech's Longitude
				rLon = -74.00179278965967;
			}
			else{
				//Eiffel Tower's latitude
				rLat = 48.8582;
				//Eiffel Tower's Longitude
				rLon = 2.2945;
			}
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
	        	return false;
	        	//throw new IOException();
	        }
	        //if distance is within 100 meters determine that the user is at the restaurante
	        if(distance <= 0.1){
	        	return true;
	        }
	        else{
	        	return false;
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
