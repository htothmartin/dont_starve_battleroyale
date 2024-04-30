package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * Felvehető / kézbe vehető item leírására szolgáló osztály.
 */
public abstract class EquippableItem extends AbstractItem {

    /**
     * Az item elhasználódása.
     */
    float itemPercentage;
    /**
     * A mennyiszer használható még.
     */
    int durability;
    /**
     * A maximum használhatósága.
     */
    int maxDurability;
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param type   az item típusa
     */
    public EquippableItem(ItemType type, int durability) {
        super(type, 1);
        this.itemPercentage = 100;
        this.durability = durability;
        this.maxDurability = durability;
    }

    /**
     * Megadja, hogy milyen állapotban van a tárgy.
     * @return a tárgy használatlansága, %-ban (100%: tökéletes állapot)
     */
    public float percentage() {
        return itemPercentage;
    }

    /**
     * Az item használata.
     * Cökkenti a használhatóságát.
     * @return igaz, ha mar elhasznalodott, hamis ha nem
     */
    public boolean use(){
        durability--;
        itemPercentage = ((float)durability/maxDurability)*100;
        return itemPercentage == 0;
    }

}
