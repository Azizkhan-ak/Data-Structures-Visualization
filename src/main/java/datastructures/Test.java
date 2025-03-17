package datastructures;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dtos.Edge;
import dtos.Vertex;

public class Test {
    private Integer currentSize;
    private Vertex[] vertices;
    private Boolean[][] edges;


    public Test(Integer size)
    {
        this.currentSize= size;;
        vertices = new Vertex[currentSize];
    }

}
