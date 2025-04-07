import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Для координации работы с файлом

/**
 * Coordinates file work and do commands
 */
public class Office {
    private LinkedHashMap<Integer, HumanBeing> dudes;
    private Date initDate;
    private Iterator i;
    private boolean wasStart;
    protected static HashMap<String, String> manual;
    private Clerk clerk;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    {
        dudes = new LinkedHashMap<>();
        i = dudes.entrySet().iterator();
        manual = new HashMap<>();
        manual.put("help", "Вывести справку по доступным командам");
        manual.put("info", "Вывести в стандартный поток вывода информацию о коллекции.");
        manual.put("show", "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении.");
        manual.put("insert_key {key}", "Добавить новый элемент с заданным ключом.");
        manual.put("update_id {id}", "Обновить значение элемента коллекции, id которого равен заданному.");
        manual.put("remove_key {key}", "Удалить элемент из коллекции по его ключу.");
        manual.put("clear", "Очистить коллекцию.");
        manual.put("save", "Cохранить коллекцию в файл.");
        manual.put("execute_script {file_name}", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        manual.put("exit", "Завершить программу (без сохранения в файл)");
        manual.put("remove_lower {name}", "Удалить из коллекции все элементы, меньшие, чем заданный.");
        manual.put("replace_if_lower {than this key} {name}", "Заменить значение по ключу, если новое значение меньше старого.");
        manual.put("remove_greater_key {than this key}", "Удалить из коллекции все элементы, ключ которых превышает заданный.");
        manual.put("remove_all_by_minutes_of_waiting {minutesOfWaiting}", "Удалить из коллекции все элементы, значение поля minutesOfWaiting которого эквивалентно заданному.");
        manual.put("sum_of_mow", "Вывести сумму значений поля minutesOfWaiting для всех элементов коллекции.");
        manual.put("print_car", "Вывести значения поля car всех элементов в порядке возрастания.");
    }

    public void customize(HumanBeing dude){
        Scanner reader = new Scanner(System.in);
        String thing = new String();
        System.out.println("Введите имя.");
        dude.setName(reader.nextLine().toLowerCase());
        System.out.println("Введите координаты x,y через пробел.");
        String[] xy = reader.nextLine().split(" ");
        dude.setCoordinates(Long.parseLong(xy[0]),Long.parseLong(xy[1]));
        System.out.println("У всех тут должна быть тачка. Напишите, какая у этого парня машина.");
        dude.setCar(new Car(reader.nextLine()));
        System.out.println("Эта тачка крутая? Напишите true или false.");
        dude.getCar().setCool(Boolean.parseBoolean(reader.nextLine().toLowerCase()));
        System.out.println("Приглядимся поближе? Напишите yes или no.");
        boolean t = true;
        while (t){
            switch (reader.nextLine().toLowerCase()){
                case ("yes"):
                    System.out.println("Этот парень герой? Напишите true, если да и false, если нет.");
                    dude.isRealHero(Boolean.parseBoolean(reader.nextLine().toLowerCase()));
                    System.out.println("Есть ли у него зубочистка? Напишите true, если да и false, если нет.");
                    dude.doHaveToothpick(Boolean.parseBoolean(reader.nextLine().toLowerCase()));
                    System.out.println("Есть ли у него оружие? Напишите true, если да и false, если нет.");
                    if (reader.nextLine().toLowerCase().equals("true")){
                        System.out.println("Выбираем тип оружия! Напишите axe, bat, hammer, rifle или pistol.");
                        dude.setWeaponType(clerk.stringToWeapon(reader.nextLine().toLowerCase()));
                    }
                    System.out.println("Какое у парня настроение? Варианты: calm, sorrow, apathy.");
                    dude.setMood(clerk.stringToMood(reader.nextLine().toLowerCase()));
                    t = false;
                    break;
                case ("no"):
                    System.out.println("Ладно.");
                    t = false;
                    break;
                default:
                    System.out.println("Напишите yes или no.");
            }
        }
        System.out.println("Готово! Ваш чувак кастомизирован.");
    }

    public LinkedHashMap<Integer, HumanBeing> copy(LinkedHashMap<Integer, HumanBeing> m) {
        LinkedHashMap<Integer, HumanBeing> newm = new LinkedHashMap<Integer, HumanBeing>();
        for (Integer k: m.keySet()){
            newm.put(k, m.get(k));
        }
        return newm;
    }

    public boolean dudeLowerThatAll(HumanBeing theDude, LinkedHashMap<Integer, HumanBeing> dudes){
        Boolean l = true;
        for (HumanBeing dude: dudes.values()){
            if (theDude.greaterThan(dude)){
                l = false;
            }
        }
        return l;
    }

    public LinkedHashMap<Integer, HumanBeing> sorted(LinkedHashMap<Integer, HumanBeing> dudes) {
        LinkedHashMap<Integer, HumanBeing> nots = copy(dudes);
        LinkedHashMap<Integer, HumanBeing> notsForRemoving = copy(dudes);
        LinkedHashMap<Integer, HumanBeing> s = new LinkedHashMap<Integer, HumanBeing>();
        while (!notsForRemoving.isEmpty()) {
            for (HumanBeing dude : nots.values()) {
                if (dudeLowerThatAll(dude, notsForRemoving)) {
                    s.put(dude.getId(), dude);
                    notsForRemoving.remove(dude.getId());
                }
            }

        }
        return s;
    }

    public void help() {
        System.out.println("Справка пользователя.");
        System.out.println("Команды:");
        for (String i : manual.keySet()){
            System.out.println(i + " - " + manual.get(i));
        }
    }
    public void info(){
        System.out.println("Информация о коллекции:");
        System.out.println("Тип: " + dudes.getClass());
        System.out.println("Дата инициализации: " + initDate);
        System.out.println("Количество элементов: " + dudes.size());
    }
    public void show(){
        if (dudes.isEmpty()){
            System.out.println("Коллекция пуста.");
        }
        for(Integer i: dudes.keySet()){
            HumanBeing dude = dudes.get(i);
            //Получим минуты ожидания
            Duration age = Duration.between(dude.getCreationDate(), LocalDateTime.now());
            Long min = age.toMinutes();
            dude.setMinutesOfWaiting(min.floatValue());
            System.out.println(dude + ":");
            System.out.println("   Дата создания: " + dude.getCreationDate().format(formatter));
            System.out.println("   Координаты: " + dude.getCoordinates());
            System.out.println("   Геройские качества: " + dude.RealHeroToString());
            System.out.println("   Есть ли зубочистка: " + dude.ToothpickToString());
            System.out.println("   Скорость: " + dude.getImpactSpeed());
            System.out.println("   Время ожидания в минутах: " + dude.getMinutesOfWaiting());
            System.out.println("   Настроение: " + dude.MoodToString());
            System.out.println("   Машина: " + dude.getCar());
        }
    }
    public void insert(Integer k) throws BusyIdException {
        try{
            if (dudes.containsKey(k)){
                BusyIdException e = new BusyIdException();
                throw e;
            }else{
                HumanBeing dude = new HumanBeing();
                dude.setId(k);
                dudes.put(k,dude);
                clerk.addGeneratedId(k);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void update(Integer id){
        try {
            if (dudes.containsKey(id)) {
                customize(dudes.get(id));
            } else {
                IllegalArgumentException e = new IllegalArgumentException("Такого ID в коллекции нет!");
                throw e;
            }
        }catch (Exception e){System.err.println(e);}

    }
    public void remove(Integer k){
        try {
            if (dudes.containsKey(k)) {
                dudes.remove(k);
                clerk.removeGeneratedId(k);
            } else {
                IllegalArgumentException e = new IllegalArgumentException("Такого ID в коллекции нет!");
                throw e;
            }
        }catch (Exception e){System.out.println(e);}
    }
    public void clear(){
        dudes.clear();
        clerk.clearGeneratedId();
    }
    public void save() throws IOException {
        clerk.save();
    }

    public void removeLower(String nam){
        char fl = nam.charAt(0);
        Integer theId = 0;
        String name = nam.replace(nam.charAt(0), java.lang.Character.toUpperCase(fl));
        for(HumanBeing dude: dudes.values()) {
            if (name.equals(dude.getName())) {
                theId = dude.getId();
            }
        }
        LinkedHashMap<Integer, HumanBeing> newdudes = copy(dudes);
        for(HumanBeing dude: dudes.values()){
            if (dude.getId() < theId){
                clerk.removeGeneratedId(dude.getId());
                newdudes.remove(dude.getId());
            }
        }
        dudes = newdudes;
    }
    public void replaceIfLower(Integer k, String name){
        StringBuilder n = new StringBuilder(name.toLowerCase());
        n.setCharAt(0, java.lang.Character.toUpperCase(name.charAt(0)));
        String nam = n.toString();
        Integer stid = 0;
        for (Integer i: dudes.keySet()){
            if (nam.equals(dudes.get(i).getName())){
                if (dudes.get(i).getId() < k){
                    stid = dudes.get(i).getId();
                }
            }
        }
        if (stid > 0){
            HumanBeing theDude = dudes.get(stid);
            clerk.removeGeneratedId(theDude.getId());
            dudes.remove(stid);
            theDude.setId(k);
            dudes.put(theDude.getId(), theDude);
        }

    }

    public void removeGreaterKey(Integer k){
        for (HumanBeing dude: dudes.values()){
            if (dude.getId() < k){
                clerk.removeGeneratedId(dude.getId());
                dudes.remove(dude.getId());
            }
        }
    }
    public void removeByMOW(Float f){
        for (Integer k: dudes.keySet()){
            if (dudes.get(k).getMinutesOfWaiting() == f){
                clerk.removeGeneratedId(dudes.get(k).getId());
                dudes.remove(k);
            }
        }
    }
    public void sumOfMOW(){
        float sum = 0;
        for (HumanBeing dude: dudes.values()){
            sum += dude.getMinutesOfWaiting();
        }
        System.out.println(sum);
    }
    public void printCars(){
        LinkedHashMap<Integer, HumanBeing> sdudes = sorted(this.dudes);
        for (HumanBeing dude: sdudes.values()){
            System.out.println(dude.getName() + " и его " + dude.getCar());
        }
    }
    public Office(String collectionPath) throws IOException, BusyIdException {
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до файла нужно передать через переменную окружения Pathforlab.");
            System.exit(1);
        }
        File file = new File(collectionPath);
        try {
            if (file.exists()){
                this.clerk = new Clerk(file);
                this.dudes = clerk.getDudes();
            }
            else throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл по указанному пути не существует.");
            System.exit(1);
        }
        this.initDate = new Date();
        wasStart = true;

    }
}
