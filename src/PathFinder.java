import java.util.ArrayList;
import java.util.Stack;

public class PathFinder implements IPathFinder {

    private final ArrayList<ArrayList<Integer>> forwardPaths = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> loops = new ArrayList<>();
    private final Stack<Integer> dfsStack = new Stack<>();
    private final Stack<ArrayList<Integer>> pathStack = new Stack<>();
    private ArrayList<Branch>[] signalFlowGraph;

    @Override
    public void findPaths(ArrayList<Branch>[] signalFlowGraph) {
        this.signalFlowGraph = signalFlowGraph;
        pathStack.push(new ArrayList<>());
        dfsStack.add(0);
        while (!dfsStack.empty()) {
            try {
                int beforeLast = pathStack.peek().get(pathStack.peek().size() - 1);
                pathStack.peek().add(dfsStack.peek());
                int last = pathStack.peek().get(pathStack.peek().size() - 1);
                if (last <= beforeLast) {
                    findLoops(pathStack.peek());
                    continue;
                }
            } catch (Exception e) {
                pathStack.peek().add(dfsStack.peek());
            }
            try {
                for (int i = 1; i < signalFlowGraph[dfsStack.peek()].size(); i++) {
                    ArrayList<Integer> temp = pathStack.peek();
                    pathStack.push(new ArrayList<>());
                    for (Integer integer : temp) pathStack.peek().add(integer);
                }
                int index = dfsStack.pop();
                for (Branch branch : signalFlowGraph[index])
                    dfsStack.push(branch.getEndNode());
            } catch (Exception e) {
                dfsStack.pop();
                forwardPaths.add(pathStack.pop());
            }
        }
    }

    private void findLoops(ArrayList<Integer> loop) {
        int loopStartIndex = loop.indexOf(loop.get(loop.size() - 1));
        int loopEndIndex = loop.lastIndexOf(loop.get(loop.size() - 1));
        if (loopStartIndex == loopEndIndex) {
            int index = dfsStack.pop();
            for (Branch branch : signalFlowGraph[index])
                if (index > branch.getEndNode())
                    dfsStack.push(branch.getEndNode());
            return;
        }
        if (loopStartIndex >= 0) {
            loop.subList(0, loopStartIndex).clear();
            if (!isDuplicate(loop))
                loops.add(loop);
            dfsStack.pop();
            pathStack.pop();
        }
    }

    private boolean isDuplicate(ArrayList<Integer> newLoop) {
        for (ArrayList<Integer> loop : loops) {
            if (loop.size() == newLoop.size()) {
                for (int i = 0; i < newLoop.size(); i++) {
                    if (!loop.get(i).equals(newLoop.get(i)))
                        break;
                    if (i == newLoop.size() - 1)
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getForwardPaths() {
        return forwardPaths;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getLoops() {
        return loops;
    }
}
