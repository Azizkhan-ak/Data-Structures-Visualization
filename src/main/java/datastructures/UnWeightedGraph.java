package datastructures;

import dtos.Vertex;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class UnWeightedGraph {

    private Vertex[] vertices;
    private Integer currentGraphSize = 10;
    private Boolean[][] edges;
    private Integer nextIndex = 0;

    private LinkedList[] edgesAdjacencyList;

    public void addVertex(Vertex vertex){
        if(vertices == null ){
            vertices = new Vertex[currentGraphSize];
            edges = new Boolean[currentGraphSize][currentGraphSize];
            for (int i =0;i<=vertices.length-1;i++){
                for (int j =0;j<=vertices.length-1;j++){
                    edges[i][j] = false;
                }
            }
        }
        else {
            if(vertices[vertices.length-1]!=null){
                Vertex[] tempArrayVertices = new Vertex[currentGraphSize*2];
                Boolean[][] tempArrayEdges = new Boolean[currentGraphSize*2][currentGraphSize*2];
                for (int i =0;i<vertices.length;i++){
                    tempArrayVertices[i] = vertices[i];
                }
                for (int j=0;j<vertices.length;j++){
                    for (int k =0;k<vertices.length;k++){
                        tempArrayEdges[j][k] = edges[j][k];
                    }
                }
                currentGraphSize = currentGraphSize*2;
                vertices = tempArrayVertices;
                edges = tempArrayEdges;
                tempArrayEdges = null;
                tempArrayVertices = null;
            }
        }

        if(checkIfExist(vertex.getCity())==-1) {
            vertices[nextIndex++] = vertex;
        }

        // lets sort on the basis of cityId so that we could use binary search algorithm while searching
        for (int i =0;i<vertices.length;i++){
            for (int j = i+1;j<vertices.length;j++ ){
                if(vertices[i] !=null && vertices[j] !=null && vertices[i].getCity()>vertices[j].getCity()){
                    Vertex temp = vertices[i];
                    vertices[i] = vertices[j];
                    vertices[j] = temp;
                }
            }
        }
    }

    //In this case we add directed edge between source and des if both exist in graph
    //if any of them does not exist then edging is failed
    public Boolean addEdges(Long sourceCity , Long destinationCity){
        //so first we need to get indices of source and destination and than make and edge
        int sourceIndex = checkIfExist(sourceCity);
        int destinationIndex = checkIfExist(destinationCity);

        if(sourceCity == -1 || destinationCity == -1){
            return false;
        }

        edges[sourceIndex][destinationIndex] = true;
        return true;
    }

    public Boolean addEdgesAdjacencyList(Long sourceCity,Long destinationCity){
        int sourceIndex = checkIfExist(sourceCity);
        int destinationIndex = checkIfExist(destinationCity);



        return false;
    }

    public int checkIfExist(Long cityId){
        int start = 0;
        int end = vertices.length-1;
        while (start<=end){
            int mid = (start + ((end - start)/2));
            if(vertices[mid] != null) {
                if (vertices[mid].getCity() == cityId) {
                    return mid;
                } else if (vertices[mid].getCity() < cityId) {
                    start = mid+1;
                } else {
                    end = mid-1;
                }
            }
            else{
                end = mid-1;
            }
        }
        return -1;
    }

    // 1- If possible, visit an adjacent unvisited vertex, mark it, and push it on the stack.
    // 2- If you can’t follow Rule 1, then, if possible, pop a vertex off the stack
    // 3- If you can’t follow Rule 1 or Rule 2, you’re done.

    public void dfs(){
        //lets take any vertex from vertices as start vertex

        //lets begin
        Stack<Vertex> stack = new Stack<>();
        Vertex v = vertices[2];
        v.setVisited(true);
        System.out.println("City : "+ v.getCity()+" Visited");
        stack.push(v);

        while (!stack.isEmpty()){
            v = getAdjacentUnvisitedVertex(stack.peek());
            if(v != null){
                v.setVisited(true);
                System.out.println("City : "+ v.getCity()+" Visited");
                stack.push(v);
            }
            else{
                stack.pop();
            }
        }

        //make all elements unvisited again
        for (int i =0;i<vertices.length && vertices[i]!=null;i++){
            vertices[i].setVisited(false);
        }
    }

    public void bfs(){
        Queue<Object> queue = new PriorityQueue<>();
        //lets take any vertex , for example take vertex 3
        Vertex vertex = vertices[2];
        vertex.setVisited(true);
        System.out.println(" Visited : "+vertex.getCity());
        queue.add(vertex);

        while (!queue.isEmpty())
        {
            vertex =(Vertex) queue.peek();
            Vertex adjVertex = getAdjacentUnvisitedVertex(vertex);
            if(adjVertex != null)
            {
                adjVertex.setVisited(true);
                System.out.println(" Visisted : "+adjVertex.getCity());
                queue.add(adjVertex);
            }
            else {
                queue.poll();
            }
        }

        //make all elements unvisited again
        for (int i =0;i<vertices.length && vertices[i]!=null;i++){
            vertices[i].setVisited(false);
        }
    }

    public Vertex getAdjacentUnvisitedVertex(Vertex v){

        int index = checkIfExist(v.getCity());
        for (int i =0;i<vertices.length;i++){
            if(i != index) {
                if (edges[index][i] && !vertices[i].getVisited()) {
                    return vertices[i];
                }
            }
        }
        return null;
    }

    public void minimumSpaningTreeAlgo(Long cityId){
        //cityId indicates starting vertex

        int index = checkIfExist(cityId);
        if(index == -1){
            return;
        }
        Stack<Vertex> stack = new Stack<>();
        Vertex vertex = vertices[index];
        vertex.setVisited(true);
        stack.push(vertex);

        while (!stack.isEmpty()){
            Vertex currentVertex = stack.peek();
            Vertex nextVertex = getAdjacentUnvisitedVertex(currentVertex);

            if(nextVertex!=null){
                nextVertex.setVisited(true);
                stack.push(nextVertex);
                System.out.println(currentVertex.getCity()+"-"+nextVertex.getCity());
            }
            else{
                stack.pop();
            }
        }
    }

}
