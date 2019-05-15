/**
 * This program represents the following equation in a TensorFlow graph
 * and uses TensorFlow to compute its z value by providing x, y inputs for
 * a predefined set of a, b and c constants.  In TensorFlow terminology,
 * this is a "trained" model, each node in a TensorFlow graph represents
 * and operation and each edge in the graph, called Tensor, provides input
 * to the next node
 *
 * z = a*x + b*y + c
 *
 */
import java.util.Scanner;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

public class MyTensorFlowGraph {
    private static final int a_VALUE = 2;
    private static final int b_VALUE = 3;
    private static final int c_VALUE = 1;

    public static Graph createGraph() {
        Graph graph = new Graph();
        //define constants 'a' node, its value input is a Tensor
        Operation a = graph.opBuilder("Const", "a")
                           .setAttr("dtype", DataType.fromClass(Integer.class))
                           .setAttr("value", Tensor.<Integer>create(a_VALUE, Integer.class))
                           .build();
        //define constants 'b' node, its value input is a Tensor
        Operation b = graph.opBuilder("Const", "b")
                           .setAttr("dtype", DataType.fromClass(Integer.class))
                           .setAttr("value", Tensor.<Integer>create(b_VALUE, Integer.class))
                           .build();
        //define constants 'c' node, its value input is a Tensor
        Operation c = graph.opBuilder("Const", "c")
                           .setAttr("dtype", DataType.fromClass(Integer.class))
                           .setAttr("value", Tensor.<Integer>create(c_VALUE, Integer.class))
                           .build();

        //define place holder node for x
        Operation x = graph.opBuilder("Placeholder", "x")
                           .setAttr("dtype", DataType.fromClass(Integer.class))
                           .build();
        //define place holder node for y
        Operation y = graph.opBuilder("Placeholder", "y")
                           .setAttr("dtype", DataType.fromClass(Integer.class))
                           .build();
        //define a*x operation node
        Operation ax = graph.opBuilder("Mul", "ax")
                            .addInput(a.output(0))
                            .addInput(x.output(0))
                            .build();
        //define b*y operation node
        Operation by = graph.opBuilder("Mul", "by")
                            .addInput(b.output(0))
                            .addInput(y.output(0))
                            .build();
        //define a*x + b*y operation node 
        Operation axplusby = graph.opBuilder("Add", "axplusby")
                              .addInput(ax.output(0))
                              .addInput(by.output(0))
                              .build();
        //finally a*x + b*y + c operation node 
        graph.opBuilder("Add", "z")
             .addInput(axplusby.output(0))
             .addInput(c.output(0))
             .build();

        return graph;
    }

    /**
     * Run the graph with input x and y values
     *
     * @param graph the TensorFlow graph
     * @param x the x input value
     * @param y the y input value
     * @return the result of computation of the graph
     */
    public static Object runGraph(Graph graph, Integer x, Integer y) {
        Object result;

        //Create a session to run the graph
        try (Session sess = new Session(graph)) {
            result = sess.runner()
                         .fetch("z")
                         .feed("x", Tensor.<Integer>create(x, Integer.class))
                         .feed("y", Tensor.<Integer>create(y, Integer.class))
                         .run()
                         .get(0).expect(Integer.class)
                         .intValue();
        }
        return result;
    }

    /**
     * The entry class to run the program
     *
     * @param args none expected
     */
    public static void main(String[] args) {
        Graph graph = MyTensorFlowGraph.createGraph();
        while (true) {
            Scanner in = new Scanner (System.in);
            System.out.println ("");
            System.out.println ("Enter first integer value");
            System.out.println ("Enter 'q' quit");
            String response = in.nextLine();
            if (response.equalsIgnoreCase("q")) {
                System.out.println("Goodby");
                graph.close();
                System.exit(0);
            }
            int x, y;
            try {
                 x = Integer.valueOf(response);
            } catch (NumberFormatException e) {
                System.out.println("The value you entered is not integer");
                System.out.println("Start over ..");
                continue;
            }
            System.out.println ("Enter second integer value:");
            response = in.nextLine();
            if (response.equalsIgnoreCase("q")) {
                System.out.println("Goodby");
                graph.close();
                System.exit(0);
            }
            try {
                y = Integer.valueOf(response);
            } catch (NumberFormatException e) {
                System.out.println("The value you entered is not an integer");
                System.out.println("Start over ..");
                continue;
            }
            System.out.println("COMPUTE: "+a_VALUE+"*x+"+b_VALUE+"*y+"+c_VALUE);
            System.out.println ("");
            System.out.println ("RESULT:");
            System.out.println("where x="+x+", y="+y);
            System.out.println ("");
            Object result = MyTensorFlowGraph.runGraph(graph, x, y);
            System.out.println ("");
            System.out.println(a_VALUE+"*"+x+"+"+b_VALUE+"*"+y+"+"+c_VALUE+"="+result);
        }
    }
}
