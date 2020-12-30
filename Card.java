import java.util.ArrayList;
class Card{
  private String color;
  private String suit;
  private String number;

  public Card (String color, String suit, String number){
    this.color = color;
    this.suit = suit;
    this.number = number;
  }
  public Card (String number, String color){ //constructor for the jolly
    this.number =number;
    this.suit = "";
    this.color = color;
  }

  public String getColor(){
    return this.color;
  }

  public String getSuit(){
    return this.suit;
  }

  public String getNumber(){
    return this.number;
  }

  public String toString(){
    return this.suit.equals("")?this.color + " "+  this.number : this.color +" " + this.suit +" "+ this.number;
  }
}