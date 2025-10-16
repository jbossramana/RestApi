package demo.boot.model;



public class City {
    private int id;
    private String name;
    private String state;

    public City(int id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setState(String state) {
        this.state = state;
    }
}
