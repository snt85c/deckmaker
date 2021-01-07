import java.util.ArrayList;
import java.util.Random; 
import java.util.Collections;

class Deck{
  private ArrayList<Card> deck= new ArrayList<>();
  private Random rand = new Random();

  public Deck(){}

  public void add(Card card){
    this.deck.add(card);
  }

  public ArrayList<Card> get(int range){ //get an amount of card specified in range, removes from deck
    ArrayList<Card> hand = new ArrayList<>();
    for(int i =0; i<range;i++){
      hand.add(deck.get(i));
    }
    deck.removeAll(hand);
    return hand;
  }

  public void create(){ //create a sorted deck
      final String numbers[] = {"1","2","3","4","5","6","7","8","9","10","Jack", "Queen", "King"};
      final String suits [] = {"♥", "♠", "♣", "♦"};
      final String colors [] = {"Black", "Red"};
      for(int z = 0; z<2; z++){
        for(int j= 0; j<4; j++){
          for(int i = 0; i<13; i++){
              deck.add(new Card(colors[z], suits[j],numbers[i] ));
              }
            }
          }
          deck.add(new Card("Jolly","Red"));
          deck.add(new Card("Jolly","Black"));
 
    }
  public String toString(){ //prints in columns of 6, non-parametric
    String print = "";
    int i = 0;
    for(Card e : deck){
      print+= e +" | ";
      i++;
      if(i==6){
        print+="\n";
        i=0;
      }
    }
    return print;
  }
  public String toString(ArrayList<Card> deck){ // signature with parameters, works with toRemove in removeJ() and draw(), otherwise it selects the Object.ToString() instead of this.toString()
  String print = "";
  if(deck.size()==1){
    return print += String.valueOf(deck.get(0).getColor()+" "+deck.get(0).getSuit()+" "+deck.get(0).getNumber());
  }else{
    int i = 0;
    for(Card e : deck){
      print+= e +" | ";
      i++;
      if(i==6){
        print+="\n";
        i=0;
      }
    }
  }
    return print;
  }

  public ArrayList<Card> draw(int amount){//draws and remove an amount of cards. prints and return the amount
    ArrayList<Card> drawed = new ArrayList<>();
    for (int i = 0 ; i< amount; i++){
      drawed.add(deck.get(i));
    }
    deck.removeAll(drawed);
    System.out.println("\ndrawing from top of the deck: " + this.toString(drawed));
    return drawed;
    
  }

  public void drawR(){ //only prints a random card, doesn't remove it.
    System.out.print("drawing a random card: ");
    int randcard = rand.nextInt(deck.size());
    System.out.println(deck.get(randcard));
  }

  public void shuffle(){ //shuffles the deck
    ArrayList<Card> temp = new ArrayList<>();
    while(!deck.isEmpty()){
      int index = rand.nextInt(deck.size());
      temp.add(deck.get(index));
      deck.remove(index);
    }
    deck = temp;
  }

  public void shuffleX(){//same as shuffle() but implements a method from Collections. no different outcome from previous implementation, simply showing different ay to do it.
    Collections.shuffle(deck);
  } 

  public String count(){ //count the cards
    long size = deck.stream().count();
    return "\ncounting: this deck has " + size + " cards.";
  }
   
  public void removeJ(){ // removes the two jolly,avoids ConcurrentModificationException by adding to another list and removing after the for loop
    ArrayList<Card> toRemove = new ArrayList<>();
    for(Card e: deck){
      if(e.getNumber().equals("Jolly")){
        toRemove.add(e);
      }
    }
    deck.removeAll(toRemove);
  }
  public void removeS(){ // removes special signs (Jack, Queen, King),
    ArrayList<Card> toRemove = new ArrayList<>();
    for(Card e: deck){
      if(e.getNumber().equals("Jack")||e.getNumber().equals("Queen")||e.getNumber().equals("King")){
        toRemove.add(e);
      }
    }
    deck.removeAll(toRemove);
  }
  public int size(){
    return deck.size();
  }

  public void remove7to10(){ //keeps only cards between 1 and 6 ( also, J,K,Q and Jolly) there are other removals to delete such. used for SpoonADV to make the game faster
    ArrayList toRemove = new ArrayList<>();
    for (Card e : deck){
      if(Integer.valueOf(e.getNumber())>=7 && Integer.valueOf(e.getNumber()) <=10){
        toRemove.add(e);
      }
    }
    deck.removeAll(toRemove);
  }

}