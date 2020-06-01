import java.util.ArrayList;

public class GraphBuilder implements IGraphBuilder {
    @Override
    public ArrayList<Branch>[] build(int nodeCount, ArrayList<Branch> branches) {

        ArrayList<Branch>[] signalFlowGraph = new ArrayList[nodeCount];
        for(Branch branch : branches){
            if(signalFlowGraph[branch.getStartingNode()] == null)
                signalFlowGraph[branch.getStartingNode()] = new ArrayList<>();
            signalFlowGraph[branch.getStartingNode()].add(branch);
        }
        return signalFlowGraph;
    }
}
