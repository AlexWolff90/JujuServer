package getbill;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class paybill
 */
public class Paybill extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Paybill() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataBase database = DataBase.getInstance();
		String submitButton = "";
		String tip = "";
		String UID = "";
		HttpSession session = request.getSession();
		UID = "1";//String.valueOf(session.getAttribute("uid"));
		submitButton = request.getParameter("submitButton");
		tip = request.getParameter("tipPercentage");
		System.out.println("uid "+UID);
		System.out.println("tip percentage "+tip);
		if (submitButton.equals("pay")){
			Double total = database.getTotal(UID);
			database.setPaid(UID, total);
			Double tipAmount;
			if(!tip.equals("custom")){
				tipAmount = total*Double.valueOf(tip)/100;
			}else{
				tipAmount = Double.valueOf(0);
			}
			database.setTip(UID, tipAmount);
		}
	}

}
