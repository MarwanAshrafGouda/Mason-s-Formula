import java.util.ArrayList;
import java.util.Scanner;

public class Processor implements IProcessor {

    private int nodeCount;
    private ArrayList<Branch> branches;
    private final IPathFinder pathFinder = new PathFinder();
    private final IGraphBuilder graphBuilder = new GraphBuilder();
    private final IGainCalculator gainCalculator = new GainCalculator();
    ArrayList<Branch>[] signalFlowGraph = new ArrayList[nodeCount];

    public static void main(String[] args) {
        Processor processor = new Processor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Node Count");
        processor.setNodeCount(Integer.parseInt(scanner.nextLine()));
        ArrayList<Branch> branches = new ArrayList<>();
        while (true) {
            String nextLine = scanner.nextLine();
            if (nextLine.equals("-1"))
                break;
            String[] parts = nextLine.split("\\s");
            branches.add(new Branch(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }
        processor.setBranches(branches);
        System.out.println(processor.getGain());
    }

    @Override
    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
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
