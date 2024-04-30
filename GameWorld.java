package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.level.Level;

/**
 * A játék térképért felelős osztály.
 */
public class GameWorld {

    /**
     * A játék térképe.
     */
    private final BaseField[][] gameMap;

    /**
     * A pálya már be van-t töltve.
     */
    private final boolean mapGenerated;

    /**
     * Az osztály konstruktora.
     * Betölti a kép alapján a téképet.
     * @param level a térkép
     */
    public GameWorld(Level level){
        gameMap = new BaseField[level.getHeight()][level.getWidth()];
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                gameMap[i][j] = new Field(level.getColor(j,i));
            }
        }
        mapGenerated = true;
    }

    /**
     * A palya szelessegenek lekerese.
     * @return szelleseg
     */
    public int getMapWidth(){
        return gameMap[0].length;
    }

    /**
     * A palya magassaganak lekerese.
     * @return magassag
     */
    public int getMapHeight(){
        return gameMap.length;
    }

    /**
     * Az isMapgenerated gettere.
     * @return igaz, ha a playa mar be lett tölve
     */
    public boolean isMapGenerated() {
        return mapGenerated;
    }

    /**
     * Az gameMap gettere.
     * @param x a vizszintes kordinata.
     * @param y az fuggoleges kordinata.
     * @return gameMap
     */
    public BaseField getGameMap(int x, int y) {
        return gameMap[y][x];
    }

    /**
     * Az item begyüjtése a mezőröl.
     * @param field a palya azon mezoje amin a jatekos tartozkodik
     * @return a legelső item a listában, null ha a lista ures
     */
    public AbstractItem collectItem(BaseField field){
        AbstractItem item;
        if(field.items().length > 0){
            item = field.items()[0];
            field.removeDroppedItem(field.items()[0]);
            return item;
        }
        return null;
    }

    /**
     * Az paraméterban kapott kordináta érvényességének ellenőrzése.
     * @param x kordináta
     * @param y kordináta
     * @return igaz, ha a pozicio a palyan belul van kordináta, különben hamis
     */
    public boolean isValidPosition(int x, int y){
        return ((x >= 0) && (y >= 0) && (y < gameMap.length) && (x < gameMap[0].length) && gameMap[y][x].isWalkable());
    }

    /**
     * A lehehelyezett tábortüzek életének csökkentése.
     */
    public void tickFire(){
        for (BaseField[] baseFields : gameMap) {
            for (BaseField baseField : baseFields) {
                if (baseField.hasFire()) {
                    baseField.fireTime();
                }
            }
        }
    }

}
