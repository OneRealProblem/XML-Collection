public class Coordinates {
    private Long x; //Поле не может быть null
    private Long y; //Поле не может быть null

    public Coordinates(Long x,Long y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return ("x = " + x + ", y = " + y);
    }
}
