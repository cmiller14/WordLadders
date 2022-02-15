import java.util.ArrayList;

public class LadderGameExhaustive extends LadderGame {


    public LadderGameExhaustive(String dictionaryFile) {
        super(dictionaryFile);
    }


    @Override
    public void play(String start, String end) {
        // clone dictionary that will be used instead so i can remove words that are used
        ArrayList<ArrayList<String>> copyListOfWordLists = new ArrayList<>();
        // now I need to go through the copy list and clone each of the arraylists inside it
        for (int list = 0; list < listOfWordLists.size(); list++) {
            copyListOfWordLists.add(list, new ArrayList<String>());
            for (int word = 0; word < listOfWordLists.get(list).size(); word++) {
                copyListOfWordLists.get(list).add(word, listOfWordLists.get(list).get(word));
            }
        }

        if (sameSize(start, end) && inDictionary(copyListOfWordLists, start, end)) {
            // find the correct word ladder
            WordInfo foundWordLadder = breadthSearch(copyListOfWordLists, start, end);
            // now I need to print out everything I need
            banner(start, end, foundWordLadder);

        } else {
            System.out.println("No ladder was found");
        }
    }

    private void banner(String start, String end, WordInfo foundWordLadder) {

        String history = foundWordLadder.getHistory();
        String[] words = history.split(" ");
        int enqueues = foundWordLadder.getEnqueues();

        if (foundWordLadder.getEnqueues() == 1) {
            System.out.printf("%s -> %s : No ladder was found\n",
                    start, end);
        } else {
            System.out.printf("Seeking exhaustive solution from %s -> %s\n", start,end);
            System.out.printf("[%s] total enqueues %d\n", history, enqueues );
//            System.out.printf("%s -> %s : %d Moves [%s] total enqueues %d\n",
//                    start, end, foundWordLadder.getMoves(), history , foundWordLadder.getEnqueues());
        }

    }


    private WordInfo breadthSearch(ArrayList<ArrayList<String>> dictionary, String start, String end) {
        // make a queue that will store all the words
        Queue<WordInfo> wordQueue = new Queue<WordInfo>();
        //first add the first word to the queue
        wordQueue.enqueue(new WordInfo(start,0, 1));
        // enqueues tracker
        int enqueues = 1;
        //moves tracker
        //boolean to return when the word is found
        boolean wordLadderComplete = false;
        // need a word info for to be used later
        WordInfo foundWord = new WordInfo(end,0, enqueues);
        //while loop for the algorithm
        while (!wordQueue.isEmpty() && !wordLadderComplete) {
            WordInfo dequeuedWord = wordQueue.dequeue();
            ArrayList<String> oneAwayWords = oneAway(dequeuedWord.getWord(),true, dictionary);
            for (String word : oneAwayWords) {
                var candidate = new WordInfo(word, dequeuedWord.getMoves() + 1, dequeuedWord.getHistory() + " " + word, enqueues);
                if (word.equals(end)){
                    wordLadderComplete = true;
                    foundWord = candidate;
                } else {
                    wordQueue.enqueue(candidate);
                    enqueues++;
                }
            }
        }
        return foundWord;
    }



    private boolean findWord(ArrayList<ArrayList<String>> dictionary,String word) {
        boolean exists = false;
        // loops through the dictionary to find if the word exists
        for (ArrayList<String> list : dictionary) {
            for (String listWord : list) {
                if (listWord.equals(word)) {
                    exists = true;
                }
            }
        }
        return exists;
    }


}