import java.util.Stack;
class test{
    public static void main(String[] args) {
        Stack<Card>deck = new Stack<Card>();
        //3 cards
        deck.push(new Card("3",Card.Suites.Diamonds, 1, "Game_Images\\Cards\\3_diamonds.png"));
        deck.push(new Card("3",Card.Suites.Clubs, 1, "Game_Images\\Cards\\3_clubs.png"));
        deck.push(new Card("3",Card.Suites.Hearts, 1, "Game_Images\\Cards\\3_hearts.png"));
        deck.push(new Card("3",Card.Suites.Spades, 1, "Game_Images\\Cards\\3_spades.png"));

        //4 cards
        deck.push(new Card("4",Card.Suites.Diamonds, 2, "Game_Images\\Cards\\4_Diamonds.png"));
        deck.push(new Card("4",Card.Suites.Clubs, 2, "Game_Images\\Cards\\4_Clubs.png"));
        deck.push(new Card("4",Card.Suites.Hearts, 2, "Game_Images\\Cards\\4_Hearts.png"));
        deck.push(new Card("4",Card.Suites.Spades, 2, "Game_Images\\Cards\\4_Spades.png"));

        //5 cards
        deck.push(new Card("5",Card.Suites.Diamonds, 3, "Game_Images\\Cards\\5_diamonds.png"));
        deck.push(new Card("5",Card.Suites.Clubs, 3, "Game_Images\\Cards\\5_clubs.png"));
        deck.push(new Card("5",Card.Suites.Hearts, 3, "Game_Images\\Cards\\5_hearts.png"));
        deck.push(new Card("5",Card.Suites.Spades, 3, "Game_Images\\Cards\\5_Spades.png"));

        //6 cards
        deck.push(new Card("6",Card.Suites.Diamonds, 4, "Game_Images\\Cards\\6_diamonds.png"));
        deck.push(new Card("6",Card.Suites.Clubs, 4, "Game_Images\\Cards\\6_clubs.png"));
        deck.push(new Card("6",Card.Suites.Hearts, 4, "Game_Images\\Cards\\6_hearts.png"));
        deck.push(new Card("6",Card.Suites.Spades, 4, "Game_Images\\Cards\\6_spades.png"));

        //7 cards
        deck.push(new Card("7",Card.Suites.Diamonds, 5, "Game_Images\\Cards\\7_diamonds.png"));
        deck.push(new Card("7",Card.Suites.Clubs, 5, "Game_Images\\Cards\\7_clubs.png"));
        deck.push(new Card("7",Card.Suites.Hearts, 5, "Game_Images\\Cards\\7_hearts.png"));
        deck.push(new Card("7",Card.Suites.Spades, 5, "Game_Images\\Cards\\7_spades.png"));

        //8 cards
        deck.push(new Card("8",Card.Suites.Diamonds, 6, "Game_Images\\Cards\\8_diamonds.png"));
        deck.push(new Card("8",Card.Suites.Clubs, 6, "Game_Images\\Cards\\8_clubs.png"));
        deck.push(new Card("8",Card.Suites.Hearts, 6, "Game_Images\\Cards\\8_hearts.png"));
        deck.push(new Card("8",Card.Suites.Spades, 6, "Game_Images\\Cards\\8_spades.png"));

        //9 cards
        deck.push(new Card("9",Card.Suites.Diamonds, 7, "Game_Images\\Cards\\9_diamonds.png"));
        deck.push(new Card("9",Card.Suites.Clubs, 7, "Game_Images\\Cards\\9_clubs.png"));
        deck.push(new Card("9",Card.Suites.Hearts, 7, "Game_Images\\Cards\\9_hearts.png"));
        deck.push(new Card("9",Card.Suites.Spades, 7, "Game_Images\\Cards\\9_spades.png"));

        //10 cards
        deck.push(new Card("10",Card.Suites.Diamonds, 8, "Game_Images\\Cards\\10_diamonds.png"));
        deck.push(new Card("10",Card.Suites.Clubs, 8, "Game_Images\\Cards\\10_clubs.png"));
        deck.push(new Card("10",Card.Suites.Hearts, 8, "Game_Images\\Cards\\10_hearts.png"));
        deck.push(new Card("10",Card.Suites.Spades, 8, "Game_Images\\Cards\\10_spades.png"));

        //jacks
        deck.push(new Card("Jack",Card.Suites.Diamonds, 9, "Game_Images\\Cards\\jack_diamonds.png"));
        deck.push(new Card("Jack",Card.Suites.Clubs, 9, "Game_Images\\Cards\\jack_clubs.png"));
        deck.push(new Card("Jack",Card.Suites.Hearts, 9, "Game_Images\\Cards\\jack_hearts.png"));
        deck.push(new Card("Jack",Card.Suites.Spades, 9, "Game_Images\\Cards\\jack_spades.png"));
        
        //queens
        deck.push(new Card("Queen",Card.Suites.Diamonds, 10, "Game_Images\\Cards\\Queen_diamonds.png"));
        deck.push(new Card("Queen",Card.Suites.Clubs, 10, "Game_Images\\Cards\\Queen_clubs.png"));
        deck.push(new Card("Queen",Card.Suites.Hearts, 10, "Game_Images\\Cards\\Queen_hearts.png"));
        deck.push(new Card("Queen",Card.Suites.Spades, 10, "Game_Images\\Cards\\Queen_spades.png"));
        
        //kings
        deck.push(new Card("King",Card.Suites.Diamonds, 11, "Game_Images\\Cards\\King_diamonds.png"));
        deck.push(new Card("King",Card.Suites.Clubs, 11, "Game_Images\\Cards\\King_clubs.png"));
        deck.push(new Card("King",Card.Suites.Hearts, 11, "Game_Images\\Cards\\King_hearts.png"));
        deck.push(new Card("King",Card.Suites.Spades, 11, "Game_Images\\Cards\\King_spades.png"));

        //ace cards
        deck.push(new Card("Ace",Card.Suites.Diamonds, 12, "Game_Images\\Cards\\ace_diamond.png"));
        deck.push(new Card("Ace",Card.Suites.Clubs, 12, "Game_Images\\Cards\\ace_clubs.png"));
        deck.push(new Card("Ace",Card.Suites.Hearts, 12, "Game_Images\\Cards\\Ace_hearts.png"));
        deck.push(new Card("Ace",Card.Suites.Spades, 12, "Game_Images\\Cards\\ace_spades.png"));

        //2 cards
        deck.push(new Card("2",Card.Suites.Diamonds, 13, "Game_Images\\Cards\\2_diamonds.png"));
        deck.push(new Card("2",Card.Suites.Clubs, 13, "Game_Images\\Cards\\2_clubs.png"));
        deck.push(new Card("2",Card.Suites.Hearts, 13, "Game_Images\\Cards\\2_hearts.png"));
        deck.push(new Card("2",Card.Suites.Spades, 13, "Game_Images\\Cards\\2_spades.png"));

        //jokers
        deck.push(new Card("Joker",Card.Suites.Hearts, 14, "Game_Images\\Cards\\Red_Joker.png"));
        deck.push(new Card("Joker",Card.Suites.Spades, 14, "Game_Images\\Cards\\black_joker.png"));


        Gameframe gameFrame = new Gameframe(deck);// initialize game
        //Testframe frame = new Testframe();

    }


}