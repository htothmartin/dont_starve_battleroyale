package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemAxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemPickaxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemSpear;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemTorch;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.level.BaseField;

/**
 * A kraftolás akció leírására szolgáló osztály: adott típusú item kraftolása.
 */
public class ActionCraft extends Action {
    /**
     * A lekraftolandó item típusa.
     */
    private final ItemType itemType;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param itemType a lekraftolandó item típusa
     */
    public ActionCraft(ItemType itemType) {
        super(ActionType.CRAFT);
        this.itemType = itemType;
    }

    /**
     * Az itemType gettere.
     * @return a lekraftolandó item típusa
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Item craftolása.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        CraftItem craft = new CraftItem(who, mapPos, inventory);
        switch (this.itemType) {
            case AXE -> craft.axe();
            case PICKAXE -> craft.pickaxe();
            case SPEAR -> craft.spear();
            case TORCH -> craft.torch();
            case FIRE -> {
                if(mapPos.isEmpty()){
                    craft.fire();
                }
            }
        }
    }

    /**
     * A kraftolásért felelős belső osztály.
     */
    private static class CraftItem{

        /**
         * Ki hajtja végre a cselekvést.
         */
        private final BaseCharacter who;
        /**
         * A player inventoryja.
         */
        private final BaseInventory inventory;

        /**
         * A player pozicioja.
         */
        private final BaseField pos;

        /**
         * Kostruktor.
         * @param who ki hajtja végre a cselekvést
         */
        protected CraftItem(BaseCharacter who, BaseField pos, BaseInventory inventory){
            this.who = who;
            this.inventory = inventory;
            this.pos = pos;
        }

        /**
         * Balta ktaftolása és hozzáadása az inventoryhoz.
         */
        private void axe(){
            if(inventory.getItem(ItemType.TWIG) >= 3){
                inventory.removeItem(ItemType.TWIG, 3);
                tryAddItem(new ItemAxe());
            }
        }

        /**
         * Csákány ktaftolása és hozzáadása az inventoryhoz.
         */
        private void pickaxe(){
            if(inventory.getItem(ItemType.TWIG) >= 2 && inventory.getItem(ItemType.LOG) >= 2){
                inventory.removeItem(ItemType.TWIG, 2);
                inventory.removeItem(ItemType.LOG, 2);
                tryAddItem(new ItemPickaxe());
            }
        }

        /**
         * Spear ktaftolása és hozzáadása az inventoryhoz.
         */
        private void spear(){
            if (inventory.getItem(ItemType.LOG) >= 2 && inventory.getItem(ItemType.STONE) >= 2){
                inventory.removeItem(ItemType.STONE, 2);
                inventory.removeItem(ItemType.LOG, 2);
                tryAddItem(new ItemSpear());
            }
        }

        /**
         * Fáklya ktaftolása és hozzáadása az inventoryhoz.
         */
        private void torch(){
            if(inventory.getItem(ItemType.LOG) >= 1 && inventory.getItem(ItemType.TWIG) >= 3){
                inventory.removeItem(ItemType.TWIG, 3);
                inventory.removeItem(ItemType.LOG, 1);
                tryAddItem(new ItemTorch());
            }
        }

        /**
         * Tábortűz ktaftolása és lehelyzése.
         */
        private void fire(){
            if(inventory.getItem(ItemType.TWIG) >= 2 && inventory.getItem(ItemType.LOG) >= 2 && inventory.getItem(ItemType.STONE) >= 4){
                inventory.removeItem(ItemType.TWIG,2);
                inventory.removeItem(ItemType.LOG,2);
                inventory.removeItem(ItemType.STONE,4);
                pos.setFire();
            }
        }

        /**
         * Megprobalja hozzaddni az inventoryhoz a lekraftolt itemet, ha nem sikerul kidobja az aktualis mezore.
         * @param item a lekraftolt item
         */
        private void tryAddItem(AbstractItem item){
            if(!inventory.addItem(item)){
                pos.dropItem(item);
            }
        }
    }

}
