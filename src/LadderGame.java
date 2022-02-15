import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class LadderGame {

    protected  ArrayList<ArrayList<String>> listOfWordLists = new ArrayList<>();


    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }


    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        ArrayList<String> words = new ArrayList<>();
        //find the length of the word
        int lengthOfWord = word.length();
        // index for the words list
        int wordsIndex = 0;

        if(withRemoval) {
            // for loop goes through all words that are the same size
            for (int word1 = 0; word1 < listOfWordLists.get(lengthOfWord).size(); word1++ ) {
                if (difference(word, listOfWordLists.get(lengthOfWord).get(word1),1)) { // use the private method that I wrote to find difference
                    words.add(wordsIndex,listOfWordLists.get(lengthOfWord).get(word1));
                    wordsIndex++;
                }
            }
            listOfWordLists.get(lengthOfWord).removeAll(words);
        } else {
            // for loop goes through all words that are the same size
            for (int word1 = 0; word1 < listOfWordLists.get(lengthOfWord).size(); word1++ ) {
                if (difference(word, listOfWordLists.get(lengthOfWord).get(word1),1)) { // use the private method that I wrote to find difference
                    words.add(wordsIndex,listOfWordLists.get(lengthOfWord).get(word1));
                    wordsIndex++;
                }
            }
        }

        return words;
    }

    public ArrayList<String> oneAway(String word, boolean withRemoval, ArrayList<ArrayList<String>> dictionary) {

        ArrayList<String> words = new ArrayList<>();
        //find the length of the word
        int lengthOfWord = word.length();
        // index for the words list
        int wordsIndex = 0;

        if (withRemoval) {
            // for loop goes through all words that are the same size
            for (int word1 = 0; word1 < dictionary.get(lengthOfWord).size(); word1++ ) {
                if (difference(word, dictionary.get(lengthOfWord).get(word1),1)) { // use the private method that I wrote to find difference
                    words.add(wordsIndex, dictionary.get(lengthOfWord).get(word1));
                    wordsIndex++;
                }
            }
            dictionary.get(lengthOfWord).removeAll(words);
        } else {
            // for loop goes through all words that are the same size
            for (int word1 = 0; word1 < dictionary.get(lengthOfWord).size(); word1++ ) {
                if (difference(word, (String) dictionary.get(lengthOfWord).get(word1),1)) { // use the private method that I wrote to find difference
                    words.add(wordsIndex, (String) dictionary.get(lengthOfWord).get(word1));
                    wordsIndex++;
                }
            }
        }
        return words;
    }

    // method that returns true if two words are different by one character
    private boolean difference(String word1, String word2, int difference) {
        int numOfDifferences = 0;

        for (int letter = 0; letter < word1.length(); letter++ ) {
            if (word1.charAt(letter) != word2.charAt(letter)) {
                numOfDifferences++;
            }
        }

        return numOfDifferences == difference;
    }

    protected int diff(String word1, String word2) {
        int differences = 0;

        for (int letter = 0; letter < word1.length(); letter ++) {
            if (word1.charAt(letter) != word2.charAt(letter)) {
                differences++;
            }
        }
        return differences;
    }

    public void listWords(int length, int howMany) {

        for (int i = 0; i < howMany; i++) {
            System.out.println(listOfWordLists.get(length).get(i));
        }

    }

    /*
        Reads a list of words from a file, putting all words of the same length into the same array.
     */
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }

            for (int i = 0; i <= longestWord; i++) {
                listOfWordLists.add(i,new ArrayList<>());
            }

            for (int i = 0; i < allWords.size(); i++) { //now I through the allWords arraylist and place the word in its length
                int wordLength = allWords.get(i).length();
                listOfWordLists.get(wordLength).add(allWords.get(i));
            }


        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }

    public abstract void play(String start, String end);
}