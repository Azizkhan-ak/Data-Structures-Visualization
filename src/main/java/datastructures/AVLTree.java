package datastructures;

import dtos.Node;
import dtos.UserDto;

public class AVLTree {
    Node root;

    public void insertIntoAVLTree(UserDto userDto) {

        Boolean isInserted = false;
        Node newNode = null;
        if (root == null) {
            newNode = new Node();
            newNode.setUser(userDto);
            this.root = newNode;
            isInserted = true;
        } else {
            Node tempRoot = root;
            Node parentNode = tempRoot;
            while (tempRoot != null) {
                if (tempRoot.getUser().getId() < userDto.getId()) {
                    if (tempRoot.getLeftNode() == null) {
                        newNode = new Node();
                        newNode.setUser(userDto);
                        newNode.setParentNode(parentNode);
                        tempRoot.setLeftNode(newNode);
                        tempRoot.setLeftChild(true);
                        isInserted = true;
                    } else {
                        parentNode = tempRoot;
                        tempRoot = tempRoot.getLeftNode();
                    }
                } else if (tempRoot.getUser().getId() > userDto.getId()) {
                    if (tempRoot.getRightNode() == null) {
                        newNode = new Node();
                        newNode.setUser(userDto);
                        newNode.setParentNode(parentNode);
                        tempRoot.setRightNode(newNode);
                        tempRoot.setLeftChild(false);
                        isInserted = true;
                    } else {
                        parentNode = tempRoot;
                        tempRoot = tempRoot.getRightNode();
                    }
                } else {
                    // key already exists
                    return;
                }
            }
        }

        //if insertion is done we will need to put balance factor onto everynode again
        if(isInserted){
            Boolean isTreeBalanced = false;
           outer: while (!isTreeBalanced) {
                rePopulateBalanceFactor(newNode);
                while (newNode != null) {
                    if (newNode.getBalanceFactor() > 1 || newNode.getBalanceFactor() < -1) {
                        //rotations required
                        Node child = newNode;
                        Node parent = newNode.getParentNode();
                        Node grandParent = parent.getParentNode();

                        if(child.getLeftChild() && parent.getLeftChild()){
                            //RR Rotation
                            Node greatGrandParent = grandParent.getParentNode();
                            greatGrandParent.setLeftNode(parent);
                            grandParent.setLeftNode(parent.getRightNode());
                            parent.setRightNode(grandParent);
                        }
                        else if(!child.getLeftChild() && !parent.getLeftChild())
                        {
                            //LL Rotation
                            Node greatGrandParent = parent.getParentNode();
                            greatGrandParent.setRightNode(parent);
                            grandParent.setRightNode(parent.getLeftNode());
                            parent.setLeftNode(grandParent);
                        }
                        else if (!child.getLeftChild() && parent.getLeftChild()){
                            //LL and RR Rotation
                            //first rotation
                            parent.setRightNode(child.getLeftNode());
                            child.setLeftNode(parent);
                            grandParent.setLeftNode(child);

                            //second rotation
                            Node tempNode = parent;
                            parent = child;
                            child = tempNode;

                            Node greatGrandParent = grandParent.getParentNode();
                            greatGrandParent.setLeftNode(parent);
                            grandParent.setLeftNode(parent.getRightNode());
                            parent.setRightNode(grandParent);

                        }
                        else if(child.getLeftChild() && !parent.getLeftChild()){
                            //RR and LL Rotation
                            //first rotation
                            parent.setLeftNode(child.getRightNode());
                            child.setRightNode(parent);
                            grandParent.setRightNode(child);
                            //second rotation
                            Node tempNode = parent;
                            parent = child;
                            child = tempNode;

                            Node greatGrandParent = parent.getParentNode();
                            greatGrandParent.setRightNode(parent);
                            grandParent.setRightNode(parent.getLeftNode());
                            parent.setLeftNode(grandParent);

                        }
                        // continue to outer loop again
                        continue outer;
                    }
                    newNode = newNode.getParentNode();
                }
                isTreeBalanced = true;
            }
        }
    }

        public void rePopulateBalanceFactor(Node node){
        if(node == null){
            return;
        }
        node.setBalanceFactor(calculateHeightOfSubTreeMax(node.getLeftNode()) - calculateHeightOfSubTreeMax(node.getRightNode()));
        rePopulateBalanceFactor(node.getLeftNode());
        rePopulateBalanceFactor(node.getRightNode());
    }

//    public void rePopulateBalanceFactor(Node node){
//        while (node!=null)
//        {
//            node.setBalanceFactor(calculateHeightOfSubTreeMax(node.getLeftNode()) - calculateHeightOfSubTreeMax(node.getRightNode()));
//            node = node.getParentNode();
//        }
//    }

    public int calculateHeightOfSubTreeMax(Node node){

        if(node == null){
            return 0;
        }
        int left = calculateHeightOfSubTreeMax(node.getLeftNode())+1;
        int right = calculateHeightOfSubTreeMax(node.getRightNode())+1;
        return Math.max(left,right)+1;
    }
}
