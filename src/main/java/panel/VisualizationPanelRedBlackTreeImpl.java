package panel;


import datastructures.BinaryTree;
import datastructures.RedBlackTree;
import dtos.Node;

import javax.swing.*;
import java.awt.*;

public class VisualizationPanelRedBlackTreeImpl extends JPanel {
    private RedBlackTree redBlackTree;

    public VisualizationPanelRedBlackTreeImpl(RedBlackTree tree)
    {
        this.redBlackTree = tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (redBlackTree != null && redBlackTree != null) {
            drawNode(g, redBlackTree.rootNode, getWidth() / 5, 50);
        }
    }

    public void colorNodeWithDelayPreOrder(Node root){
        if(root == null)
        {
            return;
        }
        //color current location
        Graphics g = getGraphics();
        g.setColor(Color.RED);
        g.fillOval(root.getxLoc(),root.getyLoc(),root.getnWidth(),root.getnHeight());
        g.setColor(Color.WHITE);
        g.drawOval(root.getxLoc(),root.getyLoc(),root.getnWidth(),root.getnHeight());

        // Draw the label at the center of the node
        g.setColor(Color.BLACK);
        String label = root.getUser().getId().toString();
        FontMetrics fm = g.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        int labelHeight = fm.getHeight();
        g.drawString(label, root.getCurrentNodeX() - labelWidth / 2, root.getyLoc() + labelHeight / 2);
        try{
            Thread.sleep(1000);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        colorNodeWithDelayInOrder(root.getLeftNode());
        colorNodeWithDelayInOrder(root.getRightNode());
    }
    public void colorNodeWithDelayInOrder(Node root){
        if(root == null)
        {
            return;
        }
        colorNodeWithDelayInOrder(root.getLeftNode());
        //color current location
        Graphics g = getGraphics();
        g.setColor(Color.RED);
        g.fillOval(root.getxLoc(),root.getyLoc(),root.getnWidth(),root.getnHeight());
        g.setColor(Color.WHITE);
        g.drawOval(root.getxLoc(),root.getyLoc(),root.getnWidth(),root.getnHeight());

        // Draw the label at the center of the node
        g.setColor(Color.BLACK);
        String label = root.getUser().getId().toString();
        FontMetrics fm = g.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        int labelHeight = fm.getHeight();
        g.drawString(label, root.getCurrentNodeX() - labelWidth / 2, root.getyLoc() + labelHeight / 2);
        try{
            Thread.sleep(1000);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        colorNodeWithDelayInOrder(root.getRightNode());
    }


    private int calculateWidth(Node node) {
        if (node == null) {
            return 0;
        }
        int leftWidth = calculateWidth(node.getLeftNode());
        int rightWidth = calculateWidth(node.getRightNode());
        return leftWidth + rightWidth + 50; // Width of the current node
    }

    private int drawNode(Graphics g, Node node, int x, int y) {
        if (node == null) {
            return 0;
        }

        // Calculate the width of the left subtree
        int leftWidth = drawNode(g, node.getLeftNode(), x, y + 100);

        // Calculate the current node's x-coordinate
        int nodeWidth = 50;
        int currentNodeX = x + leftWidth;

        // Draw the current node
        int nodeHeight = 30;

        if(node.getBlack()) {
            g.setColor(Color.BLACK);
        }
        else{
            g.setColor(Color.RED);
        }
        g.fillOval(currentNodeX - nodeWidth / 2, y, nodeWidth, nodeHeight);
        g.setColor(Color.WHITE);
        g.drawOval(currentNodeX - nodeWidth / 2, y, nodeWidth, nodeHeight);

        // Draw the label at the center of the node
        g.setColor(Color.WHITE);
        String label = node.getUser().getId().toString();
        FontMetrics fm = g.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        int labelHeight = fm.getHeight();
        g.drawString(label, currentNodeX - labelWidth / 2, y + labelHeight / 2);

        node.setxLoc(currentNodeX - nodeWidth / 2);
        node.setyLoc(y);
        node.setnWidth(nodeWidth);
        node.setnHeight(nodeHeight);
        node.setCurrentNodeX(currentNodeX);


        // Draw lines connecting parent to left and right child nodes
        if (node.getLeftNode() != null) {
            // Calculate the x-coordinate of the center of the left child node
            int leftChildX = x + leftWidth-nodeWidth;
            // Calculate the y-coordinate of the center of the left child node
            int leftChildY = y + 100;
            // Draw line from parent to left child
            g.setColor(Color.WHITE);
            g.drawLine(currentNodeX, y + nodeHeight / 2, leftChildX, leftChildY - nodeHeight / 2);
        }
        if (node.getRightNode() != null) {
            // Calculate the x-coordinate of the center of the right child node
            int rightChildX = x + leftWidth + nodeWidth;
            // Calculate the y-coordinate of the center of the right child node
            int rightChildY = y + 100;
            // Draw line from parent to right child
            g.setColor(Color.WHITE);
            g.drawLine(currentNodeX, y + nodeHeight / 2, rightChildX, rightChildY - nodeHeight / 2);
        }

        // Calculate the width of the right subtree
        int rightWidth = drawNode(g, node.getRightNode(), x + leftWidth + nodeWidth, y + 100);

        // Return the total width of the subtree rooted at the current node
        return leftWidth + nodeWidth + rightWidth;
    }
}
