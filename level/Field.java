package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemFire;

import java.util.ArrayList;
import java.util.List;

/**
 * A BaseField interface-t implementáló osztály.
 */
public class Field implements BaseField{

    /**
     * A mező szine.
     */
    private int color;
    /**
     * A mezőn van-e tábortűz lehelyezve.
     */
    private ItemFire fire;
    /**
     * Az eldobott itemek.
     */
    private final List<AbstractItem> item;
    /**
     * Az interakcióba lépések száma.
     */
    private int tickInteract;


    /**
     * Az osztály konstruktora.
     * @param color szin
     */
    public Field(int color){
        this.color = color;
        item = new ArrayList<>();
        tickInteract = 0;
        fire = null;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mező járható-e.
     * @return igaz, amennyiben a mező járható; hamis egyébként
     */
    @Override
    public boolean isWalkable() {
        return (color != MapColors.WATER);
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e fa.
     * @return igaz, amennyiben van fa; hamis egyébként
     */
    @Override
    public boolean hasTree() {
        return color == MapColors.TREE;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e kő.
     * @return igaz, amennyiben van kő; hamis egyébként
     */
    @Override
    public boolean hasStone() {
        return color == MapColors.STONE;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e gally.
     * @return igaz, amennyiben van gally; hamis egyébként
     */
    @Override
    public boolean hasTwig() {
        return color == MapColors.TWIG;
    }

    /**
     * Ezen metódus segítségével lekérdezheő, hogy a mezőn van-e bogyó.
     * @return igaz, amennyiben van bogyó; hamis egyébként
     */
    @Override
    public boolean hasBerry() {
        return color == MapColors.BERRY;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e répa.
     * @return igaz, amennyiben van répa; hamis egyébként
     */
    @Override
    public boolean hasCarrot() {
        return color == MapColors.CARROT;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e tűz rakva.
     * @return igaz, amennyiben van tűz; hamis egyébként
     */
    @Override
    public boolean hasFire() {
        return fire != null;
    }

    /**
     * Ezen metódus segítségével a mezőn lévő tárgyak lekérdezhetők.<br>
     * A tömbben az a tárgy jön hamarabb, amelyik korábban került az adott mezőre.<br>
     * A karakter ha felvesz egy tárgyat, akkor a legkorábban a mezőre kerülő tárgyat fogja felvenni.<br>
     * Ha nem sikerül felvenni, akkor a (nem) felvett tárgy a tömb végére kerül.
     * @return a mezőn lévő tárgyak
     */
    @Override
    public AbstractItem[] items() {
        return item.toArray(item.toArray(new AbstractItem[item.size()]));

    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mező üres-e.
     * @return igaz, amennyiben üres; hamis egyébként
     */
    public boolean isEmpty(){
        return color == MapColors.EMPTY;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy hányszor léptünk interakcióval a mezővel.
     * @return az interakciók száma
     */
    public int getTick(){
        return tickInteract;
    }

    /**
     *  Ez a metódus meghívására növelődik az interakciók száma.
     */
    public void tickInteracted(){
        tickInteract++;
    }

    /**
     * Ezzel a metúdussal eldobhatunk itemeket az adott mezőre.
     * @param droppedItem eldobott item
     */
    @Override
    public void dropItem(AbstractItem droppedItem) {
        item.add(droppedItem);
    }

    /**
     * Ezzel a metúdussal beállíthatjuk a mező értékét üresha ha például már kivágtuk a rajta lévő fát.
     */
    public void setEmpty(){
        color = MapColors.EMPTY;
    }

    /**
     * Ezzel a metúdussal felvehetünk itemeket a földről, ami törlődik a tartalmazó listából.
     */
    public void removeDroppedItem(AbstractItem removeItem){
        item.remove(removeItem);
    }

    /**
     * Ezzel a metúdussal tüzet rakhatunk az adott mezőre.
     */
    public void setFire(){
        fire = new ItemFire();
    }

    /**
     * Ez a metódus megszunteti a tuzet, ha mar kialudt.
     */
    public void fireTime(){
        if(fire != null && fire.isFireWentOut()){
            fire = null;
        }
    }
}
