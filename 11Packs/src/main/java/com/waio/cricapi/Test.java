
package com.waio.cricapi;
import java.io.IOException;

public class Test {
   
	 
     
	public static void main(String[] args) throws IOException{
    	
    	//API api = new API();
    	
    	//api.getEmployees();
    	
		//NewMatchesAPI api = new NewMatchesAPI();
		//api.newMatches();
		
		SquadAPI api = new SquadAPI();
		api.getTeamSquad();
		
    }
    
}
    
