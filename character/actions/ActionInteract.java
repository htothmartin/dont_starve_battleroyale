package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemLog;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemRawBerry;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemRawCarrot;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemStone;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemTwig;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.level.BaseField;

/**
 * Az aktuális mezőn lévő tereptárggyal való interakcióba lépés (favágás, kőcsákányozás, gally / bogyó / répa leszedése)
 * leírására szolgáló osztály.
 */
public class ActionInteract extends Action {
    /**
     * Az akció létrehozására szolgáló konstruktor.
     */
    public ActionInteract() {
        super(ActionType.INTERACT);
    }

    /**
     * A mezőn lévő tárggyal való interakcióba lépés.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        EquippableItem equippedItem = inventory.equippedItem();
        Interact interact = new Interact(mapPos, inventory);

        if(mapPos.hasTree()) {
            interact.tree(equippedItem);
        }
        if(mapPos.hasStone()){
            interact.stone(equippedItem);
        }
        if(mapPos.hasTwig()){
            interact.twig();
        }

        if(mapPos.hasBerry()){
            interact.berry();
        }

        if(mapPos.hasCarrot()){
            interact.carrot();
        }
    }

    /**
     * Az interakciot megvalosito belso osztaly.
     */
    private class Interact {
        /**
         * Az aktualis mezo addattagja.
         */
        private final BaseField mapPos;
        /**
         * Az vegrahjto jatekos inventoyja.
         */
        private final BaseInventory inventory;

        /**
         * Konstruktor.
         * @param field az aktualis mezo
         * @param inventory a jatekos invetoryja
         */
        protected Interact(BaseField field, BaseInventory inventory){
            this.mapPos = field;
            this.inventory = inventory;

        }

        /**
         * A ko es a fa interakcioja.
         * @param maxTick a kiuteshez szukseges interakciosza
         * @param item a sikeres interakciokor kidobando item
         */

        private void interactField(int maxTick, AbstractItem item) {
            mapPos.tickInteracted();
            if (inventory.equippedItem().use()) {
                inventory.breakItem();
            }
            if (mapPos.getTick() == maxTick) {
                mapPos.setEmpty();
                mapPos.dropItem(item);
            }
        }

        /**
         * A bogyo, a gally es a carrot begyujtese.
         * @param maxTick a kiuteshez szukseges interakcio
         * @param item a sikeres interakciokor hozzadando item
         */
        private void interactFieldAnother(int maxTick, AbstractItem item) {
            mapPos.tickInteracted();
            if (mapPos.getTick() == maxTick) {
                mapPos.setEmpty();
                inventory.addItem(item);
                if (item.getAmount() > 0) {
                    mapPos.dropItem(item);
                }
            }
        }

        /**
         * A fa interakció.
         */
        protected void tree(EquippableItem equippedItem) {
            if (equippedItem != null && equippedItem.getType() == ItemType.AXE) {
                AbstractItem log = new ItemLog(2);
                interactField(4, log);
            }
        }

        /**
         * A kő interakció.
         */
        protected void stone(EquippableItem equippedItem) {
            if (equippedItem != null && equippedItem.getType() == ItemType.PICKAXE) {
                AbstractItem stone = new ItemStone(3);
                interactField(5, stone);
            }
        }

        /**
         * A Twig interakció.
         */
        protected void twig() {
            AbstractItem twig = new ItemTwig(1);
            interactFieldAnother(2, twig);
        }

        /**
         * A bogyó interakció.
         */
        protected void berry() {
            AbstractItem berry = new ItemRawBerry(1);
            interactFieldAnother(1, berry);
        }

        /**
         * A répa interakció.
         */
        protected void carrot() {
            AbstractItem carrot = new ItemRawCarrot(1);
            interactFieldAnother(1, carrot);
        }
    }
}
