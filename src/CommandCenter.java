import java.io.*;
import java.util.Scanner;
// Для работы с командами пользователя

/**
 * Communicates with user
 */
public class CommandCenter {

    private String usCom;
    private String[] command;
    private Office office;

    {
        this.usCom = new String();
    }

    public void run() throws  BusyIdException{
        try (Scanner reader = new Scanner(System.in)) {
            while (!usCom.equals("exit")) {
                usCom = reader.nextLine().toLowerCase();
                command = usCom.split(" ");
                switch (command[0]) {
                    case ("help"):
                        office.help();
                        break;
                    case ("info"):
                        office.info();
                        break;
                    case ("show"):
                        office.show();
                        break;
                    case ("insert_key"):
                        office.insert(Integer.parseInt(command[1]));
                        break;
                    case ("update_id"):
                        office.update(Integer.parseInt(command[1]));
                        break;
                    case ("remove_key"):
                        office.remove( Integer.parseInt(command[1]));
                        break;
                    case ("clear"):
                        office.clear();
                        break;
                    case ("save"):
                        office.save();
                        break;
                    case ("execute_script"):
                        File fr = new File(command[1]);
                        doFile(fr);
                        break;
                    case ("remove_lower"):
                        office.removeLower(command[1]);
                        break;
                    case ("replace_if_lower"):
                        office.replaceIfLower(Integer.parseInt(command[1]), command[2]);
                        break;
                    case ("remove_greater_key"):
                        office.removeGreaterKey(Integer.parseInt(command[1]));
                        break;
                    case ("remove_all_by_minutes_of_waiting"):
                        office.removeByMOW(Float.parseFloat(command[1]));
                        break;
                    case ("sum_of_mow"):
                        office.sumOfMOW();
                        break;
                    case ("print_car"):
                        office.printCars();
                        break;
                    case("exit"):
                        break;
                    default:
                        System.err.println("Вы уверены, что написали команду правильно? Отправьте help для справки по командам.");
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден.");
        } catch (IOException e) {
            System.err.println("Error");
        }
    }

    public void doFile(File f) throws BusyIdException, FileNotFoundException {
        FileReader t = new FileReader(f);
        try (BufferedReader reader = new BufferedReader(t)) {
            while (!usCom.equals("exit")) {
                String com = reader.readLine();
                if (com == null){
                    System.err.println("Не хватает команды exit.");
                    break;
                }
                String usCom = com.toLowerCase();
                command = usCom.split(" ");
                switch (command[0]) {
                    case ("help"):
                        office.help();
                        break;
                    case ("info"):
                        office.info();
                        break;
                    case ("show"):
                        office.show();
                        break;
                    case ("insert_key"):
                        office.insert(Integer.parseInt(command[1]));
                        break;
                    case ("update_id"):
                        office.update(Integer.parseInt(command[1]));
                        break;
                    case ("remove_key"):
                        office.remove( Integer.parseInt(command[1]));
                        break;
                    case ("clear"):
                        office.clear();
                        break;
                    case ("save"):
                        //office.save();
                    case ("execute_script"):
                        File fr = new File(command[1]);
                        doFile(fr);
                        break;
                    case ("remove_lower"):
                        office.removeLower(command[1]);
                        break;
                    case ("replace_if_lower"):
                        office.replaceIfLower(Integer.parseInt(command[1]), command[2]);
                        break;
                    case ("remove_greater_key"):
                        office.removeGreaterKey(Integer.parseInt(command[1]));
                        break;
                    case ("remove_all_by_minutes_of_waiting"):
                        office.removeByMOW(Float.parseFloat(command[1]));
                        break;
                    case ("sum_of_mow"):
                        office.sumOfMOW();
                        break;
                    case ("print_car"):
                        office.printCars();
                        break;
                    case("exit"):
                        break;
                    default:
                        System.out.println("Вы уверены, что написали команду правильно? Отправьте help для справки по командам.");
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден");
        } catch (IOException e) {
            System.err.println("Ошибка");
        }
    }

    public CommandCenter(Office of){
        System.out.println("Добро пожаловать в Коллекцию чуваков! Здесь вы можете коллекционировать своих чуваков и смотреть их параметры.");
        System.out.println("Напоминаю, что эта программа не поддерживает автоматическое сохранение данных. Для сохранения данных в файл нужно ввести команду save");
        System.out.println("Для справки напишите help");
        System.out.println("");
        this.office = of;
    }
}
