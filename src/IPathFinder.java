import java.util.ArrayList;

public interface IPathFinder {

    void findPaths(ArrayList<Branch>[] signalFlowGraph);

    ArrayList<ArrayList<Integer>> getForwardPaths();

    ArrayList<ArrayList<Integer>> getLoops();
}
