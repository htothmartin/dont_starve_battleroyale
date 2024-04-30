package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.character.Character;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.character.actions.ActionAttack;
import prog1.kotprog.dontstarve.solution.character.actions.ActionNone;
import prog1.kotprog.dontstarve.solution.character.actions.ActionStepAndAttack;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemLog;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemRawBerry;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemRawCarrot;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemStone;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemTwig;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.utility.Direction;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékosok működéséért felelős osztály.<br>
 */
public class CharacterManager {


    /**
     * A csatlakozott játékosokat tartalmazó lista.
     */
    final private List<BaseCharacter> characters;

    /**
     * Az emberi játékos csatlakozott-e.
     */
    private boolean playerJoined;

    /**
     * Default kostruktor.
     */
    public CharacterManager(){
        characters = new ArrayList<>();
    }

    /**
     * A isPlayerJoines gettere.
     * @return igaz ha a játkos már csatlakozott, hamis különben
     */
    public boolean isPlayerJoined() {
        return playerJoined;
    }

    /**
     * Új játékos hozzáadása.
     * @param c hozzáadni kívánt játékos
     */
    public void characterAdd(BaseCharacter c){
        characters.add(addRandomItems(c));
        if(!playerJoined && c.isPlayer()){
            playerJoined = true;
        }
    }

    /**
     *Random itemek hozzádása az inventoryhoz a játék elején.
     * @param character a játékos
     * @return a jatekos
     */
    public BaseCharacter addRandomItems(BaseCharacter character){
        for (int i = 0; i < 4; i++) {

            int r = GameManager.getInstance().getRandom().nextInt(5);

            switch (r) {
                case 0 -> character.getInventory().addItem(new ItemLog(1));
                case 1 -> character.getInventory().addItem(new ItemStone(1));
                case 2 -> character.getInventory().addItem(new ItemTwig(1));
                case 3 -> character.getInventory().addItem(new ItemRawBerry(1));
                case 4 -> character.getInventory().addItem(new ItemRawCarrot(1));
            }
        }

        return character;
    }

    /**
     * A paraméterben érkező névről eldöntjük-e hogy csatlakozott-e már.
     * @param name játékos neve
     * @return igaz, ha csatlakozott mar ilyen nevu jatekos, kuloben hamis
     */
    public boolean isJoined(String name){
        for (BaseCharacter c : characters) {
            if(c.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * A játékos lekérdezése név alapján.
     * @param name játékos neve
     * @return a megtalált játékos, null ha nincs ilyen játékos
     */
    public BaseCharacter getCharacter(String name){
        for (BaseCharacter c : characters) {
            if (name.equalsIgnoreCase(c.getName()) && c.getHp() > 0) {
                return c;
            }
        }
        return null;
    }

    /**
     * A csatlakozott játékosok számának lekérdezése.
     * @return játékosok száma
     */
    public int getSize(){
        return characters.size();
    }

    /**
     * Index alapján játékos lekérdezése a listából.
     * @param i index
     * @return az indexedik karakter.
     */
    public BaseCharacter getIndex(int i){
        return characters.get(i);
    }

    /**
     * Maradék életben lévő játékosok száma.
     * @return játékosok száma.
     */
    public int remaining(){
        int live = 0;
        for (BaseCharacter character : characters) {
            if (character.getHp() > 0) {
                live++;
            }
        }
        return live;
    }

    /**
     * Minden körben minden játékos éhezése.
     */
    public void hungerEveryRound(){
        for (BaseCharacter character : characters) {
            character.hungerChar();
            character.calcSpeed();
        }
    }

    /**
     * A gyoztes jatekos megkeresese.
     * @return a gyoztes jatekos
     */
    public BaseCharacter getWinnerPlayer(){
        if(remaining() == 1){
            for (BaseCharacter character : characters) {
                if (character.getHp() != 0) {
                    return character;
                }
            }
        }
        return null;
    }

    /**
     * Ezzel a metodussal lekerdezhetjuk, hogy az emberi jatekos meghalt-e mar.
     * @return igaz, ha mar meghalt, kulonben hamis
     */
    public boolean isPlayerDead(){
        for(BaseCharacter x : characters){
            if(x.isPlayer() && x.getHp() == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * A karater lehelyzésénél a térképen megfelelő távolságra megvalsító metódus.
     * @param i kordináta
     * @param j korináta
     * @param threshold a min távolság két játékos között
     * @return igaz, ha megfelőlő a távolság, külöben hamis
     */
    public boolean distanceValidation(int i, int j, double threshold){
        for (BaseCharacter character : characters) {
            float x = character.getCurrentPosition().getX();
            float y = character.getCurrentPosition().getY();

            double distance = Math.sqrt(Math.pow(j - x, 2) + Math.pow(i - y, 2));
            if (distance <= threshold || (i == Math.round(y) && j == Math.round(x))) {
                return false;
            }
        }
        return true;
    }

    /**
     * A gepi jatekosok csaelkvesenek vegrehajtasa.
     */
    public void aiActionExecute(){
        BaseCharacter player = GameManager.getInstance().getPlayer();
        for (BaseCharacter x : characters){
            if(!x.equals(player) && (x.getHp() > 0 || x.isDiedThisRound())){
                Action actionAI = aiCalcAction(x);
                BaseField field = GameManager.getInstance().getField((int)x.getCurrentPosition().getNearestWholePosition().getX(),
                        (int)x.getCurrentPosition().getNearestWholePosition().getY());
                actionAI.execute(x, GameManager.getInstance().getMap(), field, x.getInventory());
                x.setLastAction(actionAI);
            }
            if(x.getHp() == 0 && !x.isDiedThisRound()){
                x.setLastAction(new ActionNone());
            }
        }
    }

    /**
     * Meghatarozza az ai cselekveset.
     * @param c a cselekdni kivano jatekos.
     * @return a cselekves
     */
    private Action aiCalcAction(BaseCharacter c){
        float x = c.getCurrentPosition().getX();
        float y = c.getCurrentPosition().getY();
        float playerX = GameManager.getInstance().getPlayer().getCurrentPosition().getX();
        float playerY = GameManager.getInstance().getPlayer().getCurrentPosition().getY();
        float xdiff = x - playerX;
        float ydiff = y - playerY;
        if(Math.abs(xdiff) > Math.abs(ydiff)){
            if(Math.abs(x - playerX) > 2){
                if(x - playerX > 0){
                    return new ActionStepAndAttack(Direction.LEFT);
                }
                if(x - playerX < 0){
                    return new ActionStepAndAttack(Direction.RIGHT);
                }
            }
        } else {
            if(Math.abs(y - playerY) > 2){
                if(y - playerY > 0){
                    return new ActionStepAndAttack(Direction.UP);
                }
                if(y - playerY < 0){
                    return new ActionStepAndAttack(Direction.DOWN);
                }
            }
        }
        return new ActionAttack();
    }

    /**
     * A jatekos lehelyezese.
     * @param i az y kordinata
     * @param j az x kordinata
     * @param name a jatekos neve
     * @param player a emberi vagy gepi jatekos-e
     * @return a lehelyezett jatekos pozicioja
     */
    public Position placeCharacter(int i, int j, String name, boolean player){
        Position newPosition = new Position(j, i);
        characterAdd(new Character(name, player, newPosition));
        return newPosition;
    }

    /**
     * A jatekos lista.
     * @return jatkosokat tartalmazo lista.
     */
    public List<BaseCharacter> getCharacters() {
        return characters;
    }
}

