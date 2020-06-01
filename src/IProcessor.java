import java.util.ArrayList;

public interface IProcessor {

    void setNodeCount(int nodeCount);

    void setBranches(ArrayList<Branch> branches);

    ArrayList<ArrayList<Integer>> getForwardPaths();

    ArrayList<ArrayList<Integer>> getLoops();

    ArrayList<ArrayList<ArrayList<Integer>>> getNonTouchingLoops();

    ArrayList<Integer> getDeltas();

    double getGain();
}
