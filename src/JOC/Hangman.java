package JOC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Hangman
{
    String mysteryWord;
    StringBuilder currentGuess;
    ArrayList<Character> previousGuess = new ArrayList<>();

    int maxTries = 6;
    int currentTry = 0;

    ArrayList<String> dictionary = new ArrayList<>();
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;

    public Hangman() throws IOException
    {
        initializeStreams();
        mysteryWord = pickWord();
        currentGuess = initializeCurrentGuess();
    }

    public void initializeStreams() throws IOException
    {
        try {
            File inFile = new File("src/JOC/dictionary.txt");
            fileReader = new FileReader(inFile);
            bufferedFileReader = new BufferedReader(fileReader);
            String currrentLine = bufferedFileReader.readLine();
            while (currrentLine != null)
            {
                dictionary.add(currrentLine);
                currrentLine = bufferedFileReader.readLine();
            }
            bufferedFileReader.close();
            fileReader.close();
        }
        catch (IOException e)
        {
            System.out.println("Could not init streams");
        }
    }

    public String pickWord()
    {
        Random rand = new Random();
        int wordIndex = Math.abs(rand.nextInt()) % dictionary.size();
        return dictionary.get(wordIndex);
    }

    public StringBuilder initializeCurrentGuess()
    {
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < mysteryWord.length() * 2; i++)
        {
            if (i % 2 == 0)
            {
                current.append("_");
            }
            else
            {
                current.append(" ");
            }
        }
        return current;
    }

    public String getFormalCurrentGuess()
    {
        return "Current guess: " + currentGuess.toString();
    }

    public boolean gameOver()
    {
        if(didWeWinn())
        {
            System.out.println();
            System.out.println("Congratulations! You guessed the right word: '" + mysteryWord.toUpperCase() + "'.");
            return true;
        }
        else if (didWeLose())
        {
            System.out.println();
            System.out.println("Sorry, you lost. You spent all of your 6 " +
                    "tries. The word was '" + mysteryWord.toUpperCase() + "'.");
            return true;
        }
        return false;
    }

    public boolean didWeWinn()
    {
        String guess = getCondensedCurrentGuess();
        return guess.equals(mysteryWord);
    }

    public boolean didWeLose()
    {
        return currentTry >= maxTries;
    }

    public String getCondensedCurrentGuess()
    {
        String guess = currentGuess.toString();
        return guess.replace(" ","");
    }

    public boolean isGuessedAlready(char guess)
    {
       return previousGuess.contains(guess);
    }

    public boolean playGuess(char guess)
    {
        boolean isItAGoodGuess = false;
        previousGuess.add(guess);
        for (int i = 0; i < mysteryWord.length(); i++)
        {
            if(mysteryWord.charAt(i) == guess)
            {
                currentGuess.setCharAt(i * 2, guess);
                isItAGoodGuess = true;

            }
        }
        if( !isItAGoodGuess)
        {
            currentTry++;
        }
        return isItAGoodGuess;
    }

    public String drawPicture()
    {
        switch (currentTry)
        {
            case 0: return noPersonDraw();
            case 1: return addHeadDraw();
            case 2: return addBodyDraw();
            case 3: return addOaneArmDraw();
            case 4: return addSecondArmDraw();
            case 5: return addFirstLegDraw();
            default: return fullPersonDraw();

        }
    }

    private String noPersonDraw()
    {
        return " - - - - -\n"+
                "|        |\n"+
                "|        \n" +
                "|       \n"+
                "|        \n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addHeadDraw()
    {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|       \n"+
                "|        \n" +
                "|       \n" +
                "|\n" +
                "|\n";
    }
    public String addBodyDraw()
    {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|        |  \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addOaneArmDraw()
    {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|        | \\ \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addSecondArmDraw()
    {
        return " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|        \n" +
                "|\n" +
                "|\n";
    }

    public String addFirstLegDraw()
    {
        return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|         \\ \n" +
                "|\n" +
                "|\n";
    }

    public String fullPersonDraw()
    {
       return   " - - - - -\n"+
                "|        |\n"+
                "|        O\n" +
                "|      / | \\ \n"+
                "|        |\n" +
                "|       / \\ \n" +
                "|\n" +
                "|\n";
    }
}
