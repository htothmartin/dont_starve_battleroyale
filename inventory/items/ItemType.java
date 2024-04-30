package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A tárgy típusok enumja.
 */
public enum ItemType {
    /**
     * fejsze.
     */
    AXE(1, EatAble.NOTEATABLE),

    /**
     * csákány.
     */
    PICKAXE(1,EatAble.NOTEATABLE),

    /**
     * lándzsa.
     */
    SPEAR(1,EatAble.NOTEATABLE),

    /**
     * fáklya.
     */
    TORCH(1,EatAble.NOTEATABLE),

    /**
     * farönk.
     */
    LOG(15,EatAble.NOTEATABLE),

    /**
     * kő.
     */
    STONE(10,EatAble.NOTEATABLE),

    /**
     * gally.
     */
    TWIG(20,EatAble.NOTEATABLE),

    /**
     * nyers bogyó.
     */
    RAW_BERRY(10,EatAble.EATABLE),

    /**
     * nyers répa.
     */
    RAW_CARROT(10,EatAble.EATABLE),

    /**
     * főtt bogyó.
     */
    COOKED_BERRY(10,EatAble.EATABLE),

    /**
     * főtt répa.
     */
    COOKED_CARROT(10,EatAble.EATABLE),

    /**
     * tábortűz (inventory-ban nem tárolható!).
     */
    FIRE(0,EatAble.NOTEATABLE);

    /**
     * Maxérték.
     */
    private final int maxValue;
    /**
     * Ehető vag nem.
     */
    private final EatAble eatable;

    /**
     * Kontruktor.
     */
    ItemType(int maxValue, EatAble eatable){
        this.maxValue = maxValue;
        this.eatable = eatable;
    }

    /**
     * Max tárolható érték lekérdezése.
     * @return max tárolhatő érték
     */
    public int getMaxValue(){
        return maxValue;
    }

    /**
     * Ehetőség lkérdezése.
     * @return ehető-e
     */
    public boolean eatAble() {
        return this.eatable == EatAble.EATABLE;
    }
}
