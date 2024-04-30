package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.level.BaseField;

/**
 * A tárgy mozgatása akció leírására szolgáló osztály: a tárgyat az inventory-ban egy másik pozícióra mozgatjuk.
 */
public class ActionMoveItem extends Action {
    /**
     * A mozgatni kívánt tárgy pozíciója az inventory-ban.
     */
    private final int oldIndex;

    /**
     * A mozgatni kívánt tárgy új pozíciója az inventory-ban.
     */
    private final int newIndex;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     * @param oldIndex a mozgatni kívánt tárgy pozíciója az inventory-ban
     * @param newIndex a mozgatni kívánt tárgy új pozíciója az inventory-ban
     */
    public ActionMoveItem(int oldIndex, int newIndex) {
        super(ActionType.MOVE_ITEM);
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
    }

    /**
     * az oldIndex gettere.
     * @return A mozgatni kívánt tárgy pozíciója az inventory-ban
     */
    public int getOldIndex() {
        return oldIndex;
    }

    /**
     * a newIndex gettere.
     * @return A mozgatni kívánt tárgy új pozíciója az inventory-ban
     */
    public int getNewIndex() {
        return newIndex;
    }

    /**
     * A item mozgatása az inventoryban.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        inventory.moveItem(oldIndex,newIndex);
    }
}
