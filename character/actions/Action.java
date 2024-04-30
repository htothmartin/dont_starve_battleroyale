package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.level.BaseField;

/**
 * A karakterek egy akciójának leírására szolgáló osztály.
 */
public abstract class Action {
    /**
     * Az akció típusa.
     * @see ActionType
     */
    private final ActionType type;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     * @param type az akció típusa
     */
    public Action(ActionType type) {
        this.type = type;
    }

    /**
     * A type gettere.
     * @return az akció típusa
     */
    public ActionType getType() {
        return type;
    }

    /**
     * A cselekvés végrehajtása.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    abstract public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory);
}
