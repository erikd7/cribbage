import java.util.ArrayList;

class Deck extends Hand{

    int count;
    //ArrayList<Card> cards; -- instead, declare this at Hand and let Deck inherit

    //constructor--fill deck with cards
    public Deck(){
        count = 52;

        cards = new ArrayList<Card>();
        for(int s=1;s<=4;s++){//for each suite s
            for(int r=1;r<=13;r++){//for each card value v
                cards.add(new Card(r,s));
            }
        }
    }

    @Override//card string
    public String toString(){ 
        return "The deck is " + cards; 
    }

}