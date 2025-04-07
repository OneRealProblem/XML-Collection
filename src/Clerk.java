import java.io.*;
import java.lang.*;
import java.util.*;
//Делает всю работу с файлом

/**
 * Do all the file work
 */
public class Clerk {
    private LinkedHashMap<Integer, HumanBeing> dudes;
    private String[] infoaboutdudes;
    private ArrayList<Integer> generatedId;
    private Integer i;
    private File work;

    {
        generatedId = new ArrayList<Integer>();
        i = 1;
    }
    public LinkedHashMap<Integer, HumanBeing> getDudes(){
        return dudes;
    }
    public String[] getInfoaboutdudes(){
        return(infoaboutdudes);
    }

    public ArrayList<Integer> getGeneratedId() {
        return generatedId;
    }

    public void addGeneratedId(Integer id) {
        this.generatedId.add(id);
    }
    public void removeGeneratedId(Integer id) {
        this.generatedId.remove(id);
    }
    public void clearGeneratedId(){
        this.generatedId.clear();
    }

    public void setdudeId(Integer id, HumanBeing dude) throws BusyIdException{
        if (generatedId.contains(id)){
            throw (new BusyIdException());
        }
        else{
            dude.setId(id);
            generatedId.add(id);
        }
    }
    public Integer generateID(){
        while (generatedId.contains(i)){
            i += 1;
        }
        return i;
    }

    public Mood stringToMood(String s){
        try {
            Mood m;
            s.toLowerCase();
            switch (s) {
                case ("calm"):
                    return Mood.CALM;
                case ("sorrow"):
                    return Mood.SORROW;
                case ("apathy"):
                    return Mood.APATHY;
                default:
                    throw new IllegalStateException();
            }
        }catch (IllegalStateException i){
            System.err.println("Это что за настроение такое " + s + "? Он будет спокойным");
            return Mood.CALM;
        }
    }

    public WeaponType stringToWeapon(String s){
        WeaponType m;
        s.toLowerCase();
        switch(s){
            case("pistol"):
                m = WeaponType.PISTOL;
                break;
            case("axe"):
                m = WeaponType.AXE;
                break;
            case("bat"):
                m = WeaponType.BAT;
                break;
            case("hammer"):
                m = WeaponType.HAMMER;
                break;
            case("rifle"):
                m = WeaponType.RIFLE;
                break;
            default:
                System.err.println("Мы не поняли, что вы написали, поэтому выбрали за вас.");
                m = WeaponType.AXE;
                break;
        }
        return m;
    }

    public void save() throws IOException {
        FileWriter eraser = new FileWriter(work,false);
        eraser.write("");
        FileWriter pen = new FileWriter(work, true);
        pen.write("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
        pen.write("<humans> \n");
        try {
            for (HumanBeing dude : dudes.values()) {
                pen.write("<" + dude.getName() + "> \n" );
                pen.write("<name>" + dude.getName() + "</name> \n");
                pen.write("<id>" + dude.getId() + "</id> \n");
                pen.write("<realHero>" + dude.isRealHero() + "</realHero> \n");
                pen.write("<hasToothpick>" + dude.getHasToothpick() + "</hasToothpick> \n");
                pen.write("<ImpactSpeed>" + dude.getImpactSpeed() + "</ImpactSpeed> \n");
                if (dude.getWeaponType() != null){
                    pen.write("<WeaponType>" + dude.getWeaponType() + "</WeaponType> \n");
                }
                pen.write("<Mood>" + dude.getMood() + "</Mood> \n");
                pen.write("<Car>" + dude.getCar() + "</Car> \n");
                pen.write("</" + dude.getName() + "> \n");
            }
        }catch (NullPointerException n){
            System.err.println("Что-то пошло не так!");
        }
        pen.write("</humans>");
        pen.flush();
        pen.close();
    }

    public Clerk(File f) throws BusyIdException, IOException {
        work = f;
        this.dudes = new LinkedHashMap<Integer,HumanBeing>();
        FileInputStream fileInputStream = new FileInputStream(f);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 200);
        int i;
        String text = new String();
        HumanBeing dude = new HumanBeing();
        while((i = bufferedInputStream.read())!= -1) {
            text += ((char) i);
        }
        infoaboutdudes = text.toLowerCase().split(">");
        for (int ind = 1; ind< infoaboutdudes.length; ind+=1){
            String thing = infoaboutdudes[ind].trim();
            switch (thing) {
                case ("<name"):
                    String lowername = infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"));
                    dude.setName(lowername);
                    break;
                case ("<hastoothpick"):
                    dude.doHaveToothpick(Boolean.valueOf(infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"))));
                    break;
                case("<id"):
                    dude.setId(Integer.parseInt(infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"))));
                case ("<realhero"):
                    dude.isRealHero(Boolean.parseBoolean(infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"))));
                    break;
                case ("<impactspeed"):
                    dude.setImpactSpeed(Long.parseLong(infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"))));
                    break;
                case ("<weapontype"):
                    dude.setWeaponType(stringToWeapon(infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"))));
                    break;
                case ("<mood"):
                    dude.setMood(stringToMood(infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"))));
                    break;
                case ("<car"):
                    dude.setCar(new Car(infoaboutdudes[ind + 1].substring(0,infoaboutdudes[ind + 1].indexOf("<"))));
                    break;
                default:
                    if ((thing.startsWith("</"))&&(!thing.equals("</humans"))){
                        if (dude.getId() == null){
                            setdudeId(generateID(),dude);
                        }
                        System.out.println(dude);
                        this.dudes.put(dude.getId(),dude);
                        dude = new HumanBeing();
                }
            }
        }
    }
}
