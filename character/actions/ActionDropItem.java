package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.level.BaseField;

/**
 * Az item eldobás akció leírására szolgáló osztály: egy inventory-ban lévő item eldobása az aktuális mezőre.
 */
public class ActionDropItem extends Action {
    /**
     * Az eldobandó tárgy pozíciója az inventory-ban.
     */
    private final int index;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index az eldobandó tárgy pozíciója az inventory-ban
     */
    public ActionDropItem(int index) {
        super(ActionType.DROP_ITEM);
        this.index = index;
    }

    /**
     * Az index gettere.
     * @return az eldobandó tárgy pozíciója az inventory-ban
     */
    public int getIndex() {
        return index;
    }

    /**
     * Item eldobása.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        AbstractItem item = inventory.dropItem(index);

        if(item != null){
            mapPos.dropItem(item);
        }
    }
}
