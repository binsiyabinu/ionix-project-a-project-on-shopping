package bro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin {
	
	
	public void admin() throws SQLException, IOException, ClassNotFoundException
	{
		ConnectionManager cm=new ConnectionManager();
		Connection con=cm.connection();
		Statement statement=con.createStatement();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean flag=false;
		boolean j=false;
	String uname,pass;
	System.out.println("Enter username:");
	uname=br.readLine();
	System.out.println("Enter password");
	pass=br.readLine();
	ResultSet res = statement.executeQuery("select username,password from login where username='"+uname+"' and password='"+pass+"'");
	if(res.next())//start of resultset
	{
		System.out.println("welcome  "+uname);
		
		do
		{
		
	
		System.out.println("Menu");
		System.out.println("1.Add product");
		System.out.println("2.Display inventory details");
		System.out.println("3.Logout");
		int choice = Integer.parseInt(br.readLine());
		
		switch(choice)//nested switch
		{
		//start of add product
		case 1:
		{//case 1.1 starts
			
			int pid,msq,price;
			System.out.println("Enter product id");
		    pid=Integer.parseInt(br.readLine());
		    System.out.println("Enter product name");
		    String pname=br.readLine();
		    System.out.println("Enter minSellQuantity");
		    msq=Integer.parseInt(br.readLine());
		    System.out.println("Enter price");
		    price=Integer.parseInt(br.readLine());
		    statement.executeUpdate("insert into add_product (product_id,product_name,min_sell_quantity,price) values ('"+pid+"','"+pname+"','"+msq+"','"+price+"')");
		    int qa;
			System.out.println("Enter the Quantity available currently");
			qa=Integer.parseInt(br.readLine());
			statement.executeUpdate("update add_product set quantity_available='"+qa+"' where product_id='"+pid+"' and product_name='"+pname+"'");
			int tc,pr = 0;
			ResultSet price1=statement.executeQuery("select price from add_product where product_id='"+pid+"' and product_name='"+pname+"'");
			if(price1.next())
			{
			 pr=price1.getInt("price");
			}
			tc=pr*qa;
			statement.executeUpdate("update add_product set total_cost='"+tc+"' where product_id='"+pid+"' and product_name='"+pname+"'");
			System.out.println("Total cost is "+tc);
		}break; //case 1.1 ends
		//end of add product
		
		
		//start of display inventory details
		case 2:
		{//case 1.2 starts
			
			String q="select * from add_product";
			ResultSet r1=statement.executeQuery(q);
			
			
			System.out.println("**************************************************Inventory Details*****************************************************************************");
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("product id\tproduct name\tmin sell quantity\tprice\ttotalcost\tavailability");
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
			if(r1.next())
			{
			do
			{
				System.out.println(r1.getInt(1)+"\t\t"+r1.getString(2)+"\t\t"+r1.getInt(3)+"\t\t\t"+r1.getInt(4)+"\t"+r1.getInt(5)+"\t\t"+r1.getInt(6));
			}while(r1.next());
			}
			else
			{
				System.out.println("No Records found!!!");
			}
			

		}break;
		
		//case 1.2 ends
		//end of display inventory details
		//logout
		case 3:
		{//case 1.3 starts 
			
			System.out.println("You are successfully logged out!!!");
			Main m=new Main();
			m.content();
			
		}break;
		//end of logout
		//case 1.3 ends
		}//end of nested switch
		System.out.println("Do u want to continue?(yes/no)");
	    String more=br.readLine();
	    if(more.equalsIgnoreCase("yes"))
	    {
	    	j=true;
	    }
	    else
	    {
	    	j=false;
	    }
		
}while(j);
//end of resultset
//end of admin login
//end of case 1
//start of user login

	}
}
}