package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.GameWorld;
import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.level.BaseField;

import java.util.List;

/**
 * A támadás akció leírására szolgáló osztály: a legközelebbi karakter megtámadása.
 */
public class ActionAttack extends Action {
    /**
     * Az akció létrehozására szolgáló konstruktor.
     */
    public ActionAttack() {
        super(ActionType.ATTACK);
    }

    /**
     * Másik játékos megtámadása.
     * @param who ki hajtja végre a cselekvést
     * @param map a játék térképe
     * @param inventory a jatekos inventoryja
     * @param mapPos azon mezo amelyiken a jatekos all
     */
    @Override
    public void execute(BaseCharacter who, GameWorld map, BaseField mapPos, BaseInventory inventory) {
        double closestPlayerDistance = Double.MAX_VALUE;
        BaseCharacter closestPlayer = null;
        List<BaseCharacter> characters = GameManager.getInstance().getCharacters();
        for (BaseCharacter character : characters) {
            if (character.getHp() > 0) {
                if (character.equals(who)) {
                    continue;
                }
                double distance = Math.sqrt(Math.pow(who.getCurrentPosition().getX() - character.getCurrentPosition().getX(), 2)
                        + Math.pow(who.getCurrentPosition().getY() - character.getCurrentPosition().getY(), 2));
                if (distance <= 2.0 && distance < closestPlayerDistance) {
                    closestPlayerDistance = distance;
                    closestPlayer = character;
                }
            }
        }
        if(closestPlayer != null){
            attack(inventory, closestPlayer);
            if(closestPlayer.getHp() == 0){
                die(closestPlayer, map);
            }
        }
    }

    /**
     * Tamadas.
     * @param inventory tamdo jatekos invetoryja.
     * @param enemy enemy.
     */
    private void attack(BaseInventory inventory, BaseCharacter enemy){
        if(inventory.equippedItem() != null){
            ItemType type = inventory.equippedItem().getType();
            enemy.attackHP(type);
            if(type != ItemType.TORCH && inventory.equippedItem().use()){
                inventory.breakItem();
            }
        } else {
            enemy.attackHP(ItemType.TWIG);
        }
    }

    /**
     * Ellefel meghal.
     * @param who attacker.
     * @param map palya.
     */
    private void die(BaseCharacter who, GameWorld map){
        BaseField pos = map.getGameMap((int)who.getCurrentPosition().getNearestWholePosition().getX(), (int)who.getCurrentPosition().getNearestWholePosition().getY());
        BaseInventory inventory = who.getInventory();
        if(inventory.equippedItem() != null){
            pos.dropItem(inventory.equippedItem());
        }
        for (int i = 0; i < 10; i++) {
            if(inventory.getItem(i) != null){
                pos.dropItem(inventory.dropItem(i));
            }
        }
    }

}
