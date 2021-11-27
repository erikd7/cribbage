//import java.util.ArrayList;
import java.util.Scanner;

class Cribbage{

    //cribbage variables
    Player p1;
    Player p2;

    //we'll push p1 and p2 into the dealer variables for things that alternate based on dealer/non-dealer
    Player dealer;
    Player nonDealer;

    Deck gameDeck;
    Hand crib;
    Hand flop;
    int round;
    int goCount;
    Hand goHand;

    public Cribbage(){
        //create our players
        p1 = new Player(1);
        p2 = new Player(2);

        //create new deck for game
        gameDeck = new Deck();

        //create the crib
        crib = new Hand();

        flop = new Hand();

        goCount = 0;
        goHand = new Hand();
        round = 0;
    }

    public static void main(String[] args){
        System.out.println("Time to play cribbage!");
        //wrap in do while loop to play again
        boolean again = false;
        do{
            
        //set up the game
        Cribbage game = new Cribbage();

        System.out.println("It's " + game.p1 + " versus " + game.p2 + ".");

        //START OF ROUND
        do{
        game.roundStart();

        //deal the cards
        game.deal();

        //select cards to go to the crib
        game.crib = game.dealer.createCrib(game.crib);
        game.crib = game.nonDealer.createCrib(game.crib);
        System.out.println(game.crib);

        //cut the deck--done by non-dealer
        System.out.println(game.nonDealer.name + " cuts the deck!");

        //draw top card 'flop'
        game.gameDeck = game.flop.cardIntoHand(game.gameDeck);
        System.out.println("The starter is the " + game.flop.cards.get(0).cardText);

        //play the go
        game.goPlay();

        //end of round
        game.roundEnd();
          
        }while(game.round < 5);

        //ask to play again
        String a = edScan("Enter y to play again.");

            if(a.equalsIgnoreCase("y")){
                again = true;
            }else{
                again = false;
            }
        }while(again == true);
    }

    //deal cards - 6 per player
    public void deal(){
        p1.phand.reset();
        p2.phand.reset();
        for(int dealt = 1;dealt<=6;dealt++){
            gameDeck = p1.phand.cardIntoHand(gameDeck);
            gameDeck = p2.phand.cardIntoHand(gameDeck);
        }
        
        //display the results of the deal
        System.out.println(this.dealer.name + " deals for " + p1.name + ": " + p1.phand);
        System.out.println(this.dealer.name + " deals for " + p2.name + ": " + p2.phand);
    }

    //start of round tasks
    public void roundStart(){
        this.round+=1;

        System.out.println("--------------ROUND " + this.round + "--------------\n");

        //assign the dealer--dealer will reference the p1 or p2 object, same for nonDealer
        if(this.p1.isDealer==true){
            this.dealer = this.p1;
            this.nonDealer = this.p2;
        }else{
            this.dealer = this.p2;
            this.nonDealer = this.p1;
        }
    }
    //end of round tasks
    public void roundEnd(){
        //new dealer. these changes will affect p1 and p2 objects, and they will be reassigned at the start of the loop in roundStart()
        this.dealer.isDealer = false;    
        this.nonDealer.isDealer = true;

        //show scores
        System.out.println("\nRound " +this.round+" ends. " +this.p1.name+" has "+this.p1.score+ " points and " +this.p2.name + " has " +this.p2.score+" points.\n");
    }


    public void goPlay(){
        Hand dealerGoHand = new Hand(this.dealer.phand);//so they can't choose same card twice
        Hand nonDealerGoHand = new Hand(this.nonDealer.phand);//so they can't choose same card twice
        String s;
        int timesSkipped = 0;//how many times has the current count been unplayable
        Player last = dealer;

        while(!(dealerGoHand.cards.isEmpty()) || !(nonDealerGoHand.cards.isEmpty())){//repeat until hands are empty - this is the Go/count level
            //break if no cards for 31 for dealer
            boolean canPlay = false;
            if(!(nonDealerGoHand.cards.isEmpty()));{//skip everything if they have no cards
            for(Card x : nonDealerGoHand.cards){
                if(this.goCount + x.value <= 31){
                    canPlay = true;
                    break;//don't need to keep searching
                }
            }
            
            if(canPlay && last.sameAs(dealer)){//everything below in the condition that they can play

            //first the nondealer
            do{
            s = edScan(nonDealer.name + ", the count is " + this.goCount + ". Play a card: " + nonDealerGoHand);
            if(this.goCount + nonDealerGoHand.getCard(s).value > 31){System.out.println("Go cannot exceed 31. Try again.");}
            }while(nonDealerGoHand.getCardLoc(s) == -1 || (this.goCount + nonDealerGoHand.getCard(s).value > 31));//repeat if card is not in hand
            this.goHand = nonDealerGoHand.swapHand(s, this.goHand);//move card from player hand to go hand
            //update go count total
            this.goCount += this.goHand.cards.get(this.goHand.cards.size()-1).value;
            //score
            this.nonDealer.score += this.goHand.goPoints(this.goCount);//update points for the player
            timesSkipped=0;//played a card, so reset times skipped
            last = nonDealer;
            }else if(!(canPlay)){
                timesSkipped++;
                if(timesSkipped>=2){//neither player can play a card, time to reset the goCount
                    this.goCount = 0;
                    timesSkipped=0;
                }else{//if it's just this player that can't play a card, other player gets a go
                    System.out.println("The count is " + this.goCount +" and no more cards can be played. " + dealer.name +" gets a point for last card.");
                    this.dealer.score+=1;
                }
            }
            }

            if(!(dealerGoHand.cards.isEmpty())){//skip everything if dealer has no cards
            //break if no cards for 31 for dealer
            canPlay = false;
            for(Card x : dealerGoHand.cards){
                if(this.goCount + x.value <= 31){
                    canPlay = true;
                    break;
                }
            }
            
            if(canPlay && last.sameAs(nonDealer)){

            //now the dealer
            do{
            s = edScan(dealer.name + ", the count is " + this.goCount + ". Play a card: " + dealerGoHand);
            if(this.goCount + dealerGoHand.getCard(s).value > 31){System.out.println("Go cannot exceed 31. Try again.");}
            }while(dealerGoHand.getCardLoc(s) == -1 || (this.goCount + dealerGoHand.getCard(s).value > 31));//repeat if card is not in hand
            this.goHand = dealerGoHand.swapHand(s, this.goHand);//move card from player hand to go hand
            //update go count total
            this.goCount += this.goHand.cards.get(this.goHand.cards.size()-1).value;
            //score
            this.nonDealer.score += this.goHand.goPoints(this.goCount);
            timesSkipped=0;//played a card, so reset times skipped
            last = dealer;
            }else if(!(canPlay)){
                timesSkipped++;
                if(timesSkipped>=2){//neither player can play a card, time to reset the goCount
                    this.goCount = 0;
                    timesSkipped=0;
                }else{
                    System.out.println("The count is " + this.goCount +" and no more cards can be played. " + nonDealer.name +" gets a point for last card.");
                    this.nonDealer.score+=1;
                }
            }
            }
            
        }
        //last card gets a point
        last.score+=1;
        System.out.println(last.name + " gets a point for last card.");
    }

    public static String edScan(String prompt){
        Scanner scanIn = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanIn.nextLine();

        return input;
    }
}