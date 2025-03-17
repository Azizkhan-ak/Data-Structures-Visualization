package datastructures;

import dtos.Node;
import dtos.UserDto;

public class BinaryTree {
    public Node root;
    public static Integer numOfNodesInTree = 0;

    public BinaryTree(){}

    public Boolean insertIntoBinaryTree(UserDto userDto)
    {
        Boolean result = false;
        try{
            if(root == null)
            {
                //means that Binary Tree will be created
                Node node = new Node();
                node.setUser(userDto);
                node.setRightNode(null);
                node.setLeftNode(null);
                this.root = node;
                result = true;
                numOfNodesInTree++;
            }
            else {
                //means that Binary Tree is already created so we first need to check if
                // there is already user present with same CNIC
                if(!isExistInTree(userDto))
                {
                    //means that User does not exist so we will insert
                    Node tempNode = this.root;
                    while (tempNode!=null)
                    {
                        if(userDto.getId().longValue() < tempNode.getUser().getId().longValue())
                        {
                            if(tempNode.getLeftNode() == null)
                            {
                                Node newNode = new Node();
                                newNode.setUser(userDto);
                                newNode.setLeftNode(null);
                                newNode.setRightNode(null);
                                tempNode.setLeftNode(newNode);
                                result = true;
                                numOfNodesInTree++;
                                break;
                            }
                            else {
                                tempNode = tempNode.getLeftNode();
                            }
                        }

                        if(userDto.getId().longValue() > tempNode.getUser().getId().longValue())
                        {
                            if(tempNode.getRightNode() == null)
                            {
                                Node newNode = new Node();
                                newNode.setUser(userDto);
                                newNode.setLeftNode(null);
                                newNode.setRightNode(null);
                                tempNode.setRightNode(newNode);
                                numOfNodesInTree++;
                                result = true;
                                break;
                            }
                            else {
                                tempNode = tempNode.getRightNode();
                            }
                        }
                    }
                }
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    public Boolean isExistInTree(UserDto userDto)
    {
        Boolean result = false;
        try{
            if(this.root != null)
            {
                Node tempNode = this.root;
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

    public void inOrderTraversal(Node root)
    {
        if(root==null)
        {
            return ;
        }
        inOrderTraversal(root.getLeftNode());
        inOrderTraversal(root.getRightNode());
    }

    /*so there will be three cases
    1- When node to be deleted is leave node , we will simply make reference of parent node to left/child NULL
    2- When there is left/right child of to be deleted Node , we will replace Node to be deleted with Left/Right child
    3- When there is Left & Right child , than  ( we do not touch left Sub tree )
           -> if right child has no left child , we will simple make right child replace Node to be deleed and left child will be child of right child (curently parent Node)
           -> if right child has left childs ,
                     -> if left most child of this right child does not have any right child , than we will make leftmost child to replace deleted Node
                     -> or else we will put leftmostchild in deleted node position , and its right child as leftchild of parent of leftmostchild

    */
    public Boolean deleteFromBinaryTree(UserDto userDto)
    {
        Long userId = userDto.getId();
        Boolean result = false;
        try{
            if(this.root==null)
            {
                return false;
            }
            Node tempParent = null;
            Node tempRoot = this.root;
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
                    if (tempParent == null && this.root.getLeftNode() == null && this.root.getRightNode() == null) {
                        //means that to be deleted element is root node and tree only has root element
                        this.root = null;
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
                                this.root = tempRoot.getLeftNode();
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
                                this.root = tempRoot.getRightNode();
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
                                this.root = tempRoot.getRightNode();
                                this.root.setLeftNode(tempRoot.getLeftNode());
                                this.root.setRightNode(tempRoot.getRightNode().getRightNode());
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
                                    this.root = leftMostSuccessor;
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
                                    this.root = leftMostSuccessor;
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


}
