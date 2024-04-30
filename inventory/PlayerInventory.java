package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

/**
 * A BaseInventoryt implementáló osztály.
 */
public class PlayerInventory implements BaseInventory{

    /**
     * Az inventory.
     */
    private final AbstractItem[] inventory;
    /**
     * A játékos kezében tartott item.
     */
    private EquippableItem equipedItem;


    /**
     * Az osztályból def kontruktora.
     */
    public PlayerInventory(){
        inventory = new AbstractItem[10];
    }

    /**
     * Egy item hozzáadása az inventory-hoz.<br>
     * Ha a hozzáadni kívánt tárgy halmozható, akkor a meglévő stack-be kerül (ha még fér, különben új stacket kezd),
     * egyébként a legelső új helyre kerül.<br>
     * Ha egy itemből van több megkezdett stack, akkor az inventory-ban hamarabb következőhöz adjuk hozzá
     * (ha esetleg ott nem fér el mind, akkor azt feltöltjük, és utána folytatjuk a többivel).<br>
     * Ha az adott itemből nem fér el mind az inventory-ban, akkor ami elfér azt adjuk hozzá, a többit pedig nem
     * (ebben az esetben a hívó félnek tudnia kell, hogy mennyit nem sikerült hozzáadni).
     * @param item a hozzáadni kívánt tárgy
     * @return igaz, ha sikerült hozzáadni a tárgyat teljes egészében; hamis egyébként
     */
    @Override
    public boolean addItem(AbstractItem item) {
        int amount = item.getAmount();
        if(item instanceof EquippableItem){
            for (int i = 0; i < inventory.length; i++) {
                if(inventory[i] == null){
                    inventory[i] = item;
                    return true;
                }
            }
            return false;
        }
        for (AbstractItem abstractItem : inventory) {
            ItemType type;
            int currentAmount;
            if(abstractItem != null){
                type = abstractItem.getType();
                currentAmount = abstractItem.getAmount();
            } else {
                continue;
            }
            if (type == item.getType()) {
                int leftSpace = type.getMaxValue() - currentAmount;
                if (amount > leftSpace) {
                    amount -= leftSpace;
                    abstractItem.setAmount(type.getMaxValue());

                } else {
                    abstractItem.setAmount(currentAmount + amount);
                    item.setAmount(0);
                    return true;
                }
            }
        }

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                ItemType type = item.getType();
                if (amount > type.getMaxValue()) {
                    inventory[i] = item.clone();
                    inventory[i].setAmount(type.getMaxValue());
                    amount -= type.getMaxValue();
                } else {
                    inventory[i] = item.clone();
                    inventory[i].setAmount(amount);
                    item.setAmount(0);
                    return true;
                }
            }
        }

        item.setAmount(amount);
        return false;
    }

    /**
     * Egy tárgy kidobása az inventory-ból.
     * Hatására a tárgy eltűnik az inventory-ból.
     * @param index a slot indexe, amelyen lévő itemet szeretnénk eldobni
     * @return az eldobott item
     */
    @Override
    public AbstractItem dropItem(int index) {
        if(indexTest(index)){
            return null;
        }

        AbstractItem drop;
        drop = inventory[index];
        inventory[index] = null;
        return drop;
    }

    /**
     * Bizonyos mennyiségű, adott típusú item törlése az inventory-ból. A törölt itemek véglegesen eltűnnek.<br>
     * Ha nincs elegendő mennyiség, akkor nem történik semmi.<br>
     * Az item törlése a legkorábban lévő stackből (stackekből) történik, akkor is, ha van másik megkezdett stack.<br>
     * @param type a törlendő item típusa
     * @param amount a törlendő item mennyisége
     * @return igaz, amennyiben a törlés sikeres volt
     */
    @Override
    public boolean removeItem(ItemType type, int amount) {
        if(howMany(type) < amount){
            return false;
        }

        for (int i = 0; i < inventory.length; i++) {
            if(inventory[i] != null && inventory[i].getType() == type){
                int currentAmount = inventory[i].getAmount();
                if(currentAmount >= amount && currentAmount-amount >= 0){
                    inventory[i].setAmount(currentAmount-amount);
                    if(inventory[i].getAmount() <= 0){
                        inventory[i] = null;
                    }
                    return true;
                } else{
                    amount -= currentAmount;
                    inventory[i] = null;
                }
            }
        }
        return false;
    }

    /**
     * Két item pozíciójának megcserélése az inventory-ban.<br>
     * Csak akkor használható, ha mind a két pozíción már van item.
     * @param index1 a slot indexe, amelyen az első item található
     * @param index2 a slot indexe, amelyen a második item található
     * @return igaz, ha sikerült megcserélni a két tárgyat; hamis egyébként
     */
    @Override
    public boolean swapItems(int index1, int index2) {
        if(indexTest(index1) || indexTest(index2) || inventory[index1] == null || inventory[index2] == null){
            return false;
        }

        AbstractItem swap;
        swap = inventory[index1];
        inventory[index1] = inventory[index2];
        inventory[index2] = swap;

        return true;
    }

    /**
     * Egy item átmozgatása az inventory egy másik pozíciójára.<br>
     * Csak akkor használható, ha az eredeti indexen van tárgy, az új indexen viszont nincs.
     * @param index a mozgatni kívánt item pozíciója az inventory-ban
     * @param newIndex az új pozíció, ahova mozgatni szeretnénk az itemet
     * @return igaz, ha sikerült a mozgatás; hamis egyébként
     */
    @Override
    public boolean moveItem(int index, int newIndex) {
        if(indexTest(index) || indexTest(newIndex)){
            return false;
        }
        if(inventory[index] == null || inventory[newIndex] != null){
            return false;
        }
        inventory[newIndex] = inventory[index];
        inventory[index] = null;
        return true;

    }

    /**
     * Két azonos típusú tárgy egyesítése.<br>
     * Csak stackelhető tárgyakra használható. Ha a két stack méretének összege a maximális stack méreten belül van,
     * akkor az egyesítés az első pozíción fog megtörténni. Ha nem, akkor az első pozíción lévő stack maximálisra
     * töltődik, a másikon pedig a maradék marad.<br>
     * @param index1 első item pozíciója az inventory-ban
     * @param index2 második item pozíciója az inventory-ban
     * @return igaz, ha sikerült az egyesítés (változott valami a művelet hatására)
     */
    @Override
    public boolean combineItems(int index1, int index2) {
        if(isCombinable(index1, index2)){
            return false;
        }
        ItemType typeIndex1 = inventory[index1].getType();
        int amountIndex1 = inventory[index1].getAmount();
        int amountIndex2 = inventory[index2].getAmount();
        if((amountIndex1 + amountIndex2) <= typeIndex1.getMaxValue()){
            inventory[index1].setAmount(amountIndex1+amountIndex2);
            inventory[index2] = null;
        } else{
            int diff = typeIndex1.getMaxValue() - amountIndex1;
            inventory[index1].setAmount(typeIndex1.getMaxValue());
            inventory[index2].setAmount(amountIndex2-diff);
        }
        return true;
    }

    /**
     * Egy item felvétele a karakter kezébe.<br>
     * Csak felvehető tárgyra használható. Ilyenkor az adott item a karakter kezébe kerül.
     * Ha a karakternek már tele volt a keze, akkor a kezében lévő item a most felvett item helyére kerül
     * (tehát gyakorlatilag helyet cserélnek).
     * @param index a kézbe venni kívánt tárgy pozíciója az inventory-ban
     * @return igaz, amennyiben az itemet sikerült a kezünkbe venni
     */
    @Override
    public boolean equipItem(int index) {
        if(indexTest(index) || inventory[index] == null || !(inventory[index] instanceof EquippableItem)){
            return false;
        }

        EquippableItem swap;
        if(this.equipedItem == null){
            this.equipedItem = (EquippableItem)inventory[index];
            inventory[index] = null;
            return true;
        } else{
            swap = (EquippableItem) inventory[index];
            inventory[index] = this.equipedItem;
            this.equipedItem = swap;
            return true;
        }
    }

    /**
     * A karakter kezében lévő tárgy inventory-ba helyezése.<br>
     * A karakter kezében lévő item az inventory első szabad pozíciójára kerül.
     * Ha a karakternek üres volt a keze, akkor nem történik semmi.<br>
     * Ha nincs az inventory-ban hely, akkor a levett item a pálya azon mezőjére kerül, ahol a karakter állt.
     * @return a levetett item, amennyiben az nem fért el az inventory-ban; null egyébként
     */
    @Override
    public EquippableItem unequipItem() {
        if(equipedItem == null){
            return null;
        }

        EquippableItem swap;

        if(emptySlots() == 0){
            swap = equipedItem;
            equipedItem = null;
            return swap;
        }

        if(emptySlots() > 0){
            for (int i = 0; i < inventory.length; i++) {
                if(inventory[i] == null){
                    inventory[i] = equipedItem;
                    equipedItem = null;
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Egy item megfőzése.<br>
     * Csak nyers étel főzhető meg. Hatására az inventory adott pozíciójáról 1 egységnyi eltűnik.
     * @param index A megfőzni kívánt item pozíciója az inventory-ban
     * @return A megfőzni kívánt item típusa
     */
    @Override
    public ItemType cookItem(int index) {
        if(indexTest(index) || inventory[index] == null){
            return null;
        }

        ItemType type = inventory[index].getType();
        int amount = inventory[index].getAmount();
        if(type.eatAble() && (type == ItemType.RAW_BERRY || type == ItemType.RAW_CARROT)){
            if(amount == 1){
                inventory[index] = null;
            } else{
                inventory[index].setAmount(amount-1);
            }
            return type;
        }
        return null;
    }

    /**
     * Egy item elfogyasztása.<br>
     * Csak ételek ehetők meg. Hatására az inventory adott pozíciójáról 1 egységnyi eltűnik.
     * @param index A megenni kívánt item pozíciója az inventory-ban
     * @return A megenni kívánt item típusa
     */
    @Override
    public ItemType eatItem(int index) {
        if(indexTest(index) || inventory[index] == null){
            return null;
        }

        ItemType type = inventory[index].getType();
        int amount = inventory[index].getAmount();

        if(type.eatAble()){
            if(amount == 1){
                inventory[index] = null;
            } else{
                inventory[index].setAmount(amount-1);
            }
            return type;
        }

        return null;
    }

    /**
     * A rendelkezésre álló üres inventory slotok számának lekérdezése.
     * @return az üres inventory slotok száma
     */
    @Override
    public int emptySlots() {
        int empty = 0;
        for (AbstractItem abstractItem : inventory) {
            if (abstractItem == null) {
                empty++;
            }
        }
        return empty;
    }

    /**
     * Az aktuálisan viselt tárgy lekérdezése.<br>
     * Ha a karakter jelenleg egy tárgyat sem visel, akkor null.<br>
     * @return Az aktuálisan viselt tárgy
     */
    @Override
    public EquippableItem equippedItem() {
        return this.equipedItem;
    }

    /**
     * Adott inventory sloton lévő tárgy lekérdezése.<br>
     * Az inventory-ban lévő legelső item indexe 0, a következőé 1, és így tovább.<br>
     * Ha az adott pozíció üres, akkor null.<br>
     * @param index a lekérdezni kívánt pozíció
     * @return az adott sloton lévő tárgy
     */
    @Override
    public AbstractItem getItem(int index) {
        if(indexTest(index)){
            return null;
        }
        return inventory[index];
    }

    /**
     Visszaadja a tárolóban található adott típusú elemek összes mennyiségét.
     @param type Az elem típusa, amelynek az összes mennyiségét megszeretnénk kapni.
     @return Az adott típusú elemek összes mennyisége a tárolóban.
     */
    public int getItem(ItemType type){
        int amount = 0;
        for (AbstractItem abstractItem : inventory) {
            if (abstractItem != null && abstractItem.getType() == type) {
                amount += abstractItem.getAmount();
            }
        }
        return amount;
    }

    /**
     Tesztelésre szolgáló metódus.
     @return Az inventory tartalma.
     */
    public String toString(){
        String string = "";
        for (AbstractItem abstractItem : inventory) {
            if (abstractItem != null){
                System.out.println(abstractItem.getType().toString() + " " + abstractItem.getAmount() + System.lineSeparator());
            }
        }
        return string;
    }

    /**
     * A kezben levo item elhasznalodasa.
     */
    public void breakItem(){
        equipedItem = null;
    }

    /**
     Az invetory tömb index tesztelése, hogy ne hivatkozzunk a tömmbön kívülre.
     @param index a tesztelendő szám.
     @return Megfelelő-e az index.
     */
    private boolean indexTest(int index){
        return index < 0 || index > 9;
    }

    /**
     Megszámolja mennyi van az inventoryban a paraméterben kapott item típusból.
     @param type typus.
     @return mennyiség.
     */
    private int howMany(ItemType type){
        int max = 0;
        for (AbstractItem abstractItem : inventory) {
            if (abstractItem != null && abstractItem.getType() == type) {
                max += abstractItem.getAmount();
            }
        }
        return max;
    }

    /**
     * Megnezi, hogy egyesitheto a ket indexen levo item.
     * @param index1 erre az indexre egyesites
     * @param index2 egyesiteni kivant item indexe
     * @return igaz, ha egyesitheto, hamis ha nem
     */
    private boolean isCombinable(int index1, int index2){
        return indexTest(index1) || indexTest(index2) || inventory[index1] == null || inventory[index2] == null
                || inventory[index1].getType() != inventory[index2].getType()
                || inventory[index1] instanceof EquippableItem || inventory[index2] instanceof EquippableItem
                || inventory[index1].getAmount() == inventory[index1].getType().getMaxValue();
    }
}
