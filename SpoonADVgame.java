import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

class SpoonADVgame{
  //creates a deck for the player, a deck for the CPU, and a place to discard a card each (table), plus a new Deck Object
  private ArrayList<Card> player = new ArrayList<>();
  private ArrayList<Card> CPU = new ArrayList<>(); 
  private ArrayList<Card> table = new ArrayList<>(); 
  private Deck deck = new Deck(); 
  private boolean next = true; 
  int counter = 1;

  public void start(){
    System.out.printf("%n %35s %n %n","///SPOON  - Advanced///");
    deck.create();//create a deck, removes the Jolly and all cards above 7 (below)
    deck.removeJ();
    deck.removeS();
    deck.remove7to10();
    deck.shuffleX();
    player.addAll(deck.get(4));//gives 4 cards to the player
    CPU.addAll(deck.get(4));//gives 4 cards to the CPU
    table.addAll(deck.get(1));
    this.phases();//starts the game
  }

  public void phases(){
    Scanner scan = new Scanner(System.in);
    int toRemove = -1;
    while(next){//main loop of the game
      this.bothHands();//visualize player and CPU hands
      if(deck.size()==0){ //"draw" EXIT, all cards have been drawed
        System.out.printf("%n %35s %n ","///DRAW (no more cards)///\n");
        System.exit(1);
      }
      while(true){//loop to check validity of input
        System.out.print("do you want the card from the table? y/n: ");
        String selection = scan.next();
        scan.nextLine();
        if(selection.contains("y") || selection.contains("Y")){//if the player wants the card from the table, it will ask wich card to remove, then make the swap
          System.out.print("which card to remove: ");
          if(scan.hasNextInt()){//check if the value is not a char or string
            toRemove = scan.nextInt();
            if(toRemove >= 0 && toRemove<=3){//check if the number is in range
              player.add(table.get(0));
              table.remove(0);
              table.add(player.get(toRemove));
              player.remove(toRemove);
              break;
              } else {
              System.out.print("Error, wrong number//"); 
              }
            }else{
            System.out.print("Error, not a number//");
            scan.next();
          }
        }
        if(selection.contains("N")|| selection.contains("n")){//if the player doesn't wants to swap cards with the table, swaps a card from the deck, then replace the one on the table
          System.out.print("which card to remove: ");
          if(scan.hasNextInt()){//check if the value is not a char or string
            toRemove = scan.nextInt();
            if(toRemove >= 0 && toRemove<=3){//check if the number is in range
              table.remove(0);
              table.add(player.get(toRemove));
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

      }
      if(player.get(0).getNumber()==player.get(1).getNumber()&&player.get(2).getNumber()  ==player.get(3).getNumber()&&player.get(1).getNumber()==player.get(2).getNumber()){
        //EXIT winning condition, all player cards have the same number
        next = false; //ends the while loop
        System.out.printf("%n %35s %n ","///WINNER///\n");
        this.bothHandsEND();//shows CPU and player one more time, speciaal visualization with no card selection prompt
        System.exit(1);
      }
      CPUPlays();//the computer plays his phase
      counter++;
      //empty the screen
      //System.out.print("\033[H\033[2J");
      //System.out.flush();
      //empty the screen
    } 
  }

  public void CPUPlays(){
    HashSet <String> cards = new HashSet<>();//HashSet to find duplicate
    ArrayList <Card> extra = new ArrayList<>();//ArrayList to store duplicate
    for (int i = 0; i<CPU.size();i++){//cycle thorugh CPU cards and attempt to assign the card to the Set
      if(cards.add(CPU.get(i).getNumber())== false){//if assignment fails (because cards is HashSet) and a copy is already present
        extra.add(CPU.get(i));//then add to extra list, as it is a duplicate(1)
      }
    }
    if(!extra.isEmpty()){//if a card has been assigned to Set, we need to add to extra his  original duplicate
      for(Card e : CPU){
        if(e.getNumber()==(extra.get(0).getNumber())){
          extra.add(e); //add the duplicates(2)
      }
    }
    extra.remove(0);//removes the first that was assigned in (1), otherwise is a double duplicate
    if(extra.size()==4){//needs to remove another duplicate if there are 3 similar cards from(2)
      extra.remove(0);
    }
  }
    CPU.removeAll(extra);//remove the repeated card from the deck (if any), so we can work with just the spare cards

    if(extra.isEmpty()){ //if there are no extra cards, we need to check if there is a match with the card on the table
      for(Card e : CPU){
        if(e.getNumber()==table.get(0).getNumber()){
          extra.add(table.get(0));
          table.remove(0);
          table.add(CPU.get(0));
        //if there is, add it to extra, remove that card from the table, and put back one from the deck
        }
      }
    //to avoid concurrentModificationException, remove a card from the deck, add all the extra to the CPU deck, then clear extra 
    CPU.remove(0);
    CPU.addAll(extra);
    extra.clear();
  }

  if(!extra.isEmpty() && table.get(0).getNumber()== extra.get(0).getNumber()){ //if there are extra cards, we still need to check if there is a match with the card on the table, it's enought to check the first card on the extra to do so.
    extra.add(table.get(0));
    table.remove(0);
    table.add(CPU.get(0));
    CPU.remove(0);
    CPU.addAll(extra);
    extra.clear();
    //if so, add the card on the table to extra, remove from the table, add to the table a card from the CPU(non extra) remove a card from CPU(non extra), then add all the extra and clear that list 
  } else {//if there is no match between the table and the deck, and there is no duplicated card, simply take a card from the deck, remove from the top of the hand, and replace the one on the table.
    table.remove(0);
    table.add(CPU.get(0));
    CPU.remove(0); 
    CPU.addAll(deck.get(1));
    CPU.addAll(extra);
    extra.clear();
  }
  

  if(CPU.get(0).getNumber()==CPU.get(1).getNumber() && CPU.get(1).getNumber()==CPU.get(2).getNumber() && CPU.get(2).getNumber()==CPU.get(3).getNumber()){//if all the numbers are similar
    System.out.printf("%35s","LOST THE GAME IN ");//then you lost the game, EXIT
    System.out.println(counter + " PHASES\n");
    this.bothHandsEND();
    System.exit(1);
   }
  }

public void bothHands(){
  System.out.print("PHASE");
  System.out.print(counter<10?"0"+counter: counter);//add a zero if less than 2 digits
  System.out.print("---------------------------------------\n");
  System.out.printf("%20s %n", "PLAYER");
  for(int i = 0; i<CPU.size();i++){
    System.out.printf("%15s %s %15s %n",player.get(i) , "["+i+"]" ,CPU.get(i));
  }
  System.out.println("\ncard on the deck: " + table);
  System.out.println("---------------------------------cards left:"+ deck.size());
}

public void bothHandsEND(){//removes the number for selection in the player cards, used for game over
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


