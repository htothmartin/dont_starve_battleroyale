package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.character.actions.ActionStep;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.level.Level;
import prog1.kotprog.dontstarve.solution.utility.Direction;
import prog1.kotprog.dontstarve.solution.utility.Position;
import java.util.*;

/**
 * A játék működéséért felelős osztály.<br>
 * Az osztály a singleton tervezési mintát valósítja meg.
 */
public final class GameManager {

    /**
     * A játék térképe.
     */
    private GameWorld gameMap;
    /**
     * A játékosokat kezelő objektum.
     */
    private CharacterManager characters;
    /**
     * A tutorial be van-e állítva.
     */
    private boolean isTutorial;
    /**
     * A játék idő.
     */
    private int currentTime;
    /**
     * A játék már el van-e indítva.
     */
    private boolean gameStarted;
    /**
     * A játék befejezodott-e mar..
     */
    private boolean gameEnded;

    /**
     * Az osztályból létrehozott egyetlen példány (nem lehet final).
     */
    static private GameManager instance = new GameManager();

    /**
     * Random objektum, amit a játék során használni lehet.
     */
    private final Random random = new Random();

    /**
     * Az osztály privát konstruktora.
     */
    private GameManager() {
    }

    public static void main(String[] args) {

        Level l1 = new Level("level00.png");
        System.out.println(GameManager.instance.joinCharacter("Jani",true).toString());
        GameManager.instance.loadLevel(l1);
        GameManager.instance.joinCharacter("Jani",true);

        System.out.println(GameManager.instance.joinCharacter("Jani",true).toString());
        System.out.println(GameManager.instance.joinCharacter("pista",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pidsfsta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistsdfa",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistdsaa",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistdfa",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistfgha",false).toString());
        System.out.println(GameManager.instance.joinCharacter("piscxbta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pisreta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pibcsta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pisertta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("piujhsta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pishgjta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistrwea",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pisttrhfa",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistrtvca",false).toString());
        System.out.println(GameManager.instance.joinCharacter("piscczfta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pisretta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistytukja",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pishgkftta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pisthfeda",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistdfga",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pisertta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistretta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pihstda",false).toString());
        System.out.println(GameManager.instance.joinCharacter("piershfta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pifdgsta",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistxca",false).toString());
        System.out.println(GameManager.instance.joinCharacter("pistnhja",false).toString());
        instance.startGame();

        System.out.println(GameManager.getInstance().getPlayer().getInventory().toString());
        instance.setTutorial(false);
        for (int i = 0; i < 230; i++) {
            //instance.characters.hungerEveryRound();
            instance.tick(new ActionStep(Direction.RIGHT));
        }



    }


    /**
     * Az osztályból létrehozott példány elérésére szolgáló metódus.
     * @return az osztályból létrehozott példány
     */
    static public GameManager getInstance() {
        return instance;
    }

    /**
     * A létrehozott random objektum elérésére szolgáló metódus.
     * @return a létrehozott random objektum
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Egy karakter becsatlakozása a játékba.<br>
     * A becsatlakozásnak számos feltétele van:
     * <ul>
     *     <li>A pálya már be lett töltve</li>
     *     <li>A játék még nem kezdődött el</li>
     *     <li>Csak egyetlen emberi játékos lehet, a többi karaktert a gép irányítja</li>
     *     <li>A névnek egyedinek kell lennie</li>
     * </ul>
     * @param name a csatlakozni kívánt karakter neve
     * @param player igaz, ha emberi játékosról van szó; hamis egyébként
     * @return a karakter pozíciója a pályán, vagy (Integer.MAX_VALUE, Integer.MAX_VALUE) ha nem sikerült hozzáadni
     */
    public Position joinCharacter(String name, boolean player) {
        if(gameMap == null || characters == null || (characters.isPlayerJoined() && player) || characters.isJoined(name) || gameStarted){
            return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        List<Position> avaiblePos = new ArrayList<>();
        double threshold = 50.0;
        while(threshold >= 0){
            for (int i = 0; i < gameMap.getMapHeight(); i++) {
                for (int j = 0; j < gameMap.getMapWidth(); j++) {
                    if(characters.distanceValidation(i,j,threshold) && gameMap.getGameMap(j,i).isEmpty()){
                        avaiblePos.add(new Position(j,i));
                    }
                }
            }
            threshold -= 5.0;
        }
        if(avaiblePos.size() > 0){
            Collections.shuffle(avaiblePos);
            return characters.placeCharacter((int)avaiblePos.get(0).getY(), (int)avaiblePos.get(0).getX(), name, player);
        }

        return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Egy adott nevű karakter lekérésére szolgáló metódus.<br>
     * @param name A lekérdezni kívánt karakter neve
     * @return Az adott nevű karakter objektum, vagy null, ha már a karakter meghalt vagy nem is létezett
     */
    public BaseCharacter getCharacter(String name) {
        if(characters != null){
            return characters.getCharacter(name);
        }
        return null;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy hány karakter van még életben.
     * @return Az életben lévő karakterek száma
     */
    public int remainingCharacters() {
        if(characters != null){
            return characters.remaining();
        }
        return 0;
    }

    /**
     * Ezen metódus segítségével történhet meg a pálya betöltése.<br>
     * A pálya betöltésének azelőtt kell megtörténnie, hogy akár 1 karakter is csatlakozott volna a játékhoz.<br>
     * A pálya egyetlen alkalommal tölthető be, később nem módosítható.
     * @param level a fájlból betöltött pálya
     */
    public void loadLevel(Level level) {
        if(gameMap == null){
            gameMap = new GameWorld(level);
            characters = new CharacterManager();
        }
    }

    /**
     * A pálya egy adott pozícióján lévő mező lekérdezésére szolgáló metódus.
     * @param x a vízszintes (x) irányú koordináta
     * @param y a függőleges (y) irányú koordináta
     * @return az adott koordinátán lévő mező
     */
    public BaseField getField(int x, int y) {
        if(gameMap.isMapGenerated()){
            return gameMap.getGameMap(x, y);
        } else {
            return null;
        }
    }

    /**
     * A játék megkezdésére szolgáló metódus.<br>
     * A játék csak akkor kezdhető el, ha legalább 2 karakter már a pályán van,
     * és közülük pontosan az egyik az emberi játékos által irányított karakter.
     * @return igaz, ha sikerült elkezdeni a játékot; hamis egyébként
     */
    public boolean startGame() {
        if(gameMap != null && characters.getSize() >= 2 && characters.isPlayerJoined() && !gameStarted){
            gameStarted = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ez a metódus jelzi, hogy 1 időegység eltelt.<br>
     * A metódus először lekezeli a felhasználói inputot, majd a gépi ellenfelek cselekvését végzi el,
     * végül eltelik egy időegység.<br>
     * Csak akkor csinál bármit is, ha a játék már elkezdődött, de még nem fejeződött be.
     * @param action az emberi játékos által végrehajtani kívánt akció
     */
    public void tick(Action action) {
        if(!gameStarted || gameEnded){
            return;
        }
        BaseField field = getField((int)getPlayer().getCurrentPosition().getNearestWholePosition().getX(),(int)getPlayer().getCurrentPosition().getNearestWholePosition().getY());
        action.execute(getPlayer(), getMap(), field, getPlayer().getInventory());
        getPlayer().setLastAction(action);

        if(!isTutorial){
            characters.aiActionExecute();
        }

        gameMap.tickFire();
        characters.hungerEveryRound();
        currentTime++;
        if(getWinner() != null || characters.isPlayerDead()){
            gameEnded = true;
        }
    }

    /**
     * Ezen metódus segítségével lekérdezhető az aktuális időpillanat.<br>
     * A játék kezdetekor ez az érték 0 (tehát a legelső időpillanatban az idő 0),
     * majd minden eltelt időpillanat után 1-gyel növelődik.
     * @return az aktuális időpillanat
     */
    public int time() {
        return currentTime;
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük a játék győztesét.<br>
     * Amennyiben a játéknak még nincs vége (vagy esetleg nincs győztes), akkor null-t ad vissza.
     * @return a győztes karakter vagy null
     */
    public BaseCharacter getWinner() {
        return characters.getWinnerPlayer();
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük, hogy a játék elkezdődött-e már.
     * @return igaz, ha a játék már elkezdődött; hamis egyébként
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük, hogy a játék befejeződött-e már.
     * @return igaz, ha a játék már befejeződött; hamis egyébként
     */
    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * Ezen metódus segítségével beállítható, hogy a játékot tutorial módban szeretnénk-e elindítani.<br>
     * Alapértelmezetten (ha nem mondunk semmit) nem tutorial módban indul el a játék.<br>
     * Tutorial módban a gépi karakterek nem végeznek cselekvést, csak egy helyben állnak.<br>
     * A tutorial mód beállítása még a karakterek csatlakozása előtt történik.
     * @param tutorial igaz, amennyiben tutorial módot szeretnénk; hamis egyébként
     */
    public void setTutorial(boolean tutorial) {
        if(!isGameStarted()){
            isTutorial = tutorial;
        }

    }

    /**
     * Az emberi jétkos lekérése.
     * @return az emberi játékos
     */
    public BaseCharacter getPlayer(){
        if(characters == null || !characters.isPlayerJoined()){
            return null;
        }
        for (int i = 0; i < characters.getSize(); i++) {
            if(characters.getIndex(i).isPlayer()){
                return characters.getIndex(i);
            }
        }
        return null;
    }

    /**
     * A játék térképenek lekérése.
     * @return játék térkép kezelésere szolgáló objektum
     */
    public GameWorld getMap(){
        return gameMap;
    }

    /**
     * A jatekosok lekérése.
     * @return a krakaterek listaja.
     */
    public List<BaseCharacter> getCharacters(){
        return characters.getCharacters();
    }

}
