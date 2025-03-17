package dtos;

public class Node {

    private UserDto user;
    private Node leftNode;
    private Node rightNode;

    //This node's parent if exists if no means that its root node
    private Node parentNode;

    // if true means that black or else red
    private Boolean isBlack;

    private Boolean isLeftChild;

    //used in AVL Tree
    private Integer balanceFactor;
    private int xLoc;
    private int yLoc;
    private int nHeight;
    private int nWidth;

    private int currentNodeX;

    public Node() {
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public int getnHeight() {
        return nHeight;
    }

    public void setnHeight(int nHeight) {
        this.nHeight = nHeight;
    }

    public int getnWidth() {
        return nWidth;
    }

    public void setnWidth(int nWidth) {
        this.nWidth = nWidth;
    }

    public int getCurrentNodeX() {
        return currentNodeX;
    }

    public void setCurrentNodeX(int currentNodeX) {
        this.currentNodeX = currentNodeX;
    }

    public Boolean getBlack() {
        return isBlack;
    }

    public void setBlack(Boolean black) {
        isBlack = black;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Boolean getLeftChild() {
        return isLeftChild;
    }

    public void setLeftChild(Boolean leftChild) {
        isLeftChild = leftChild;
    }

    public Integer getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(Integer balanceFactor) {
        this.balanceFactor = balanceFactor;
    }
}
