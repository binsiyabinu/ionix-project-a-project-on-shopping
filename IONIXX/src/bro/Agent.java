package bro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Agent {
	
	public void agent() throws ClassNotFoundException, SQLException, NumberFormatException, IOException
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
		System.out.println("Enter password:");
		pass=br.readLine();
		String date = null;
		int totalcost = 0;
		ResultSet res = statement.executeQuery("select username,password from agent_login where username='"+uname+"' and password='"+pass+"'");
		if(res.next())
		{//start of resultset
		System.out.println("welcome  "+uname+",");
		System.out.println("\nYou logged in successfully\n");
		
		do
		{//start of do while in case 2(agent)
		System.out.println("1.Buy/sell\n2.show history\n3.logout");
		int choice=Integer.parseInt(br.readLine());
		
		switch(choice)
		{//start of switch
		//start of buy or sell
		case 1:
		{//case 1 starts
		System.out.println("Enter the product id");
		int pid=Integer.parseInt(br.readLine());
		System.out.println("which transaction you want to perform?(buy/sell)");
		String trans=br.readLine();
		if(trans.equalsIgnoreCase("buy"))
		{//start of buy
		ResultSet r2 = statement.executeQuery("select product_name,price,quantity_available,min_sell_quantity,total_cost from add_product where product_id='"+pid+"'");
		if(r2.next())
		{	//start of result set r2 
				
			String name=r2.getString("product_name");
			int p=r2.getInt("price");//price
			System.out.println("product name: "+name);
			System.out.println("price: "+p);
			int minsellquan=r2.getInt("min_sell_quantity");//minimum sell quantity
			if(minsellquan>1)
			{//msq
				System.out.println("You can buy a pair of "+minsellquan);
			}//end
			int quana=r2.getInt("quantity_available");//quantity available
			int stock=(quana/minsellquan);
			if(quana>minsellquan)
			{//stock available
				System.out.println(stock+" stocks are available now");
			}//end
			else
			{//out of stock
				System.out.println("out of stock");
			}//end
			System.out.println("Enter the quantity you want?");//quantity wanted among available stocks
			int quanw= Integer.parseInt(br.readLine());//quantity wanted
			int tc=r2.getInt("total_cost");
			if(quanw<=stock)
			{//confirm order
				System.out.println("Enter date:");
				date=br.readLine();
				totalcost=quanw*p;
				System.out.println("Total amount is "+totalcost);
				System.out.println("Do you want to confirm your order");
				String more=br.readLine();
			    if(more.equalsIgnoreCase("yes"))
			    {//yes
			    	quana=quana-(quanw*minsellquan);
			    	System.out.println("Your order is placed now!!!");
			    	statement.executeUpdate("update add_product set quantity_available='"+quana+"' where product_id='"+pid+"' ");
			    	statement.executeUpdate("INSERT INTO  TRANSACTION_HISTORY (username,product_id,product_name,date,amount,quantity,trans) VALUES ('"+uname+"','"+pid+"','"+name+"','"+date+"','"+totalcost+"','"+quanw+"','"+trans+"')");
			    }//yes ends
			    else
			    {//no
			    	System.out.println("Order not placed");
			    }// no ends
			}//confirm order end
			else
			{//order not available start
				System.out.println("only "+stock+" stocks are available now");
			}//order not available end
			}//end of second result set
		}//end of buy
			if(trans.equalsIgnoreCase("sell"))
			{//starting of sell
				int quantdb,newquantity;
				System.out.println("Enter date");
			    String date1=br.readLine();
			    System.out.println("Enter the quantity to sell");
			    int  quantity=Integer.parseInt(br.readLine());
			    ResultSet r3 = statement.executeQuery("select * from add_product where product_id='"+pid+"'");
				if(r3.next())
				{	//starting of resultset r3
					
					String name=r3.getString("product_name");
					int p=r3.getInt("price");//price
					quantdb=r3.getInt("quantity_available");//quantity available fetched from database
					newquantity=quantdb+quantity;
					int totalcost1=quantity*p;
					
					statement.executeUpdate("update add_product set quantity_available='"+newquantity+"' where product_id='"+pid+"' ");
					
					statement.executeUpdate("INSERT INTO  TRANSACTION_HISTORY (username,product_id,product_name,date,amount,quantity,trans) VALUES ('"+uname+"','"+pid+"','"+name+"','"+date1+"','"+totalcost1+"','"+quantity+"','"+trans+"')");
			    }//ending of resultset r3
				
			}//ending of sell
				
		}//end of case 1
		break;
		case 2:
		{
			String q="select * from transaction_history";
			ResultSet r1=statement.executeQuery(q);
			
			
			System.out.println("**************************************************Transaction Details*****************************************************************************");
			System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("user\t\tproduct id\tproduct name\tdate\t\t\tamount\t\tquantity\tbuy/sell");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
			if(r1.next())
			{
			do
			{
				System.out.println(r1.getString(1)+"\t\t"+r1.getInt(2)+"\t\t"+r1.getString(3)+"\t\t"+r1.getString(4)+"\t\t"+r1.getInt(5)+"\t\t"+r1.getInt(6)+"\t\t"+r1.getString(7));
			}while(r1.next());
			}
			else
			{
				System.out.println("No Records found!!!");
			}
			
			
			}break;
		case 3:
		{
			System.out.println("You are successfully logged out!!!");
			Main m=new Main();
			m.content();
			
		}break;
		
		}//end switch in agent
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
		}while(j);//end of do-while
		}
	}

}

