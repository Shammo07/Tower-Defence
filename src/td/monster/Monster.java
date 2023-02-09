package td.monster;

import td.Block;
import java.util.List;
import td.tower.Tower;
/**
 * Monster class.
 * 
 * On each turn it moves to the right by one cell. If it steps on 
 * a tower (i.e., share the same coordinate as the tower), the tower
 * will be destroyed.
 * 
 * A monster reaches home will end the game. 
 * 
 * If a monster has health point 0 or negative, it cannot move anymore
 * and shall be removed from the game.
 * 
 * A monster shall return the last digit of its health as its symbol.
 * For example, if a monster has a health 10, it should return the character '0'
 * If a monster has a health of 31, it should return the character '1'
 * 
 * 
 * There are some methods to be included in this class. Yet, you need to 
 * deduce what methods are needed from other java files.
 * 
 * 
 */
public class Monster extends Block {
    //TODO
    private int health;
    private char symbol;

    public Monster(int row, int col, int health){
        super(row,col);
        this.health = health;
    }

    //return health
    public int getHealth(){
        return health;
    }

    // return symbol
    public char getSymbol(){
        String h = "" + health;
        symbol = h.charAt(h.length() - 1);
        return symbol;
    }

    // damage the monster
    public void damage(int d){
        health = health - d;
    }

    public String toString(){
        return "Monster: " + getSymbol() + "[" + getHealth() + "]";
    }

    public void action(List <Block> blocks){
        // If it dies
        if (health <= 0) {
            this.remove();
        } else {
            col = this.getCol() + 1; // Walk
            for (int i = 0; i < blocks.size(); i++) //If a monster steps on a tower
                if (blocks.get(i) instanceof Tower && distance(blocks.get(i)) == 0){
                    blocks.get(i).remove();
                }
        }
    }

}
