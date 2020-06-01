public class Branch {

    private int startingNode;
    private int endNode;
    private int gain;

    public Branch(int startingNode, int endNode, int gain) {
        this.startingNode = startingNode;
        this.endNode = endNode;
        this.gain = gain;
    }

    public int getStartingNode() {
        return startingNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public int getGain() {
        return gain;
    }
}
