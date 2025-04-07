public class BusyIdException extends Exception{
    @Override
    public String toString(){
        return ("Такое ID уже занято.");
    }
}
