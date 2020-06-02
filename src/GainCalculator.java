import java.util.ArrayList;

public class GainCalculator implements IGainCalculator {

    private ArrayList<Branch>[] signalFlowGraph;

    @Override
    public ArrayList<ArrayList<ArrayList<Integer>>> getNonTouchingLoops(ArrayList<ArrayList<Integer>> loops) {
        ArrayList<ArrayList<ArrayList<Integer>>> nonTouchingLoops = new ArrayList<>();
        nonTouchingLoopsRecursive(nonTouchingLoops, loops, new ArrayList<>(), 0);
        for (int n = 0; n < nonTouchingLoops.size(); n++) {
            boolean flag = false;
            ArrayList<ArrayList<Integer>> nonTouchingLoop = nonTouchingLoops.get(n);
            for (int i = 0; i < nonTouchingLoop.size(); i++) {
                for (int j = i + 1; j < nonTouchingLoop.size(); j++) {
                    if (isTouching(nonTouchingLoop.get(i), nonTouchingLoop.get(j))) {
                        nonTouchingLoops.remove(nonTouchingLoop);
                        n--;
                        flag = true;
                        break;
                    }
                }
                if (flag) break;
            }
        }
        return nonTouchingLoops;
    }

    private void nonTouchingLoopsRecursive(ArrayList<ArrayList<ArrayList<Integer>>> nonTouchingLoops, ArrayList<ArrayList<Integer>> loops, ArrayList<ArrayList<Integer>> temp, int index) {
        if (index == loops.size()) {
            if (temp.size() >= 2)
                nonTouchingLoops.add(temp);
            return;
        }
        nonTouchingLoopsRecursive(nonTouchingLoops, loops, temp, index + 1);
        ArrayList<ArrayList<Integer>> copy = new ArrayList<>(temp);
        copy.add(loops.get(index));
        nonTouchingLoopsRecursive(nonTouchingLoops, loops, copy, index + 1);
    }

    private boolean isTouching(ArrayList<Integer> path1, ArrayList<Integer> path2) {
        for (int node : path1) {
            if (path2.contains(node))
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
        this.signalFlowGraph = signalFlowGraph;
        ArrayList<Double> gain = new ArrayList<>();
        for (ArrayList<Integer> path : paths) {
            gain.add(1.0);
            for (int i = 0; i < path.size() - 1; i++) {
                int startingPoint = path.get(i);
                int endPoint = path.get(i + 1);
                for (Branch branch : signalFlowGraph[startingPoint]) {
                    if (branch.getEndNode() == endPoint)
                        gain.set(gain.size() - 1, gain.get(gain.size() - 1) * branch.getGain());
                }
            }
        }
        return gain;
    }

    @Override
    public double getDelta(ArrayList<ArrayList<Integer>> loops, ArrayList<ArrayList<ArrayList<Integer>>> nonTouchingLoops) {
        double delta = 1;
        ArrayList<Double> loopGains = getLoopGain(loops, signalFlowGraph);
        for (Double loopGain : loopGains) {
            delta -= loopGain;
        }
        for (ArrayList<ArrayList<Integer>> nonTouchingLoop : nonTouchingLoops) {
            double product = 1;
            ArrayList<Double> nonTouchingLoopGains = getLoopGain(nonTouchingLoop, signalFlowGraph);
            for (Double nonTouchingLoopGain : nonTouchingLoopGains) {
                product *= nonTouchingLoopGain;
            }
            delta += product * Math.pow(-1, nonTouchingLoop.size());
        }
        return delta;
    }

    @Override
    public double getGain(ArrayList<ArrayList<Integer>> forwardPaths, ArrayList<ArrayList<Integer>> loops, ArrayList<Branch>[] signalFlowGraph) {
        double gain = 0;
        ArrayList<Double> forwardGains = getForwardGain(forwardPaths, signalFlowGraph);
        double delta = getDelta(loops, getNonTouchingLoops(loops));
        for (int i = 0; i < forwardPaths.size(); i++) {
            double term = forwardGains.get(i);
            term *= getDelta(getSeparateLoops(loops, forwardPaths.get(i)), getNonTouchingLoops(getSeparateLoops(loops, forwardPaths.get(i))));
            gain += term;
        }
        gain /= delta;
        return gain;
    }

    private ArrayList<ArrayList<Integer>> getSeparateLoops(ArrayList<ArrayList<Integer>> loops, ArrayList<Integer> forwardPaths) {
        ArrayList<ArrayList<Integer>> separateLoops = new ArrayList<>();
        for (ArrayList<Integer> loop : loops) {
            if (!isTouching(loop, forwardPaths))
                separateLoops.add(loop);
        }
        return separateLoops;
    }
}
