package datastructures;

/*
        Rules :
        1 -> Every node should either be black or red
        2 -> Root will always be black
        3 -> NULL childs are treated as black nodes
        4 -> Every path from root to leaf will have same number of blacks
        5 -> No two consecutive red nodes are allowed
        6 -> Newly inserted node will be red


       Balancing:
            -> If two consecutive nodes are red we :
                    -> Take 2 Nodes , parent & child :
                            -> If Uncle is red , we perform color flips
                                    -> Set Parent & Uncle to Black and GrandParent to Red.
                                    -> check if grandparent is root node if yes make it black.
                            -> If Uncle is black, we perform rotations and than color flips.
                                RR || RL || LL || LR
                                In this case we will get tree 3 elements rotated so make parent as black and childs are red

        so If This logic is implemteted correctly there should never be condition when
        number of blacks from root via different paths to leaf differ.

        Max height 2 logn , it is less stricted than AVL Trees


     */

import dtos.Node;
import dtos.UserDto;

public class RedBlackTree {

    public Node rootNode;

    private Integer numOfNodesInTree = 0;

    public Boolean insertIntoRedBlackTree(UserDto userDto)
    {
        Boolean isInserted = false;
        if(isExistInTree(userDto)){
            return isInserted;
        }

        if(rootNode == null)
        {
            rootNode = new Node();
            rootNode.setBlack(true);
            rootNode.setUser(userDto);
            numOfNodesInTree++;
            isInserted = true;
            return isInserted;
        }

        Node tempRoot = rootNode;
        Node parentNode = null;
        do{
            if( userDto.getId() < tempRoot.getUser().getId().longValue()){
                parentNode = tempRoot;
                if(tempRoot.getLeftNode() == null){
                    Node newNode = new Node();
                    newNode.setBlack(false);
                    newNode.setUser(userDto);
                    newNode.setLeftChild(true);
                    newNode.setParentNode(parentNode);
                    tempRoot.setLeftNode(newNode);
                    numOfNodesInTree++;
                    isInserted = true;
                    break;
                }
                else {
                    tempRoot = tempRoot.getLeftNode();
                }
            }
            else if(userDto.getId() > tempRoot.getUser().getId().longValue()){
                parentNode = tempRoot;
                if(tempRoot.getRightNode() == null){
                    Node newNode = new Node();
                    newNode.setBlack(false);
                    newNode.setUser(userDto);
                    newNode.setLeftChild(false);
                    newNode.setParentNode(parentNode);
                    tempRoot.setRightNode(newNode);
                    numOfNodesInTree++;
                    isInserted = true;
                    break;
                }
                else {
                    tempRoot = tempRoot.getRightNode();
                }
            }
        }while (tempRoot!=null);

        // so when insertion is completed we now need to balance tree based on the principels where mentioned at the start
        Boolean isTreeBalance = false;
       do{

           try{
               checkForTwoConsecutiveReds(rootNode);
               //means that exception is not thrown so tree is balanced
               isTreeBalance = true;

           }catch (TerminationException ex)
           {
               // if there are two reds consecutive this catch clause will catch an exception and will do balancing

               Node child =(Node) ex.getData();
               Node parent = child.getParentNode();
               Node grandParent = parent.getParentNode();
               Node uncle = parent.getLeftChild() ? grandParent.getRightNode():grandParent.getLeftNode();

               //since nulls are treated as Black Nodes
               if(uncle == null || uncle.getBlack()){
                   //means that rotation and than color flips
                    if(child.getLeftChild() && child.getParentNode().getLeftChild()){
                        // RR Rotation
                        grandParent.getParentNode().setLeftNode(parentNode);
                        //because this will become right child of new parent
                        Node parentRightNode = parentNode.getRightNode();
                        grandParent.setLeftChild(false);
                        parentNode.setRightNode(grandParent);
                        parentNode.getRightNode().setLeftNode(parentRightNode);

                        //since parent has become root now (grandparent in this case)
                        parentNode.setBlack(true);
                        //since grandparent and uncle has become chids of parent now so
                        parentNode.getLeftNode().setBlack(false);
                        parentNode.getRightNode().setBlack(false);
                    }
                    else if (!child.getLeftChild() && !child.getParentNode().getLeftChild()) {
                        //LL Rotation
                        grandParent.getParentNode().setRightNode(parentNode);
                        //because this will become left child of new parent
                        Node parentLeftNode = parentNode.getLeftNode();
                        grandParent.setLeftChild(false);
                        parentNode.setLeftNode(grandParent);
                        parentNode.getLeftNode().setRightNode(parentLeftNode);

                        //since parent has become root now (grandparent in this case)
                        parentNode.setBlack(true);
                        //since grandparent and uncle has become chids of parent now so
                        parentNode.getLeftNode().setBlack(false);
                        parentNode.getRightNode().setBlack(false);
                    }
                    else if(child.getLeftChild() && !child.getParentNode().getLeftChild()){
                        // First child and parent LL , than child , parent and Grandparent RR
                        Node newChild = parent;
                        newChild.setRightNode(child.getLeftNode());
                        newChild.getRightNode().setLeftChild(false);
                        newChild.setLeftChild(true);
                        Node newParent = child;
                        newParent.setLeftNode(newChild);
                        newParent.setLeftChild(true);
                        newParent.setRightNode(child.getLeftNode());
                        grandParent.setLeftNode(newParent);

                        parentNode = newParent;
                        child = newChild;
                        uncle = grandParent.getRightNode();

                        // one shift is completed no RR shift is requried
                        grandParent.getParentNode().setLeftNode(parentNode);
                        //because this will become right child of new parent
                        Node parentRightNode = parentNode.getRightNode();
                        grandParent.setLeftChild(false);
                        parentNode.setRightNode(grandParent);
                        parentNode.getRightNode().setLeftNode(parentRightNode);

                        //since parent has become root now (grandparent in this case)
                        parentNode.setBlack(true);
                        //since grandparent and uncle has become chids of parent now so
                        parentNode.getLeftNode().setBlack(false);
                        parentNode.getRightNode().setBlack(false);
                    }
                    else if(!child.getLeftChild() && child.getParentNode().getLeftChild()){
                        // First child and parent RR , than child , parent and Grandparent LL
                        Node newChild = parent;
                        newChild.setLeftNode(child.getRightNode());
                        newChild.getLeftNode().setLeftChild(true);
                        newChild.setLeftChild(false);
                        Node newParent = child;
                        newParent.setRightNode(newChild);
                        newParent.setLeftChild(false);
                        newParent.setLeftNode(child.getRightNode());
                        grandParent.setRightNode(newParent);

                        parentNode = newParent;
                        child = newChild;
                        uncle = grandParent.getLeftNode();

                        grandParent.getParentNode().setRightNode(parentNode);
                        //because this will become left child of new parent
                        Node parentLeftNode = parentNode.getLeftNode();
                        grandParent.setLeftChild(false);
                        parentNode.setLeftNode(grandParent);
                        parentNode.getLeftNode().setRightNode(parentLeftNode);

                        //since parent has become root now (grandparent in this case)
                        parentNode.setBlack(true);
                        //since grandparent and uncle has become chids of parent now so
                        parentNode.getLeftNode().setBlack(false);
                        parentNode.getRightNode().setBlack(false);
                    }
               }
               else{
                   //means that color flips required
                   parent.setBlack(true);
                   uncle.setBlack(true);
                   grandParent.setBlack(false);
                   //now check if grandparent is not root Node if yes, make it black since root is always black
                   if(rootNode == grandParent){
                       grandParent.setBlack(true);
                   }
               }
           }
       }while (!isTreeBalance);

       return isInserted;
    }

    public Boolean deleteFromBinaryTree(UserDto userDto)
    {
        Long userId = userDto.getId();
        Boolean result = false;
        try{
            if(this.rootNode==null)
            {
                return false;
            }
            Node tempParent = null;
            Node tempRoot = this.rootNode;
            while (tempRoot!=null)
            {
                Long currentUserId = tempRoot.getUser().getId();
                if(userId.longValue() < currentUserId.longValue())
                {
                    tempParent = tempRoot;
                    tempRoot = tempRoot.getLeftNode();
                }
                else if(userId.longValue() > currentUserId.longValue()) {
                    tempParent = tempRoot;
                    tempRoot = tempRoot.getRightNode();
                }
                else {
                    //means that Node is found
                    if (tempParent == null && this.rootNode.getLeftNode() == null && this.rootNode.getRightNode() == null) {
                        //means that to be deleted element is root node and tree only has root element
                        this.rootNode = null;
                        numOfNodesInTree--;
                        result = false;
                    }
                    else{
                        if (tempRoot.getLeftNode() == null && tempRoot.getRightNode() == null) {
                            //means that Node is leaf Node
                            if (userId.longValue() < tempParent.getUser().getId().longValue()) {
                                tempParent.setLeftNode(null);
                                result = true;
                                numOfNodesInTree--;
                            } else {
                                tempParent.setRightNode(null);
                                result = true;
                                numOfNodesInTree--;
                            }
                        } else if (tempRoot.getLeftNode() == null || tempRoot.getRightNode() == null) {
                            //means taht node has either left or right chold
                            if (tempRoot.getLeftNode() != null) {
                                if(tempParent == null)
                                {
                                    this.rootNode = tempRoot.getLeftNode();
                                    result = true;
                                    numOfNodesInTree--;
                                }
                                else {
                                    if (currentUserId.longValue() < tempParent.getUser().getId().longValue()) {
                                        tempParent.setLeftNode(tempRoot.getLeftNode());
                                        result = true;
                                        numOfNodesInTree--;
                                    } else {
                                        tempParent.setRightNode(tempRoot.getLeftNode());
                                        result = true;
                                        numOfNodesInTree--;
                                    }
                                }
                            } else {
                                if(tempParent == null) {
                                    this.rootNode = tempRoot.getRightNode();
                                    result = true;
                                    numOfNodesInTree--;
                                }
                                else {
                                    if (currentUserId.longValue() < tempParent.getUser().getId().longValue()) {
                                        tempParent.setLeftNode(tempRoot.getRightNode());
                                        result = true;
                                        numOfNodesInTree--;
                                    } else {
                                        tempParent.setRightNode(tempRoot.getRightNode());
                                        result = true;
                                        numOfNodesInTree--;
                                    }
                                }
                            }
                        } else {
                            //means that NodeToBeDeleted has both childs
                            if (tempRoot.getRightNode().getLeftNode() == null) {
                                //means that tempRoot.getRightNode() is successor and so will replace tempRoot
                                if (tempParent == null)
                                {
                                    this.rootNode = tempRoot.getRightNode();
                                    this.rootNode.setLeftNode(tempRoot.getLeftNode());
                                    this.rootNode.setRightNode(tempRoot.getRightNode().getRightNode());
                                    result = true;
                                    numOfNodesInTree--;
                                }
                                else {
                                    if (currentUserId.longValue() < tempParent.getUser().getId().longValue()) {
                                        tempParent.setLeftNode(tempRoot.getRightNode());
                                        tempParent.getLeftNode().setLeftNode(tempRoot.getLeftNode());
                                        result = true;
                                        numOfNodesInTree--;
                                    } else {
                                        tempParent.setRightNode(tempRoot.getRightNode());
                                        tempParent.getRightNode().setLeftNode(tempRoot.getLeftNode());
                                        result = true;
                                        numOfNodesInTree--;
                                    }
                                }
                            } else {
                                Node leftSubTreeToBeIterated = tempRoot.getRightNode();
                                Node leftMostSuccessor = null;
                                Node leftMostSuccessorParent = null;
                                while (leftSubTreeToBeIterated.getLeftNode() != null) {
                                    leftMostSuccessorParent = leftMostSuccessor;
                                    leftMostSuccessor = leftSubTreeToBeIterated.getLeftNode();
                                    leftSubTreeToBeIterated = leftSubTreeToBeIterated.getLeftNode();
                                }

                                //now we have two cases :
                                // if leftSubTreeToBeIterated has right child or not
                                if (leftMostSuccessor.getRightNode() == null) {
                                    if (tempParent == null) {
                                        leftMostSuccessor.setLeftNode(tempRoot.getLeftNode());
                                        leftMostSuccessor.setRightNode(tempRoot.getRightNode());
                                        leftMostSuccessorParent.setLeftNode(null);
                                        this.rootNode = leftMostSuccessor;
                                        result = true;
                                        numOfNodesInTree--;
                                    }
                                    else{
                                        if (currentUserId.longValue() < tempParent.getUser().getId().longValue()) {
                                            leftMostSuccessor.setLeftNode(tempRoot.getLeftNode());
                                            leftMostSuccessor.setRightNode(tempRoot.getRightNode());
                                            leftMostSuccessorParent.setLeftNode(null);
                                            tempParent.setLeftNode(leftMostSuccessor);
                                            result = true;
                                            numOfNodesInTree--;
                                        } else {
                                            leftMostSuccessor.setLeftNode(tempRoot.getLeftNode());
                                            leftMostSuccessor.setRightNode(tempRoot.getRightNode());
                                            leftMostSuccessorParent.setLeftNode(null);
                                            tempParent.setRightNode(leftMostSuccessor);
                                            result = true;
                                            numOfNodesInTree--;
                                        }
                                    }
                                } else {
                                    //means that there is right Node as wqel
                                    if(tempParent == null)
                                    {
                                        leftMostSuccessor.setLeftNode(tempRoot.getLeftNode());
                                        leftMostSuccessor.setRightNode(tempRoot.getRightNode());
                                        leftMostSuccessorParent.setLeftNode(leftMostSuccessor.getRightNode());
                                        this.rootNode = leftMostSuccessor;
                                        result = true;
                                        numOfNodesInTree--;
                                    }
                                    else {
                                        if (currentUserId.longValue() < tempParent.getUser().getId().longValue()) {
                                            leftMostSuccessor.setLeftNode(tempRoot.getLeftNode());
                                            leftMostSuccessor.setRightNode(tempRoot.getRightNode());
                                            leftMostSuccessorParent.setLeftNode(leftMostSuccessor.getRightNode());
                                            tempParent.setLeftNode(leftMostSuccessor);
                                            result = true;
                                            numOfNodesInTree--;
                                        } else {
                                            leftMostSuccessor.setLeftNode(tempRoot.getLeftNode());
                                            leftMostSuccessor.setRightNode(tempRoot.getRightNode());
                                            leftMostSuccessorParent.setLeftNode(leftMostSuccessor.getRightNode());
                                            tempParent.setRightNode(leftMostSuccessor);
                                            result = true;
                                            numOfNodesInTree--;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    tempRoot = null;
                }
            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    public void checkForTwoConsecutiveReds(Node rootNode){
        if(rootNode == null) {
            return;
        }
        checkForTwoConsecutiveReds(rootNode.getLeftNode());
        if(!rootNode.getBlack() && rootNode.getParentNode()!=null && !rootNode.getParentNode().getBlack()){
            throw new TerminationException(rootNode);
        }
        checkForTwoConsecutiveReds(rootNode.getRightNode());
    }

    public Boolean isExistInTree(UserDto userDto)
    {
        Boolean result = false;
        try{
            if(this.rootNode != null)
            {
                Node tempNode = this.rootNode;
                while (tempNode!=null) {
                    if ( userDto.getId().longValue() < tempNode.getUser().getId().longValue() ) {
                        tempNode = tempNode.getLeftNode();
                    } else if (userDto.getId().longValue() > tempNode.getUser().getId().longValue()) {
                        tempNode = tempNode.getRightNode();
                    } else {
                        result = true;
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
