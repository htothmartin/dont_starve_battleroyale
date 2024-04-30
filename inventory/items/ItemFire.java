package prog1.kotprog.dontstarve.solution.inventory.items;

import prog1.kotprog.dontstarve.solution.GameManager;

/**
 * A fire item leírására szolgáló osztály.
 */
public class ItemFire extends AbstractItem {
    /**
     * A tabortuz berkacsolasanak idopontja.
     */
    private int craftedTime;
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     */
    public ItemFire() {
        super(ItemType.FIRE, 1);
        craftedTime = GameManager.getInstance().time();
    }

    public boolean isFireWentOut(){
        return craftedTime + 60 == GameManager.getInstance().time();
    }

}
