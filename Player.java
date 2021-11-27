import java.util.Scanner;

public class Player{

    //players will have a name and a score
    String name;//player's name
    int score;//the player's score
    boolean isDealer;//is the player the dealer for this round?
    Hand phand;//the player's active hand of cards
    int num;

    //constructor -- read name from input, set score to zero
    Player(int num){
        //this.name = edScan("Enter name of player " + num + ":"); -- remove for testing
        this.name = "Player"+num;//for testing only
        
        this.score = 0;

        this.phand = new Hand();
        this.num = num;

        //to start the game, set player 1 to the dealer
        if(num == 1){
            this.isDealer = true;
        }else{
            this.isDealer = false;
        }
    }

    //override return string to just print their name.
    public String toString(){
        return this.name + " (" + score + " points)";
    }
    
    //method to create the crib
    public Hand createCrib(Hand cribHold){
        Hand crib;//we're going to be resetting if bad entry. this will be faster than rerunning swapHand a bunch of times
        boolean error = false;
        
        do{
            crib = cribHold;
        
        String cribs = edScan(this.name + ", enter your 2 crib cards: ");
        

        //take crib selections and pass them into hand to crib
        //FIRST CARD
        try{//include error handling
            //only complete this if the second card is valid as well. if not, we'll repeat the whole thing
            if(this.phand.getCardLoc(cribs.substring(cribs.indexOf(" ")+1,cribs.length())) != -1){//second card is valid
                crib = this.phand.swapHand(cribs.substring(0,cribs.indexOf(" ")),crib);
            }
            error = false;
            //if swapHand returns the dummyCard, there was an issue (e.g. card not in hand)
            if(crib.equals(Hand.dummyHand)){//later-don't remember why i didn't just do getCardLoc==-1
                error = true;//error is true so we have to repeat
                crib = cribHold;//reset the crib we just changed (faster than test calling swapHand)
                continue;//since there's an error, get out now
            }
        }
        catch(Exception e){
            System.out.println("Invalid entry. Enter two cards separated by a space, e.g. 8H AS");
            error = true;
            continue;//since there's an error, get out now
        }
        //SECONDCARD
        try{
            crib = this.phand.swapHand(cribs.substring(cribs.indexOf(" ")+1,cribs.length()),crib);
            error = false;
            //if swapHand returns the dummyCard, there was an issue (e.g. card not in hand)
            if(crib.equals(Hand.dummyHand)){
                error = true;//error is true so we have to repeat
                crib = cribHold;//reset the crib we just changed (faster than test calling swapHand)
                continue;//since there's an error, get out now
            }
        }
        catch(Exception e){
            System.out.println("Invalid entry. Enter two cards separated by a space, e.g. 8H AS");
            error = true;
            continue;//since there's an error, get out now
        }
    }while(error == true);
        

        return crib;
    }


    public boolean sameAs(Player test){
        if(this.num == test.num){
            return true;
        }

        return false;
    }

    public static String edScan(String prompt){
        Scanner scanIn = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanIn.nextLine();
        //scanIn.close();

        return input;
    }

}