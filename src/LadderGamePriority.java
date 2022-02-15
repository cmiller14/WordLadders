import java.util.ArrayList;

public class LadderGamePriority extends LadderGame {

    public LadderGamePriority(String dictionaryFile) {
        super(dictionaryFile);
    }

    @Override
    public void play(String start, String end) {
        // need to make a priority queue which is the AVL tree
        AVLTree<WordInfoPriority> priorityQueue = new AVLTree<WordInfoPriority>();


        WordInfoPriority foundWord = prioritySearch(priorityQueue, start, end);

        printBanner(start, end, foundWord);


    }


    private WordInfoPriority prioritySearch(AVLTree priorityQueue, String start, String end) {
        priorityQueue.insert(new WordInfoPriority(start, 0, diff(start,end)));


        Boolean found = false;
        int enqueues = 1;
        WordInfoPriority foundWord =  new WordInfoPriority(end,0, enqueues);

        while (!found && !priorityQueue.isEmpty()) {
            // this actually finds the best word to delete because it will have the smallest priority or priority will be closest to 1
            WordInfoPriority currentWord = (WordInfoPriority)priorityQueue.deleteMin();
            // from this word I need to add all the one away words and then I need to see which has the best priority
            ArrayList<String> oneAwayWords = oneAway(currentWord.getWord(),false);
            // need to add all the words to the AVL tree
            for (String word : oneAwayWords) {
                int moves = currentWord.getMoves() + 1;
                var candidate = new WordInfoPriority(
                        word,
                        moves,
                        diff(end,word) + moves,
                        currentWord.getHistory() + word + " ",
                        enqueues
                );
                if (word.equals(end)) {
                    found = true;
                    foundWord = candidate;
                }
                if (priorityQueue.contains(candidate)) {
                    WordInfoPriority existingWord = (WordInfoPriority)priorityQueue.find(candidate);
                    int compare = candidate.compareTo(existingWord);
                    if (compare < 0) {
                        priorityQueue.insert(candidate);
                        enqueues++;
                    }
                }
                else {
                    priorityQueue.insert(candidate);
                    enqueues++;
                }

            }

        }

        return foundWord;

    }


    private void printBanner(String start, String end, WordInfoPriority foundLadder) {
        String history = foundLadder.getHistory();
        int enqueues = foundLadder.getEnqueues();

        System.out.printf("Seeking A* solution from %s -> %s \n", start, end);
        System.out.printf("[%s] total enqueues %d\n", history, enqueues);
        System.out.println();



    }


}
