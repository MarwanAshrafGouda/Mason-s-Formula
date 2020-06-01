import java.util.ArrayList;

public interface IProcessor {

    void setNodeCount(int nodeCount);

    void setBranches(ArrayList<Branch> branches);

    ArrayList<ArrayList<Integer>> getForwardPaths();

    ArrayList<ArrayList<Integer>> getLoops();

    ArrayList<ArrayList<Integer>> getNonTouchingLoops(int n); //where n is the number of non-touching loops

    ArrayList<Integer> getDeltas();

    int getGain();
}
