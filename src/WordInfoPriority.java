public class WordInfoPriority implements Comparable<WordInfoPriority> {
    private String word;
    private int moves;
    private String history;
    private int enqueues;
    private int priority;



    public WordInfoPriority(String word, int moves, int estimatedWork) {
        this.word = word;
        this.moves = moves;
        this.priority = estimatedWork;
        this.history = "";
        this.enqueues = 0;

    }

    public WordInfoPriority(String word, int moves, int estimatedWork, String history, int enqueues) {
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
    public int compareTo(WordInfoPriority o) {
        if (this.priority < o.priority) {
            return -1;
        }
        if (this.priority > o.priority) {
            return 1;
        }else {
            return this.word.compareTo(o.word);
        }
    }
}
