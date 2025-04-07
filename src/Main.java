import java.io.*;
/**
 * @author Panasenko Anastasia
 * @version 1.0
 * Dudes collector
 */
public class Main {
    public static void main(String[] args) throws IOException, BusyIdException {
        CommandCenter commander = new CommandCenter(new Office(System.getenv("Pathforlab")));
        commander.run();
        System.out.println("That's all");
    }
}
