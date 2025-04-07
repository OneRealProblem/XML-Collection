import java.time.LocalDateTime;
import java.util.*;

/**
 * Dudes class. Can be put into main collection.
 */
public class HumanBeing {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле может быть null
    private long impactSpeed; //Значение поля должно быть больше -220
    private Float minutesOfWaiting; //Поле не может быть null
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле не может быть null
    private Car car; //Поле не может быть null

    private static final List<Names> VALUES = Collections.unmodifiableList(Arrays.asList(Names.values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Names randomName()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    public Integer getId(){
        return id;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public Float getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public long getImpactSpeed() {
        return impactSpeed;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String MoodToString() {
        switch(mood){
            case CALM:
                return "спокойный";
            case APATHY:
                return "апатичный";
            case SORROW:
                return "в дрейне";
            default:
                return "да кто его знает";
        }
    }
    public String RealHeroToString() {
        if (realHero){
            return "Да";
        }else{
            return "Нет";
        }
    }
    public String ToothpickToString() {
        if (hasToothpick == null){
            return "Да кто его знает";
        }else{
            if (hasToothpick){
                return "Да";
            }
        }
        return "Нет";
        }

    public Car getCar() {
        return car;
    }

    public Mood getMood() {
        return mood;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public String getName(){ return this.name;}

    public void setName(String name){
        StringBuilder nam = new StringBuilder(name);
        nam.setCharAt(0, java.lang.Character.toUpperCase(name.charAt(0)));
        this.name = nam.toString();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCoordinates(Long x, Long y){
        this.coordinates = new Coordinates(x,y);
    }

    public void setMinutesOfWaiting(Float minutesOfWaiting) {
        this.minutesOfWaiting = minutesOfWaiting;
    }

    public void isRealHero(boolean b){
        this.realHero = b;
    }
    public void doHaveToothpick(Boolean b){
        this.hasToothpick = b;
    }
    public void setImpactSpeed(long impactSpeed){
        if (impactSpeed <= -220){
            this.impactSpeed = -220;
        } else {
            this.impactSpeed = impactSpeed;
        }
    }
    public void setWeaponType(WeaponType weaponType){
        this.weaponType = weaponType ;
    }
    public void setMood(Mood mood){
        this.mood = mood ;
    }
    public void setCar(Car car){
        this.car = car ;
    }
    public boolean greaterThan(HumanBeing another){
        if (this.id > another.getId()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        return ("Чел по имени " + name + " (ID = " + id +")");
    }



    HumanBeing(){
        String nam = randomName().name().toLowerCase();
        setName(nam);
        setCoordinates(Long.valueOf(0),Long.valueOf(0));
        setCar(new Car());
        this.creationDate = LocalDateTime.now();
        setMood(Mood.CALM);
        setMinutesOfWaiting(0.0F);
    }
}


