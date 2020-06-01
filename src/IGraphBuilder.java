import java.util.ArrayList;

public interface IGraphBuilder {

    ArrayList<Branch>[] build(int nodeCount, ArrayList<Branch> branches);
}
