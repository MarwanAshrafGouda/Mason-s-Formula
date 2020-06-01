import java.util.ArrayList;

public class GainCalculator implements IGainCalculator {

    @Override
    public ArrayList<ArrayList<ArrayList<Integer>>> getNonTouchingLoops(ArrayList<ArrayList<Integer>> loops) {
        ArrayList<ArrayList<ArrayList<Integer>>> nonTouchingLoops = new ArrayList<>();
        for(int i=2; true; i++){
            ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
            
            break;
        }
        return null;
    }

    private boolean isTouching(ArrayList<Integer> loop1, ArrayList<Integer> loop2) {
        for(int node : loop1){
            if(loop2.contains(node))
                return true;
        }
        return false;
    }

    @Override
    public ArrayList<Double> getForwardGain(ArrayList<ArrayList<Integer>> forwardPaths, ArrayList<Branch>[] signalFlowGraph) {
        return getPathGain(forwardPaths, signalFlowGraph);
    }

    @Override
    public ArrayList<Double> getLoopGain(ArrayList<ArrayList<Integer>> loops, ArrayList<Branch>[] signalFlowGraph) {
        return getPathGain(loops, signalFlowGraph);
    }

    private ArrayList<Double> getPathGain(ArrayList<ArrayList<Integer>> paths, ArrayList<Branch>[] signalFlowGraph) {
        ArrayList<Double> gain = new ArrayList<>();
        for (ArrayList<Integer> path : paths) {
            gain.add(0.0);
            for (int i = 0; i < path.size() - 1; i++) {
                int startingPoint = path.get(i);
                int endPoint = path.get(i + 1);
                for (Branch branch : signalFlowGraph[startingPoint]) {
                    if (branch.getEndNode() == endPoint)
                        gain.set(gain.size() - 1, gain.get(gain.size() - 1) + branch.getGain());
                }
            }
        }
        return gain;
    }

    @Override
    public ArrayList<Integer> getDeltas() {
        return null;
    }

    @Override
    public double getGain(ArrayList<ArrayList<Integer>> forwardPaths, ArrayList<ArrayList<Integer>> loops, ArrayList<Branch>[] signalFlowGraph) {
        getForwardGain(forwardPaths, signalFlowGraph);
        getLoopGain(loops, signalFlowGraph);
        for (int i = 0; i < forwardPaths.size(); i++) {

        }
        return 0;
    }
}
