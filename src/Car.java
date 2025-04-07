public class Car {
    private boolean cool;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCool() {
        return cool;
    }

    public void setCool(boolean cool) {
        this.cool = cool;
    }

    public Car(String name){
        setName(name);
    }
    public Car(){
        setName("idk");
        this.cool = false;
    }

    @Override
    public String toString(){
            return (name);
    }
}
