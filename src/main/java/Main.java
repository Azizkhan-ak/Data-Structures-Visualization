import datastructures.UnWeightedGraph;
import datastructures.WeightedGraph;
import dtos.Vertex;
import listeners.TreeListener;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main {

    private static String backGrounImage = "mainFrameBackgound.png";

    public static void main(String[] args)
    {


//        WeightedGraph graph = new WeightedGraph();
//
//        Vertex vertex = new Vertex();
//        vertex.setCity(1l);
//        graph.addVertex(vertex);
//
//        Vertex vertex1 = new Vertex();
//        vertex1.setCity(2l);
//        graph.addVertex(vertex1);
//
//        Vertex vertex2 = new Vertex();
//        vertex2.setCity(3l);
//        graph.addVertex(vertex2);
//
//        Vertex vertex3 = new Vertex();
//        vertex3.setCity(5l);
//        graph.addVertex(vertex3);
//
//        Vertex vertex4 = new Vertex();
//        vertex4.setCity(4l);
//        graph.addVertex(vertex4);
//
//        graph.addEdges(1l,2l,10);
//        graph.addEdges(3l,2l,20);
//        graph.addEdges(4l,5l,3);
//        graph.addEdges(5l,1l,6);
//        graph.addEdges(1l,3l,9);
//        graph.addEdges(2l,4l,19);
//        graph.addEdges(3l,1l,7);
//
//        System.out.println("=================== Depth First Search ==================");
//        graph.dfs();
//        System.out.println("=================== Breadth First Search ==================");
//        graph.bfs();
//        graph.minimumSpanningTree();
//        System.out.println("sd");



        // Get the ClassLoader
        ClassLoader classLoader = Main.class.getClassLoader();
        URL imageURL = classLoader.getResource(backGrounImage);

        JFrame mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setSize(1216,806);
        mainFrame.setMinimumSize(new Dimension(800,600));
        mainFrame.setResizable(true);
        mainFrame.setEnabled(true);
        mainFrame.setName("Data Structures Visualization");
        mainFrame.setTitle("Data Structures Visualization");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = mainFrame.getContentPane();
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(new ImageIcon(imageURL).getImage(), 0, 0, getWidth(), getHeight(), this);
                // Add a semi-transparent overlay
                g.setColor(new Color(0, 0, 0, 200)); // Black color with 100 (out of 255) transparency
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        //Now we add buttons for different data structures
        JButton tree = new JButton("Tree");
        tree.setForeground(Color.WHITE);
        tree.setEnabled(true);
        tree.setVisible(true);
        tree.setBorderPainted(true);
        tree.setContentAreaFilled(false);
        tree.setSize(150,50);
        tree.setOpaque(false);
        tree.setLocation(100,300);
        contentPane.add(tree);

        JButton linkedList = new JButton("Linked List");
        linkedList.setForeground(Color.WHITE);
        linkedList.setEnabled(true);
        linkedList.setVisible(true);
        linkedList.setBorderPainted(true);
        linkedList.setContentAreaFilled(false);
        linkedList.setSize(150,50);
        linkedList.setOpaque(false);
        linkedList.setLocation(100,350);
        contentPane.add(linkedList);

        JButton stack = new JButton("Stack");
        stack.setForeground(Color.WHITE);
        stack.setEnabled(true);
        stack.setVisible(true);
        stack.setBorderPainted(true);
        stack.setContentAreaFilled(false);
        stack.setSize(150,50);
        stack.setOpaque(false);
        stack.setLocation(100,400);
        contentPane.add(stack);

        JButton queue = new JButton("Queue");
        queue.setForeground(Color.WHITE);
        queue.setEnabled(true);
        queue.setVisible(true);
        queue.setBorderPainted(true);
        queue.setContentAreaFilled(false);
        queue.setSize(150,50);
        queue.setOpaque(false);
        queue.setLocation(100,450);
        contentPane.add(queue);

        JButton graph = new JButton("Graph");
        graph.setForeground(Color.WHITE);
        graph.setEnabled(true);
        graph.setVisible(true);
        graph.setBorderPainted(true);
        graph.setContentAreaFilled(false);
        graph.setSize(150,50);
        graph.setOpaque(false);
        graph.setLocation(100,500);
        contentPane.add(graph);

        //add listerens
        tree.addMouseListener(new TreeListener(mainFrame,tree));

        imagePanel.setLayout(new BorderLayout());
        contentPane.add(imagePanel);
        mainFrame.setContentPane(contentPane);

    }
}
