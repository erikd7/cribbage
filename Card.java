class Card extends Hand{

    int suite;//1-4 alphabetical order (1 is clubs, 4 is spades)
    int rank;//1-13 (ace is 1, 11-13 j,q,k)
    int value;//like rank except face cards are 10
    String card;//simple abbreviation, e.g. 4H or KS or 10D
    String cardText;//more memory to store these rather than call a function, but given how many times we'd have to call this per game, and fact that it doesn't change, likely faster to just store it

    boolean active = true;//becomes false once the card is played and then discarded

    public static Card dummyCard = new Card(0,0);//dummy card accessible by all classes and instances

    //card constructor. cardText is built into cardAbbreviation
    public Card(int rank, int suite){
        this.suite = suite;
        this.rank = rank;

        if(rank > 10){
            this.value = 10;
        }else{
            this.value = rank;
        }

        

        this.card = cardAbbreviation(rank,suite);
        //cardText is assigned within the cardAbbreviation function
    }

    //card rank equality check
    public boolean cardEquals(String test){
        if(this.card == test){
            return true;
        }
        return false;
    }

    //method to take a card and return <rank word> of <suite word>
    public String cardToText(String rank,String suite){
        String cardText;
        //card rank character into full string
        switch(rank){
            case "J":
                cardText = "Jack";
                break;
            case "Q":
                cardText = "Queen";
                break;
            case "K":
                cardText = "King";
                break;
            case "A":
                cardText = "Ace";
                break;
            case "0":
                cardText = "Dummy";
                break;
            default://if not a face card, just send the rank through
                cardText = rank;
                break;
        }
        //card suite letter into full word, also format to <rank> of <suite>
        switch(suite){
            case "H":
                cardText = cardText.concat(" of Hearts");
                break;
            case "S":
                cardText = cardText.concat(" of Spades");
                break;
            case "C":
                cardText = cardText.concat(" of Clubs");
                break;
            case "D":
                cardText = cardText.concat(" of Diamonds");
                break;
            case "0":
                cardText = "Dummy";
                break;
        }
        return cardText;
    }

    //method to take rank (1-10, j, q, k, a) and convert to simple card abbreviation (e.g. 5C)
    public String cardAbbreviation(int rank, int suite){
        //case statement instead of parsing into string. switch rank and suite to string
        String abbrrank = "";
        switch(rank){
            case 1:
                abbrrank = "A";
                break;
            case 2:
                abbrrank = "2";
                break;
            case 3:
                abbrrank = "3";
                break;
            case 4:
                abbrrank = "4";
                break;
            case 5:
                abbrrank = "5";
                break;
            case 6:
                abbrrank = "6";
                break;
            case 7:
                abbrrank = "7";
                break;
            case 8:
                abbrrank = "8";
                break;
            case 9:
                abbrrank = "9";
                break;
            case 10:
                abbrrank = "10";
                break;
            case 11:
                abbrrank = "J";
                break;
            case 12:
                abbrrank = "Q";
                break;
            case 13:
                abbrrank = "K";
                break;
            case 0:
                cardText = "Dummy";
                break;
            default:
                abbrrank = "issueA";
                break;
        }
 
        String abbrSuite = "";
        switch(suite){
            case 1:
                abbrSuite = "C";
                break;
            case 2:
                abbrSuite = "D";
                break;
            case 3:
                abbrSuite = "H";
                break;
            case 4:
                abbrSuite = "S";
                break;
            case 0:
                cardText = "Dummy";
                break;
            default:
                abbrSuite = "uh-oh";
                break;
        }

        //use these to call the full text function
        this.cardText = cardToText(abbrrank,abbrSuite);

        //now return the abbreviation
        return abbrrank.concat(abbrSuite);
    }

    
    @Override//card string
    public String toString(){ 
        return card; 
    } 
}