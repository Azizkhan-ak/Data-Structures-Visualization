package datastructures;

import dtos.Edge;
import dtos.Vertex;

import java.util.*;

public class WeightedGraph {

    private Vertex[] vertices;
    private Integer[][] edges;
    private Integer nextIndex = 0;
    private Integer currentGraphSize;

    Integer currentVertex = 0;

    Set<Edge> subSetOfEdges = new HashSet<>();

    private PriorityQueueForWeightedGraph queueForWeightedGraph;

    public WeightedGraph(){
        currentGraphSize = 10;
        vertices = new Vertex[10];
        //if x,y is null means that no edge exists or else it will have weight of that edge
        edges = new Integer[10][10];
        queueForWeightedGraph = new PriorityQueueForWeightedGraph();
    }

    public void addVertex(Vertex vertex){
        if(vertices[vertices.length-1]!=null){
            Vertex[] tempArrayVertices = new Vertex[currentGraphSize*2];
            Integer[][] tempArrayEdges = new Integer[currentGraphSize*2][currentGraphSize*2];
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

    //In this case we add undirected edge between source and des if both exist in graph
    //if any of them does not exist then edging is failed
    public Boolean addEdges(Long sourceCity , Long destinationCity, Integer weight){
        //so first we need to get indices of source and destination and than make and edge
        int sourceIndex = checkIfExist(sourceCity);
        int destinationIndex = checkIfExist(destinationCity);

        if(sourceCity == -1 || destinationCity == -1){
            return false;
        }
        edges[sourceIndex][destinationIndex] = weight;
        edges[destinationIndex][sourceIndex] = weight;
        return true;
    }

    public int checkIfExist(Long cityId){
        int start = 0;
        int end = vertices.length-1;
        while (start<=end){
            int mid = (start + ((end - start)/2));
            if(vertices[mid] != null) {
                if (vertices[mid].getCity().equals(cityId)) {
                    return mid;
                } else if (vertices[mid].getCity().longValue() < cityId.longValue()) {
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
                if (edges[index][i]!=null && !vertices[i].getVisited()) {
                    return vertices[i];
                }
            }
        }
        return null;
    }

    public void minimumSpanningTree(){

        if(vertices.length == 0){
            return;
        }

        Integer totalVertices = vertices.length-1;
        Integer verticesInMST = 0;
        vertices[currentVertex].setVisited(true);

        while (verticesInMST <= totalVertices){

            for (int j =0; j<vertices.length;j++){
                if(j == currentVertex)
                    continue; // means that same vertex at source and des
                if(vertices[j].getVisited())
                    continue; // if vertex is already visited skip

                Integer distance = edges[currentVertex][j];

                if(distance == null)
                    continue; // if no edge exist skip it

                putInPQ(j,distance);
            }

            if(queueForWeightedGraph.size() == 0){
                return;
            }

            Edge minEdge = queueForWeightedGraph.removeMin();
            subSetOfEdges.add(minEdge);

            System.out.println("Edge : "+ minEdge.getSourceVertexIndex().intValue() +" ---> "+ minEdge
                    .getDestinationVertexIndex().intValue());
        }
    }

    public void putInPQ(Integer destinationVertex , Integer weight){

        Integer queueIndex = queueForWeightedGraph.find(destinationVertex);
        if(queueIndex !=-1){
            Integer oldWeight = queueForWeightedGraph.peekN(queueIndex).getWeight();
            if(oldWeight > weight){
                // we need minium weight
                queueForWeightedGraph.removeN(queueIndex);
                Edge edge = new Edge(currentVertex,destinationVertex,weight);
                queueForWeightedGraph.insert(edge);
            }
        }
        else{
            Edge edge = new Edge(currentVertex,destinationVertex,weight);
            queueForWeightedGraph.insert(edge);
        }
    }

    public List<Long> findShortestPathBetweenTwoNodes(Long sourceCityId,Long destinationCityId){
        List<Long> shortestPath = new ArrayList<>();
        HashMap<Long,Long> distanceTable = new HashMap<>();
        List<Integer> visited = new ArrayList<>();
        // FIRST LET ME PICK THE SOURCE , THIS WILL GIVE ME INDEX OF SOURCE CITY
        Integer sourceVertexIndex = checkIfExist(sourceCityId);

        for(int i =0;i<vertices.length;i++){
            if(vertices[i]!=null)
            distanceTable.put(vertices[i].getCity(), Long.MAX_VALUE);
        }
        distanceTable.put(sourceCityId,0l);

        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.add(sourceVertexIndex);

        while (!priorityQueue.isEmpty())
        {
            Integer currentNodeIndex = Integer.valueOf(priorityQueue.poll().toString());
            visited.add(currentNodeIndex);

            for(int i =0;i<vertices.length;i++){
                if(vertices[i]!=null  ) {
                    if (edges[currentNodeIndex.intValue()][i] !=null && !visited.contains(i)) {
                        Integer distance = distanceTable.get(vertices[currentNodeIndex].getCity()).intValue() + edges[currentNodeIndex][i];
                        if (distanceTable.get(vertices[i].getCity()) > distance) {
                            distanceTable.put(vertices[i].getCity(), distance.longValue());
                            priorityQueue.add(i);
                        }
                    }
                }
            }
        }

        return shortestPath;
    }
}

