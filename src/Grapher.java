import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Scanner;

public class Grapher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Digraph<String, String> g = new DigraphEdgeList<>();
        Processor processor = new Processor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Node Count");
        processor.setNodeCount(Integer.parseInt(scanner.nextLine()));
        for (int i = 0; i < processor.getNodeCount(); i++)
            g.insertVertex(Integer.toString(i));
        ArrayList<Branch> branches = new ArrayList<>();
        System.out.println("Enter Branches in the Form: StartingNode EndNode Gain,\nWhere StartingNode, EndNode, " +
                "and Gain are Numerical Values\nThe Source Node is 0\nEnter -1 to End Input");
        ArrayList<String> gains = new ArrayList<>();
        while (true) {
            String nextLine = scanner.nextLine();
            if (nextLine.equals("-1"))
                break;
            String[] parts = nextLine.split("\\s+");
            String gain = parts[2];
            while (gains.contains(gain)) {
                gain += " ";
            }
            gains.add(gain);
            g.insertEdge(parts[0], parts[1], gain);
            branches.add(new Branch(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }
        processor.setBranches(branches);
        ArrayList<ArrayList<Integer>> forwardPaths = processor.getForwardPaths();
        System.out.println("Forward Paths:");
        for (int i = 0; i < forwardPaths.size(); i++) {
            ArrayList<Integer> forwardPath = forwardPaths.get(i);
            System.out.print("\tPath " + (i + 1) + ": ");
            for (int node : forwardPath)
                System.out.print(node + " ");
            System.out.println();
        }
        ArrayList<ArrayList<Integer>> loops = processor.getLoops();
        System.out.println("Loops:");
        for (int i = 0; i < loops.size(); i++) {
            ArrayList<Integer> loop = loops.get(i);
            System.out.print("\tLoop " + (i + 1) + ": ");
            for (int node : loop)
                System.out.print(node + " ");
            System.out.println();
        }
        ArrayList<ArrayList<ArrayList<Integer>>> nonTouchingLoops = processor.getNonTouchingLoops();
        System.out.println("Non Touching Loops:");
        for (int i = 0; i < nonTouchingLoops.size(); i++) {
            ArrayList<ArrayList<Integer>> nonTouchingLoopGroup = nonTouchingLoops.get(i);
            System.out.print("\tGroup " + (i + 1) + ": ");
            for (ArrayList<Integer> loop : nonTouchingLoopGroup) {
                System.out.print("( ");
                for (int node : loop)
                    System.out.print(node + " ");
                System.out.print(")");
            }
            System.out.println();
        }
        System.out.println("Overall Gain: " + processor.getGain());
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
        Scene scene = new Scene(graphView, 1024, 768);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFXGraph Visualization");
        stage.setScene(scene);
        stage.show();
        graphView.init();
    }
}
