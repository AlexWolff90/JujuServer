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
	
	protected DataBase(){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		System.out.println("Time: "+dateFormat.format(date));
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e){
			System.out.println("No MySQL JDBC Driver");
			e.printStackTrace();
			return;
		}
		System.out.println("MySQL JDBC DRiver Registered");
		
		String serverName = "localhost";
		String jujuDataBase = "juju";
		String user = "juju";
		String password = "juju";
		String url = "jdbc:mysql://" + serverName + "/" + jujuDataBase;
		
		try{
			connection = DriverManager.getConnection(url, user, password);
		}catch (SQLException e){
			System.out.println("Could not connect");
			e.printStackTrace();
			return;
		}
		
	}
	
	public static DataBase getInstance(){
		if(instance == null){
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
		String sqlQuery = "Select latitude from locations where restaurant = " + restaurant;
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
		String sqlQuery = "Select longitude from locations where restaurant = " + restaurant;
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
		String sqlQuery = "Select uid from table_ids where restaurant = " + restaurant + 
				" and table_number = " + table;
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not retrieve latitude");
			e.printStackTrace();
		}
		return id;
	}
}
