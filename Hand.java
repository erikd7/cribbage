import java.util.ArrayList;
import java.util.Random;

class Hand{//could modify Hand to share class with Deck, but will separate for now

    int count;
    int handValue;
    ArrayList<Card> cards;

    public static Hand dummyHand = new Hand();//dummy hand
    //dummyHand.cards.add(Card.dummyCard);

    public Hand(){
        count = 0;
        handValue = 0;
        cards = new ArrayList<Card>();
    }

    public Hand(Hand og){
        count = og.count;
        handValue = og.handValue;
        cards = og.cards;
    }

    //override string for printouts
    public String toString(){
        return "The hand has " + cards;
    }

    //get a random card from any given hand/deck
    public Card randomCard(){
        int cardIndex = new Random().nextInt(this.cards.size()) + 1;//number for new card

        //need to make sure the card is active (hasn't been drawn). repeat this until find new card
        while(this.cards.get(cardIndex).active == false){
            cardIndex = new Random().nextInt(this.cards.size()) + 1;//number for new card
            }

        //return the random card
        return this.cards.get(cardIndex);
    }

    //this method essentially transfers a random card from the deck to the hand. used for dealing shuffled hands
    public Deck cardIntoHand(Deck gameDeck){
        //random number for draw of new card (this is the shuffle)
        int cardIndex = new Random().nextInt(51) + 1;//number for new card

        //need to make sure the card is active (hasn't been drawn). repeat this until find new card
        while(gameDeck.cards.get(cardIndex).active == false){
            cardIndex = new Random().nextInt(51) + 1;//number for new card
            }
        
        //now that we've found random card that is available, add this card to the hand
        cards.add(gameDeck.cards.get(cardIndex));

        //discard - inactivate the card in the deck
        gameDeck.cards.get(cardIndex).active = false;

        //we need to update the gamedeck with the discarded cards, so pass it back
        return gameDeck;
        
    }

    //pass in a string id for a card (e.g. "4H"), get the card back
    public Card getCard(String cardAbbr){
        for(Card c : this.cards){
            if(cardAbbr.equalsIgnoreCase(c.card)){
                return c;
            }
        }

        //if no match
        System.out.println("There is no " + cardAbbr + " in this hand.");
        return Card.dummyCard;
    }

    //pass in a string id for a card (e.g. "4H"), get the location for the card in the hand. -1 if dne
    public int getCardLoc(String cardAbbr){
        for(int c=0;c<this.cards.size();c++){
            if(cardAbbr.equalsIgnoreCase(this.cards.get(c).card)){
                return c;
            }
        }

        //if no match
        //System.out.println("There is no " + cardAbbr.toUpperCase() + " in this hand.");
        return -1;
    }

    //reset hand to empty
    public void reset(){
        this.count = 0;
        this.handValue = 0;
        this.cards = new ArrayList<Card>();
    }

    //performed on hand to be removed from. takes card from this and puts it in new hand, which is returned
    public Hand swapHand(String cardAbbr,Hand to_H){
        //get the card to add
        Card addCard = this.getCard(cardAbbr);

        //if the card is not in the hand, we return the dummyHand, which contains the dummyCard
        if(addCard.equals(Card.dummyCard)){
            System.out.print(cardAbbr + " is not in the hand. ");
            return dummyHand;
        }
        
        //add the card to the new hand
        to_H.cards.add(addCard);

        //remove card from the from_H
        this.cards.remove(addCard);

        //return the new hand
        return to_H;

    }

    //how many points earned from last addition to the hand
    public int goPoints(int total){
        int newPoints=0;
        int latest = this.cards.size()-1;
        //fifteen
        if(total == 15){
            newPoints+=2;
            System.out.println("Two points for 15.");
        }

        //31
        if(total == 31){
            newPoints+=1;
            System.out.println("Extra point for hitting 31.");
        }
        
        //pair - 2 then 3 then 4
        //2 pair
        if(latest >= 1 && this.cards.get(latest).rank == this.cards.get(latest-1).rank){//if two recent cards exist and their rank are equal
            int pairPoints=newPoints;
            newPoints+=2;
            //3 pair
            if(latest >= 2 && this.cards.get(latest).rank == this.cards.get(latest-2).rank){//if two recent cards exist and their rank are equal
            newPoints+=4;
                //4 pair!
                if(latest >= 3 && this.cards.get(latest).rank == this.cards.get(latest-3).rank){//if two recent cards exist and their rank are equal
                newPoints+=6;
                }
            }
            System.out.println("Plus " + (newPoints-pairPoints) + " for pairs.");
        }

        //runs
        int runPoints=newPoints;
        for(int n = 1;n<=latest-1;n++){//up to run of 5
            int x = 0;//prevents switching between increasing and decreasing sequences
            if(x != 1 && (this.cards.get(latest).rank == this.cards.get(latest-n).rank -n) && (this.cards.get(latest).rank == this.cards.get(latest-n-1).rank -n -1)){//if it's -1 one from the last card
                newPoints+=n+2;
                x = -1;
            }
            if(x != 1 && (this.cards.get(latest).rank == this.cards.get(latest-n).rank +n) && (this.cards.get(latest).rank == this.cards.get(latest-n-1).rank +n +1)){//if it's -1 one from the last card
                newPoints+=n+2;
                x = 1;
            }
        }
        if((newPoints-runPoints)>0){System.out.println("Plus " + (newPoints-runPoints) + " for runs.");}


        return newPoints;
    }

    
}