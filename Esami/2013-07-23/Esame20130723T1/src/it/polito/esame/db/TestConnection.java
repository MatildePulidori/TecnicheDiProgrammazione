package it.polito.esame.db;

import java.sql.Connection;

public class TestConnection {

	public static void main(String[] args) {
		
		
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			conn.close();
			System.out.println("Test PASSED");
	
		} catch (Exception e) {
			System.err.println("Test FAILED");
			
		}
	}

}
