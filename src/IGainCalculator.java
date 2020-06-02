import java.util.ArrayList;

public interface IGainCalculator {

    ArrayList<ArrayList<ArrayList<Integer>>> getNonTouchingLoops(ArrayList<ArrayList<Integer>> loops); //where n is the number of non-touching loops

    ArrayList<Double> getForwardGain(ArrayList<ArrayList<Integer>> forwardPaths, ArrayList<Branch>[] signalFlowGraph);

    ArrayList<Double> getLoopGain(ArrayList<ArrayList<Integer>> loops, ArrayList<Branch>[] signalFlowGraph);

    double getDelta(ArrayList<ArrayList<Integer>> loops, ArrayList<ArrayList<ArrayList<Integer>>> nonTouchingLoops);

    double getGain(ArrayList<ArrayList<Integer>> forwardPaths, ArrayList<ArrayList<Integer>> loops, ArrayList<Branch>[] signalFlowGraph);
}
