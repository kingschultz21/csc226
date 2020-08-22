/* BaseballElimination.java
   CSC 226 - Spring 2019
   Assignment 4 - Baseball Elimination Program
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java BaseballElimination
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test divisions (in the format described below) and run
   the program with
	java -cp .;algs4.jar BaseballElimination file.txt (Windows)
   or
    java -cp .:algs4.jar BaseballElimination file.txt (Linux or Mac)
   where file.txt is replaced by the name of the text file.
   
   The input consists of an integer representing the number of teams in the division and then
   for each team, the team name (no whitespace), number of wins, number of losses, and a list
   of integers represnting the number of games remaining against each team (in order from the first
   team to the last). That is, the text file looks like:
   
	<number of teams in division>
	<team1_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>
	...
	<teamn_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>

	
   An input file can contain an unlimited number of divisions but all team names are unique, i.e.
   no team can be in more than one division.


   R. Little - 03/22/2019
*/

/*
*	Connor Schultz
*	For CSC 226 Assignment 3
*/
import edu.princeton.cs.algs4.*;
import java.util.*;
import java.io.File;

//Do not change the name of the BaseballElimination class
public class BaseballElimination{
	/*
	*	Division Class:
	*		- Creates a division of n teams
			- Has methods for accesing teams in the division
	*/
	class Division{
		//Teams in the division
		private Team[] teams;

		//Create an empty division given a size
		public Division(int num_teams){
			this.teams = new Team[num_teams];
		}

		//Add a team to a division
		private void addTeam(Team team){
			this.teams[team.team_number] = team;
		}

		//Return an array of teams in the division
		private Team[] getTeams(){
			return this.teams;
		}

		//Access a specific team from the division given a team number
		private Team getTeam(int team_number){
			return this.teams[team_number];
		}

		//Return the number of teams in the division
		private int size(){
			return this.teams.length;
		}
	}
	/*
	*	Team Class:
	*		- Creates a Team with remaining game data and season statistics
	*		- Used in Division class
	*/
	class Team{
		//Team Attributes:
		private String team_name;
		private int team_number;
		private int wins;
		private int losses;
		private int remaining;
		private int[] games;

		//Team Constructor
		public Team(String team_name, int team_number, int wins, 
						int losses, int remaining, int[] games){
			this.team_name = team_name;
			this.team_number = team_number;
			this.wins = wins;
			this.losses = losses;
			this.remaining = remaining;
			this.games = games;
		}

		//Return number of wins in the season
		private int getWins(){
			return this.wins;
		}

		//Return number of losses in the season
		private int getLosses(){
			return this.losses;
		}

		//Return the number of remaining games
		private int getRemaining(){
			return this.remaining;
		}

		//Return the distinct team number
		private int getTeamNumber(){
			return this.team_number;
		}

		//Return the distinct team name
		private String getTeamName(){
			return this.team_name;
		}

		//Return an array representing the remaining games for the team
		//The current instance of team is located at games[team.getTeamNumber()]
		private int[] getGames(){
			return this.games;
		}

	}

	// We use an ArrayList to keep track of the eliminated teams.
	public ArrayList<String> eliminated = new ArrayList<String>();

	/* BaseballElimination(s)
		Given an input stream connected to a collection of baseball division
		standings we determine for each division which teams have been eliminated 
		from the playoffs. For each team in each division we create a flow network
		and determine the maxflow in that network. If the maxflow exceeds the number
		of inter-divisional games between all other teams in the division, the current
		team is eliminated.
	*/
	public BaseballElimination(Scanner s){
		Division division = createDivision(s);
		for(Team team: division.getTeams()){
			boolean elim = isEliminated(team.getTeamNumber(), division);
			if(elim){
				//System.out.println(team.getTeamName()+" was eliminated.");
				eliminated.add(team.getTeamName());
			}
		}
		//printDivision(division);
	}

	/*
	*	Returns a boolean if a given team is eliminated or not
	*	Calls trivialElimination and flowElimination (if necessary)
	*/
	public boolean isEliminated(int team_number, Division div){
		boolean is_elim = false;
		Team curr = div.getTeam(team_number);
		for(int i = 0; i < div.size()-1; i++){
			Team next = div.getTeam(i+1);
			if(i == team_number){
				continue;
			}
			
			if(trivialElimination(curr,next)){
				return true;
			}
		}
		is_elim = flowElimination(curr,div);
		return is_elim;

	}

	/*
	*	Determines if a team is eliminated by using a flow network and Ford-Fulkerson
	*/
	public boolean flowElimination(Team team, Division div){
		//Network attributes
		int poss_games = possibleGames(div.size());
		int size = div.size()+poss_games+1;
		FlowNetwork flow_network = new FlowNetwork(size);

		//Indices for properly indexing nodes in a logical manner
		int index1 = 0;
		int index2 = size-div.size()-1;
		int index3 = size-div.size();

		//Total flow
		int total = 0;

		for(int i = 0; i < div.size(); i++){
			if(i != team.getTeamNumber()){
				int games_i[] = div.getTeam(i).getGames();
				for(int j = 0; j < div.size(); j++){
					if(j != team.getTeamNumber() && j > i){
						//Add edges to Flow Network
						FlowEdge edge =  new FlowEdge(size-2,index1,games_i[j]);
						flow_network.addEdge(edge);
						edge =  new FlowEdge(index1,index2,Double.POSITIVE_INFINITY);
						flow_network.addEdge(edge);
						edge =  new FlowEdge(index1,index3,Double.POSITIVE_INFINITY);
						flow_network.addEdge(edge);

						//Update indices
						index1++;
						index3++;

						//Update total flow
						total += games_i[j];
					}
				}

				Team other = div.getTeam(i);
				int flow = team.getWins() + team.getRemaining() - other.getWins();
				
				//Trivial Elimination
				
				if(flow < 0){
					return true;
				}
				//Add edges to Flow Network
				FlowEdge edge =  new FlowEdge(index2,size-1,flow);
				flow_network.addEdge(edge);

				//Update indices
				index2++;
				index3 = index2;
			}
		}

		FordFulkerson ford_fulk = new FordFulkerson(flow_network,size-2,size-1);
		if(total == ford_fulk.value()){
			System.out.println("HERE");			
			return false;
		}else{
			int index = poss_games;
			while(index < size-2){
				if(!ford_fulk.inCut(index)){
					Team t = div.getTeam(index-poss_games);
					//Something is happening here...
					System.out.println(t.getTeamName());
					return false;
				}
				
				index++;
			}
			return true;
		}
	}
	/*
	*	Determines if a team is eliminated trivally
	*/
	public boolean trivialElimination(Team team, Team next){
		if(team.getWins() + team.getRemaining() < next.getWins()){
			return true;
		}else{
			return false;
		}
	}
	/*
	*	Returns the possible number of game combinations
	*/
	public int possibleGames(int n){
		n--;
		return n * (n-1) / 2;
	}
	/*
	*	Creates a Division of Teams From an Input Stream
	*/
	public Division createDivision(Scanner s){
		int num_teams = s.nextInt();
		Division division = new Division(num_teams);
		for(int i = 0; i < num_teams; i++){
			String name = s.next();
			int tn = i;
			int ws = s.nextInt();
			int ls = s.nextInt();
			int gms[] = new int[num_teams];
			int rmng = 0;
			for(int j = 0; j < num_teams; j++){
				gms[j] = s.nextInt();
				rmng += gms[j];
			}
			Team new_team = new Team(name, tn, ws, rmng, ls, gms);
			division.addTeam(new_team);
			//System.out.println(new_team.getRemaining());
		}
		return division;
	}
	/*
	*	Prints an entire division out to Stdin.
	*	Useful for debugging.
	*/
	public static void printDivision(Division div){
		for(int i = 0; i < div.size(); i++){
			Team team = div.getTeam(i);
			System.out.print(team.getTeamNumber()+"  "+team.getTeamName()+"  "
			+team.getWins()+"  "+team.getLosses()+"  "+team.getRemaining()+"  ");
			int[] games = team.getGames();
			for(int j = 0; j < div.size(); j++){
				System.out.print(games[j]+" ");
			}
			System.out.print("\n");
		}
	}	
	/* main()
	   Contains code to test the BaseballElimantion function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		BaseballElimination be = new BaseballElimination(s);
		

		
		if (be.eliminated.size() == 0)
			System.out.println("No teams have been eliminated.");
		else
			System.out.println("Teams eliminated: " + be.eliminated);
	}
}
