package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.level.BaseField;

/**
 * A tárgy kézbe vétele akció leírására szolgáló osztály: egy inventory-ban lévő felvehető tárgy kézbe vétele.
 */
public class ActionEquip extends Action {
    /**
     * A felvenni kívánt tárgy pozíciója az inventory-ban.
     */
    private final int index;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index a felvenni kívánt tárgy pozíciója az inventory-ban
     */
    public ActionEquip(int index) {
        super(ActionType.EQUIP);
        this.index = index;
    }

    /**
     * Az index gettere.
     * @return a felvenni kívánt tárgy pozíciója az inventory-ban
     */
    public int getIndex() {
        return index;
    }

    /**
     * Item kézbe vétele.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        inventory.equipItem(index);
    }
}
