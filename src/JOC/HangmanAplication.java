package JOC;

import java.io.IOException;
import java.util.Scanner;

public class HangmanAplication
{

    public static void main(String[] args) throws IOException
    {
        //How to play the game
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Hangman! I will pick a word and you" +
                " will try to guess itcharacter by character.\n" +
                "If you guess wrong 6 times, then I win. " +
                "If you can guess it before then, you win.");
        System.out.println();
        System.out.println("I have picked my word. below is a picture, and below that" +
                " is your guess, which starts off\nas nothing. every time you " +
                "guess incorectly, I add a bodi part to the picture.\nWhen there" +
                "is a full person, you lose.");

        //allow for multiple game
        boolean doYouWantToPLay = true;
        while (doYouWantToPLay)
        {
            //setting the game
            System.out.println("Alright! Let's play");
            Hangman game = new Hangman();

            do {
                //Draw the things...
                System.out.println();
                System.out.println(game.drawPicture());
                System.out.println();
                System.out.println(game.getFormalCurrentGuess());
                //System.out.println(game.mysteryWord);
                System.out.println();

                //get the guess
                System.out.println("Enter a character that you think" +
                        " is in the word");
                char guess = (sc.next().toLowerCase()).charAt(0);
                System.out.println();

                //check if the character is guessed already
                while (game.isGuessedAlready(guess))
                {
                    System.out.println("Try again! you've alreay " +
                            "gessed that character");
                    guess = (sc.next().toLowerCase()).charAt(0);
                }

                //play the guess
                if (game.playGuess(guess))
                {
                    System.out.println("Great guess! That character is in the word");
                }
                else
                {
                    System.out.println("Unfortunately that character isn't in the word.");
                }

            }
            while(!game.gameOver());//keep going until the game is over


            //play again or not
            System.out.println();
            System.out.println("Do you want to play another game? Enter Y if you do");
            Character response = (sc.next().toUpperCase()).charAt(0);
            doYouWantToPLay = (response == 'Y');

        }
    }
}


