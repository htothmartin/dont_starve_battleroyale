package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * Egy általános itemet leíró osztály.
 */
public abstract class AbstractItem implements Cloneable{
    /**
     * Az item típusa.
     * @see ItemType
     */
    private final ItemType type;

    /**
     * Az item mennyisége.
     */
    private int amount;

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     * @param type az item típusa
     * @param amount az item mennyisége
     */
    public AbstractItem(ItemType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    /**
     * Egy klón objektum létrehozása.
     * @return a klón objektum
     */
    public AbstractItem clone(){
        try{
            return (AbstractItem)super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * A type gettere.
     * @return a tárgy típusa
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Az amount gettere.
     * @return a tárgy mennyisége
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Az amount settere.
     * @param amount a tárgy mennyisége
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
