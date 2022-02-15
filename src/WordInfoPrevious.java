public class WordInfoPrevious implements Comparable<WordInfoPrevious> {
    private String word;
    private int moves;
    private String history;
    private int enqueues;
    private int priority;



    public WordInfoPrevious(String word, int moves, int estimatedWork) {
        this.word = word;
        this.moves = moves;
        this.priority = estimatedWork;
        this.history = "";
        this.enqueues = 0;

    }

    public WordInfoPrevious(String word, int moves, int estimatedWork, String history, int enqueues) {
        this.word = word;
        this.moves = moves;
        this.priority = estimatedWork;
        this.history = history;
        this.enqueues = enqueues;

    }

    public String getWord() {
        return this.word;
    }

    public int getMoves() {
        return this.moves;
    }

    public String getHistory() {
        return this.history;
    }

    public int getEnqueues() {
        return this.enqueues;
    }

    public int getEstimatedWork() {
        return this.priority;
    }

    @Override
    public int compareTo(WordInfoPrevious o){
        return this.word.compareTo(o.word);
    }


    @Override
    public String toString() {
        return this.word;
    }

    public WordInfoPriority toPriority () {
        WordInfoPriority priority = new WordInfoPriority(
                this.word,
                this.moves,
                this.priority,
                this.history,
                this.enqueues
        );

        return priority;
    }
}