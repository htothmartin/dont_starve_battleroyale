package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.character.actions.ActionNone;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.PlayerInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.utility.Direction;
import prog1.kotprog.dontstarve.solution.utility.Position;

/**
 * A karaktert megvalósító osztály.
 */
public class Character implements BaseCharacter{

    /**
     * A játékos sebessége.
     */
    private float speed;
    /**
     * A játékos éhsége.
     */
    private float hunger;
    /**
     * A játékos életereje.
     */
    private float hp;
    /**
     * A játékos inventoryja.
     */
    final private BaseInventory inventory;
    /**
     * A játékos poziciója.
     */
    private Position pos;
    /**
     * A játékos utolsó cselekvése.
     */
    private Action lastAction;
    /**
     * A játékos neve.
     */
    private final String name;
    /**
     * Emberi játékos.
     */
    private final boolean player;

    /**
     * Az aktualis korben halt-e meg a jatekos.
     */
    private boolean diedThisRound;

    /**
     * Az osztály konstruktora.
     * @param name játékos neve
     * @param player emberi játékos-e
     * @param pos játékos poziciója
     */
    public Character(String name, boolean player, Position pos){
        this.speed = 1;
        this.hunger = 100;
        this.hp = 100;
        inventory = new PlayerInventory();
        this.pos = pos;
        this.lastAction = new ActionNone();
        this.name = name;
        this.player = player;
    }

    /**
     * A karakter mozgási sebességének lekérdezésére szolgáló metódus.
     * @return a karakter mozgási sebessége
     */
    @Override
    public float getSpeed() {
        return speed;
    }

    /**
     * A karakter jóllakottságának mértékének lekérdezésére szolgáló metódus.
     * @return a karakter jóllakottsága
     */
    @Override
    public float getHunger() {
        return hunger;
    }

    /**
     * A karakter életerejének lekérdezésére szolgáló metódus.
     * @return a karakter életereje
     */
    @Override
    public float getHp() {
        return hp;
    }

    /**
     * A karakter inventory-jának lekérdezésére szolgáló metódus.
     * <br>
     * A karakter inventory-ja végig ugyanaz marad, amelyet referencia szerint kell visszaadni.
     * @return a karakter inventory-ja
     */
    @Override
    public BaseInventory getInventory() {
        return inventory;
    }

    /**
     * A játékos aktuális pozíciójának lekérdezésére szolgáló metódus.
     * @return a játékos pozíciója
     */
    @Override
    public Position getCurrentPosition() {
        return pos;
    }

    /**
     * Az utolsó cselekvés lekérdezésére szolgáló metódus.
     * <br>
     * Egy létező Action-nek kell lennie, nem lehet null.
     * @return az utolsó cselekvés
     */
    @Override
    public Action getLastAction() {
        return lastAction;
    }

    /**
     * A játékos nevének lekérdezésére szolgáló metódus.
     * @return a játékos neve
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * A játékos lekérdezése, hogy játékos-e vagy gépi játékos.
     * @return igaz, ha emberi jatekos, hamis ha nem
     */
    public boolean isPlayer() {
        return player;
    }

    /**
     * A játékos elfogyaszt egy itemet az inventoryból.
     * @param index az megevendo targy inventory indexe
     */
    public void eat(int index){
        if(inventory.getItem(index) == null || hunger == 100){
            return;
        }
        switch (inventory.getItem(index).getType()) {
            case RAW_BERRY -> {
                inventory.eatItem(index);
                regeneration(20, -5);
            }
            case RAW_CARROT -> {
                inventory.eatItem(index);
                regeneration(12, 1);
            }
            case COOKED_BERRY -> {
                inventory.eatItem(index);
                regeneration(10, 1);
            }
            case COOKED_CARROT -> {
                inventory.eatItem(index);
                regeneration(10, 3);
            }
        }
    }

    /**
     * A játékos éhezése minden körben.
     */
    public void hungerChar(){
        this.hunger -= 0.4;
        if(hunger < 0){
            hunger = 0;
        }
        if(hunger > 100){
            hunger = 100;
        }
        if(hunger == 0){
            hp -= 5;
        }
        if(hp < 0){
            hp = 0;
        }
        diedThisRound = false;
        if(inventory.equippedItem() != null && inventory.equippedItem().getType() == ItemType.TORCH && inventory.equippedItem().use()){
            inventory.breakItem();
        }
    }

    /**
     * A játékos mozgásaanak megvalositasa.
     * @param direction a mozgas iranya
     * @param map a jatek palyaja
     */
    public void step(Direction direction, GameWorld map){
        float dx = 0, dy = 0;
        switch (direction) {
            case UP -> dy += speed * -1;
            case DOWN -> dy += speed;
            case LEFT -> dx += speed * -1;
            case RIGHT -> dx += speed;
        }
        Position newPos = new Position(getCurrentPosition().getX() + dx, getCurrentPosition().getY() + dy);
        if(map.isValidPosition((int)newPos.getNearestWholePosition().getX(),(int)newPos.getNearestWholePosition().getY())){
            pos = newPos;
        }
    }

    /**
     * A játékos sebessgégének kiszámítása az élete és a éhsége függvényében.
     */
    public void calcSpeed(){
        speed = 1;
        if(hp < 50){
            if(hp >= 30){
                speed *= 0.9;
            } else if(hp >= 10){
                speed *= 0.75;
            } else if(hp > 0){
                speed *= 0.6;
            }
        }

        if(hunger < 50){
            if(hunger >= 20){
                speed *= 0.9;
            } else if(hunger > 0){
                speed *= 0.8;
            } else if(hunger == 0){
                speed *= 0.5;
            }
        }
    }

    /**
     * A játékos életrejének növelése étel elfogysztására.
     * @param hp az eletero modositas
     * @param hunger novelese ezzel az ertekkel
     */
    private void regeneration(int hunger, int hp){
        this.hunger += hunger;
        this.hp += hp;
        if(this.hp > 100){
            this.hp = 100;
        }
    }

    /**
     * A tamadas hatasara elet csokkenese.
     * @param type fegvertipusa.
     */
    public void attackHP(ItemType type){
        switch (type){
            case SPEAR -> hp -= 19;
            case AXE, PICKAXE -> hp -= 8;
            case TORCH -> hp -= 6;
            default -> hp -= 4;
        }
        if(hp <= 0){
            hp = 0;
            diedThisRound = true;
        }
    }

    /**
     * A lastAction settere.
     * @param action a beallitani kivant ertek
     */
    public void setLastAction(Action action){
        this.lastAction = action;
    }

    /**
     * Ha az adott korben halt meg a jatekos, akkor igaz.
     * @return igaz ha az adott korben halt meg.
     */
    public boolean isDiedThisRound(){
        return diedThisRound;
    }
}
