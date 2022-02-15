public class WordInfo {
    private String word;
    private int moves;
    private String history;
    private int enqueues;


    public WordInfo(String word, int moves, int enqueues) {
        this.word = word;
        this.moves = moves;
        this.history = word;
        this.enqueues = enqueues;

    }

    public WordInfo(String word, int moves, String history, int enqueues) {
        this.word = word;
        this.moves = moves;
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



    @Override
    public String toString() {
        return String.format("Word %s Moves %d : History[%s]",
                word, moves, history);
    }
}
