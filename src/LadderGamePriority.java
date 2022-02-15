import java.util.ArrayList;

public class LadderGamePriority extends LadderGame {

    public LadderGamePriority(String dictionaryFile) {
        super(dictionaryFile);
    }

    @Override
    public void play(String start, String end) {
        // need to make a priority queue which is the AVL tree
        AVLTree<WordInfoPriority> priorityQueue = new AVLTree<WordInfoPriority>();

        if (sameSize(start, end) && inDictionary(listOfWordLists, start, end)) {
            // find the correct word ladder
            WordInfoPriority foundWordLadder = prioritySearch(priorityQueue, start, end);
            // now I need to print out everything I need
            printBanner(start, end, foundWordLadder);

        } else {
            System.out.println("No ladder was found");
        }

    }


    private WordInfoPriority prioritySearch(AVLTree priorityQueue, String start, String end) {
        priorityQueue.insert(new WordInfoPriority(start, 0, diff(start,end), start, 0));

        AVLTree<WordInfoPrevious> usedWords = new AVLTree<>();
        usedWords.insert(new WordInfoPrevious(start, 0, diff(start,end), start, 0 ));


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
                        currentWord.getHistory()+ " " + word,
                        enqueues
                );
                if (word.equals(end)) {
                    found = true;
                    foundWord = candidate;
                }
                if (usedWords.contains(candidate.toPrevious())) {
                    WordInfoPrevious existingWord = usedWords.find(candidate.toPrevious());
                    if (candidate.getEstimatedWork() < existingWord.getEstimatedWork()) {
                        usedWords.remove(existingWord);
                        usedWords.insert(candidate.toPrevious());
                        priorityQueue.insert(candidate);
                        enqueues++;
                    }
                }
                else {
                    priorityQueue.insert(candidate);
                    usedWords.insert(candidate.toPrevious());
                    enqueues++;
                }
            }
        }
        return foundWord;

    }


    private void printBanner(String start, String end, WordInfoPriority foundLadder) {
        String history = foundLadder.getHistory();
        int enqueues = foundLadder.getEnqueues();
        int moves = foundLadder.getMoves();
        System.out.printf("Seeking A* solution from %s -> %s \n", start, end);
        System.out.printf("[%s] total enqueues %d\n", history, enqueues);

    }


}
