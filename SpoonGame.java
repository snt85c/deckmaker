import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

class SpoonGame{
private ArrayList<Card> player = new ArrayList<>();
private ArrayList<Card> CPU = new ArrayList<>();
private Deck deck = new Deck();
private boolean next = true;
int counter = 1;

public void start(){
System.out.printf("%n %50s %n %n","///SPOON///");
  deck.create();//create a deck, removes the Jolly and all cards above 10 (below)
  deck.removeJ();
  deck.removeS();
  deck.shuffleX();
  player.addAll(deck.get(4));//gives 4 cards to the player
  CPU.addAll(deck.get(4));//gives 4 cards to the CPU
  this.phases();//starts the game
}

public void phases(){
  Scanner scan = new Scanner(System.in);
  int toRemove = -1;//initialize remove to a neutral setting
  while(next){//main loop of the game
    this.bothHands();
    if(deck.size()==0){ //draw exit, all cards have been drawed
      System.out.printf("%n %50s %n ","///DRAW (no more cards)///\n");
      System.exit(1);
    }
    while(true){//loop to check validity of input
      System.out.print("which card to remove?: ");
      if(scan.hasNextInt()){//check if the value is not a char or string
        toRemove = scan.nextInt();//add a number
         if(toRemove >= 0 && toRemove<=3){//check if the number is in range
          player.remove(toRemove);//remove the card from 0 to 3
          player.addAll(deck.get(1));//add one card to the player hand
          break;
          } else {
           System.out.print("Error, wrong number//"); 
          }
        }else{
        System.out.print("Error, not a number//");
        scan.next();
      }
    }
    if(player.get(0).getNumber()==player.get(1).getNumber()&&player.get(2).getNumber()==player.get(3).getNumber()&&player.get(1).getNumber()==player.get(2).getNumber()){
      //winning condition, all player cards have the same number
      next = false; //ends the while loop
      System.out.printf("%n %50s %n ","///WINNER///\n");
      this.bothHandsEND();//shows CPU and player one more itme
      System.exit(1);
    }
    CPUPlays();//the computer plays his phase
    counter++;//add +1 to the counter, for graphic purpose only no game-//empty the screenlogic breaker
    //empty the screen
    System.out.print("\033[H\033[2J");
    System.out.flush();
    //empty the screen
  }
}

public void CPUPlays(){
  HashSet <String> cards = new HashSet<>();//HashSet to find duplicate
  ArrayList <Card> extra = new ArrayList<>();//ArrayList to store duplicate
  for (int i = 0; i<CPU.size();i++){
    if(cards.add(CPU.get(i).getNumber())== false){//if assignment fails (because cards is HashSet)
      extra.add(CPU.get(i));//then add to extra list, as it is duplicate(1)
    }
  }
    if(!extra.isEmpty()){//if a card has been assigned to Set, we need to add to extra his duplicate
    for(Card e : CPU){
      if(e.getNumber()==(extra.get(0).getNumber())){//check in the CPU deck and tthe extra deck
       extra.add(e); //add the duplicates(2)
      }
    }
    extra.remove(0);//removes the first that was assigned in (1), otherwise is a double duplicate
    if(extra.size()==4){//needs to remove another duplicate if there are 3 similar cards from(2)
      extra.remove(0);
    }
  } 
  CPU.removeAll(extra);//remove the repeated card from the deck
  CPU.remove(0); //removes one card from his hand, as it should
  CPU.addAll(deck.get(1));//add one card in his hand, as it should
  CPU.addAll(extra);//add the repeated card to the CPU deck
  extra.clear();//clears the extra deck

  if(CPU.get(0).getNumber()==CPU.get(1).getNumber() && CPU.get(1).getNumber()==CPU.get(2).getNumber() && CPU.get(2).getNumber()==CPU.get(3).getNumber()){//if all the numbers are similar
    System.out.printf("%50s","LOST THE GAME IN ");//then you lost the game
    System.out.println(counter + " PHASES\n");
    this.bothHandsEND();
    System.exit(1);
  }
}

public void bothHands(){
    System.out.print("PHASE");
    System.out.print(counter<10?"0"+counter: counter);//add a zero if less than 2 digits
    System.out.print("---------------------------------------\n");
  System.out.printf("%15s %18s %n", "PLAYER", "COMPUTER");
  for(int i = 0; i<CPU.size();i++){
    System.out.printf("%15s %s %15s %n",player.get(i) , "["+i+"]" ,CPU.get(i));
  }
  System.out.println("---------------------------------cards left:"+ deck.size());
}

public void bothHandsEND(){//removes the number for selection, used for game over
    System.out.print("PHASE");
    System.out.print(counter<10?"0"+counter: counter);//add a zero if less than 2 digits
    System.out.print("---------------------------------------\n");
  System.out.printf("%15s %18s %n", "PLAYER", "COMPUTER");
  for(int i = 0; i<CPU.size();i++){
    System.out.printf("%15s %19s %n",player.get(i) , CPU.get(i));
  }
  System.out.println("---------------------------------cards left:"+ deck.size());
}

}