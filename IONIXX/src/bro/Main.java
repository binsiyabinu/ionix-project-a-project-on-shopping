/**
 * 
 */
package bro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author BINSIYA BINU
 *
 */
public class Main {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static Agent agent=new Agent();
	static Admin admin=new Admin();
	boolean flag=false;
	static boolean j=false;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NumberFormatException, IOException {
		
		
		ConnectionManager cm=new ConnectionManager();
		Connection con=cm.connection();
		Statement statement=con.createStatement();
		// TODO Auto-generated method stub
		content();
		
		
	}
	public static void content() throws IOException, ClassNotFoundException, SQLException
	{
		do
		{//main do while
		System.out.println("Menu\n1.Admin Login\n2.Agent Login\n3.Exit");
		int ch = Integer.parseInt(br.readLine());
		switch(ch)
		{//starting of  switch
		case 1:
		{
			admin.admin();
		}break;
		case 2:
		{
			agent.agent();
		}break;
		case 3:
		{
			System.exit(0);
		}
		}
		System.out.println("Do u want to login?(yes/no)");
	    String more=br.readLine();
	    if(more.equalsIgnoreCase("yes"))
	    {
	    	j=true;
	    }
	    else
	    {
	    	j=false;
	    }//ending of switch
		}while(j);

	}

}
