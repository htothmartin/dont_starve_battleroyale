package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.utility.Direction;

/**
 * A lépés és támadás akció leírására szolgáló osztály: a karakter egy lépést tesz balra, jobbra, fel vagy le,
 * majd megtámadja a legközelebbi karaktert.
 */
public class ActionStepAndAttack extends Action {
    /**
     * A mozgás iránya.
     */
    private final Direction direction;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     */
    public ActionStepAndAttack(Direction direction) {
        super(ActionType.STEP_AND_ATTACK);
        this.direction = direction;
    }

    /**
     * Az irány lekérdezésére szolgáló getter.
     * @return a mozgás iránya
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * A lépés és támadás.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        who.step(direction, map);
        new ActionAttack().execute(who,map, mapPos, inventory);
    }
}
