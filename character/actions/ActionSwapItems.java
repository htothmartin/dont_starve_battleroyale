package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.level.BaseField;

/**
 * A tárgyak cseréje akció leírására szolgáló osztály: az inventory-ban két itemet megcserélünk.
 */
public class ActionSwapItems extends Action {
    /**
     * A cserében részt vevő első tárgy indexe az inventory-ban.
     */
    private final int index1;

    /**
     * A cserében részt vevő második tárgy indexe az inventory-ban.
     */
    private final int index2;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index1 a cserében részt vevő első tárgy indexe az inventory-ban
     * @param index2 a cserében részt vevő második tárgy indexe az inventory-ban
     */
    public ActionSwapItems(int index1, int index2) {
        super(ActionType.SWAP_ITEMS);
        this.index1 = index1;
        this.index2 = index2;
    }

    /**
     * Az index1 gettere.
     * @return a cserében részt vevő első tárgy indexe az inventory-ban
     */
    public int getIndex1() {
        return index1;
    }

    /**
     * Az index2 gettere.
     * @return a cserében részt vevő második tárgy indexe az inventory-ban
     */
    public int getIndex2() {
        return index2;
    }

    /**
     * A itemek megcserélése az inventoryban.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        inventory.swapItems(index1,index2);
    }
}
