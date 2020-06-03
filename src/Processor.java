import java.util.ArrayList;

public class Processor implements IProcessor {

    private int nodeCount;
    private ArrayList<Branch> branches;
    private final IPathFinder pathFinder = new PathFinder();
    private final IGraphBuilder graphBuilder = new GraphBuilder();
    private final IGainCalculator gainCalculator = new GainCalculator();
    ArrayList<Branch>[] signalFlowGraph = new ArrayList[nodeCount];

    @Override
    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
        buildGraph();
    }

    private void buildGraph() {
        this.signalFlowGraph = graphBuilder.build(nodeCount, branches);
        pathFinder.findPaths(signalFlowGraph);
    }

    @Override
    public ArrayList<ArrayList<Integer>> getForwardPaths() {
        return pathFinder.getForwardPaths();
    }

    @Override
    public ArrayList<ArrayList<Integer>> getLoops() {
        return pathFinder.getLoops();
    }

    @Override
    public ArrayList<ArrayList<ArrayList<Integer>>> getNonTouchingLoops() {
        return gainCalculator.getNonTouchingLoops(getLoops());
    }

    @Override
    public double getGain() {
        new GainCalculator().getNonTouchingLoops(getLoops());
        return gainCalculator.getGain(getForwardPaths(), getLoops(), signalFlowGraph);
    }
}
