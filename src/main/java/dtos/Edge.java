package dtos;

public class Edge {

    private Integer sourceVertexIndex;
    private Integer destinationVertexIndex;
    private Integer weight;

    public Edge(Integer sourceVertexIndex,Integer destinationVertexIndex,Integer weight) {
        this.sourceVertexIndex = sourceVertexIndex;
        this.destinationVertexIndex = destinationVertexIndex;
        this.weight = weight;
    }

    public Integer getSourceVertexIndex() {
        return sourceVertexIndex;
    }

    public void setSourceVertexIndex(Integer sourceVertexIndex) {
        this.sourceVertexIndex = sourceVertexIndex;
    }

    public Integer getDestinationVertexIndex() {
        return destinationVertexIndex;
    }

    public void setDestinationVertexIndex(Integer destinationVertexIndex) {
        this.destinationVertexIndex = destinationVertexIndex;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
