package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.utility.Direction;
import prog1.kotprog.dontstarve.solution.utility.Position;

/**
 * Egy egyszerű karakter leírására szolgáló interface.
 */
public interface BaseCharacter {

    /**
     * A karakter mozgási sebességének lekérdezésére szolgáló metódus.
     * @return a karakter mozgási sebessége
     */
    float getSpeed();

    /**
     * A karakter jóllakottságának mértékének lekérdezésére szolgáló metódus.
     * @return a karakter jóllakottsága
     */
    float getHunger();

    /**
     * A karakter életerejének lekérdezésére szolgáló metódus.
     * @return a karakter életereje
     */
    float getHp();

    /**
     * A karakter inventory-jának lekérdezésére szolgáló metódus.
     * <br>
     * A karakter inventory-ja végig ugyanaz marad, amelyet referencia szerint kell visszaadni.
     * @return a karakter inventory-ja
     */
    BaseInventory getInventory();

    /**
     * A játékos aktuális pozíciójának lekérdezésére szolgáló metódus.
     * @return a játékos pozíciója
     */
    Position getCurrentPosition();

    /**
     * Az utolsó cselekvés lekérdezésére szolgáló metódus.
     * <br>
     * Egy létező Action-nek kell lennie, nem lehet null.
     * @return az utolsó cselekvés
     */
    Action getLastAction();

    /**
     * A játékos nevének lekérdezésére szolgáló metódus.
     * @return a játékos neve
     */
    String getName();

    /**
     * A játékos lekérdezése, hogy játékos-e vagy gépi játékos.
     * @return jétkos-e
     */
    boolean isPlayer();

    /**
     * A játékos elfogyaszt egy itemet az inventoryból.
     * @param index az megevendo targy inventory indexe
     */
    void eat(int index);

    /**
     * A játékos éhezése minden körben.
     */
    void hungerChar();

    /**
     * A játékos mozgásaanak megvalositasa.
     * @param map a jatek palyaja
     * @param direction a mozgas iranya
     */
    void step(Direction direction, GameWorld map);

    /**
     * A játékos sebessgégének kiszámítása az élete és a éhsége függvényében.
     */
    void calcSpeed();

    /**
     * A tamadas hatasara elet csokkenese.
     * @param type fegvertipusa.
     */
    void attackHP(ItemType type);

    /**
     * A lastAction settere.
     * @param action a beallitani kivant ertek
     */
    void setLastAction(Action action);

    /**
     * Ha az adott korben halt meg a jatekos, akkor igaz.
     * @return igaz ha az adott korben halt meg.
     */
    boolean isDiedThisRound();
}
