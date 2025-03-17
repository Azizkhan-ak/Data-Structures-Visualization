package dtos;

public class Vertex implements Comparable {

    private Long city;
    private Boolean isVisited = false;

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Boolean getVisited() {
        return isVisited;
    }

    public void setVisited(Boolean visited) {
        isVisited = visited;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
