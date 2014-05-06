package getbill;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;

import com.mysql.jdbc.*;
import com.sun.crypto.provider.RSACipher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBase {
	private Connection connection;
	private static DataBase instance = null;
	private static Boolean goodDriver = false;
	private static Boolean goodConnection = false;
	
	protected DataBase(){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e){
			goodDriver = false;
			System.out.println("No MySQL JDBC Driver");
			e.printStackTrace();
			return;
		}
		goodDriver = true;
		System.out.println("MySQL JDBC DRiver Registered");
		
		String serverName = "localhost";
		String jujuDataBase = "juju";
		String user = "juju";
		String password = "juju";
		String url = "jdbc:mysql://" + serverName + "/" + jujuDataBase;
		
		try{
			connection = DriverManager.getConnection(url, user, password);
		}catch (SQLException e){
			goodConnection = false;
			System.out.println("Could not connect");
			e.printStackTrace();
			return;
		}
		goodConnection = true;
		
	}
	
	public static DataBase getInstance(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		System.out.println("Time: "+dateFormat.format(date));
		if(instance == null || goodDriver == false || goodConnection == false){
			instance = new DataBase();
		}
		return instance;
	}
		
/*
	public Boolean setLatLon(Double latitude, Double longitude){
		
		return true;
	}
	public Boolean setID(String restaurant, String table, String uid){
		Statement statement;
		ResultSet rs;
		try{
			statement = connection.createStatement();
		}catch (SQLException e){
		System.out.println("Could not create statement");
		e.printStackTrace();
		return false;
	}
		String sqlQuery = "Insert into table_ids where restaurant = " + restaurant + 
				" and table_number = " + table;
		try {
			rs = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute query");
			e.printStackTrace();
			return false;
		}
		try {
			while(rs.next()){
				id = rs.getString("uid");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not retrieve latitude");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	*/
	public Double getLat(String restaurant){
		Double lat = new Double(0);
		Statement statement;
		ResultSet rs;
		try{
			statement = connection.createStatement();
		}catch (SQLException e){
		System.out.println("Could not create statement");
		e.printStackTrace();
		return lat;
	}
		String sqlQuery = "Select latitude from locations where restaurant = '" + restaurant + "';";
		try {
			rs = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute query");
			e.printStackTrace();
			return lat;
		}
		try {
			while(rs.next()){
				lat = rs.getDouble("latitude");
				System.out.println(Double.toString(lat));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not retrieve latitude");
			e.printStackTrace();
		}
		return lat;
	}
	public Double getLon(String restaurant){
		Double lon = new Double(0);
		Statement statement;
		ResultSet rs;
		try{
			statement = connection.createStatement();
		}catch (SQLException e){
		System.out.println("Could not create statement");
		e.printStackTrace();
		return lon;
	}
		String sqlQuery = "Select longitude from locations where restaurant = '" + restaurant + "';";
		try {
			rs = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute query");
			e.printStackTrace();
			return lon;
		}
		try {
			while(rs.next()){
				lon = rs.getDouble("longitude");
				System.out.println(Double.toString(lon));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not retrieve latitude");
			e.printStackTrace();
		}
		return lon;
	}
	public String getID(String restaurant, String table){
		String id = "null";
		Statement statement;
		ResultSet rs;
		try{
			statement = connection.createStatement();
		}catch (SQLException e){
		System.out.println("Could not create statement");
		e.printStackTrace();
		return id;
	}
		String sqlQuery = "Select uid from table_ids where restaurant = '" + restaurant + 
				"' and table_number = '" + table + "';";
		try {
			rs = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute query");
			e.printStackTrace();
			return id;
		}
		try {
			while(rs.next()){
				id = rs.getString("uid");
				System.out.println(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not retrieve latitude");
			e.printStackTrace();
		}
		return id;
	}
	public Double getTotal(String uid){
		Double total = new Double(-1);
		Statement statement;
		ResultSet rs;
		try{
			statement = connection.createStatement();
		}catch (SQLException e){
		System.out.println("Could not create statement");
		e.printStackTrace();
		return total;
	}
		String sqlQuery = "Select total from bill_payments where uid = '" + uid + "';";
		try {
			rs = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute query");
			e.printStackTrace();
			return total;
		}
		try {
			while(rs.next()){
				total = rs.getDouble("total");
				System.out.println(String.valueOf(total));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not retrieve total");
			e.printStackTrace();
		}
		return total;
	}
	public int setPaid(String uid, Double paid){
		Statement statement;
		int success = 1;
		try{
			statement = connection.createStatement();
		}catch (SQLException e){
		System.out.println("Could not create statement");
		e.printStackTrace();
		return success;
	}
		String sqlQuery = "Update bill_payments set paid = '" + String.valueOf(paid) + "' where uid = '" + uid + "';";
		try {
			success = statement.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute query");
			e.printStackTrace();
			return success;
		}
		return success;
	}
	public int setTip(String uid, Double tip){
		Statement statement;
		int success = 1;
		try{
			statement = connection.createStatement();
		}catch (SQLException e){
		System.out.println("Could not create statement");
		e.printStackTrace();
		return success;
	}
		String sqlQuery = "Update bill_payments set tip = '" + String.valueOf(tip) + "' where uid = '" + uid + "';";
		try {
			statement.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not execute query");
			e.printStackTrace();
			return success;
		}
		return success;
	}
}
