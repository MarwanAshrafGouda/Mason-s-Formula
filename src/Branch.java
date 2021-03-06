public class Branch {

    private final int startingNode;
    private final int endNode;
    private final double gain;

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

    public double getGain() {
        return gain;
    }
}
