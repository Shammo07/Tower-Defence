package td.tower;

import td.Block;
import td.Game;
import td.monster.Monster;

import java.util.List;

/**
 * Catapult
 * 
 * A catapult works in the following way. It will target on
 * one monster among all monsters that are in range. When there
 * are more than one monsters in range, pick ANY monster with 
 * highest remaining health point.
 * 
 * Then, it hits the target monster and other monsters located in
 * its 8 neighthor adjacent cells. For example,
 * ----------------------
 * | a | b | c | e |
 * | d | f | g | h |
 * | i | j | k | l |  ...
 * | m | n | o | p |
 * ----------------------
 * * If g is the target monster, monsters <b, c, e, f, g, h, j, k, l>
 * will receive damage.
 * * If m is the target monster, monsters <i, j, m, n>
 * will receive damage.
 * 
 * Note: In the first example, even if monster b is out of the range 
 * of the Tower, as long as Tower can hit g, b will also receive damage.
 * 
 * Propoerty of Catapult:
 * Symbol : 'C'
 * Inital power: 4
 * Range : 6
 * cost : 7
 * upgrade power: 2 
 * upgrade cost: 3
 * 
 */
public class CatapultTower extends Tower {
    private char symbol;
    private int range = 6;

    public CatapultTower(int row, int col){
        super(row, col, 4,7,3,2,6);
        symbol = 'C';
    }

    public int getRange(){
        return range;
    }

    public char getSymbol(){
        return symbol;
    }

    public void action(List <Block> blocks) {
        int count = 0; //To store the number of monsters within range
        for (int i = 0; i < blocks.size(); i++)
            if (blocks.get(i) instanceof Monster && isInRange(blocks.get(i)))
                count++;

        // To store monsters in range and their corresponding index in the blocks ArrayList.
        Monster[] inRange = new Monster[count];
        int[] indexOfMonster = new int[count];

        int a = 0;
        for (int i = 0; i < blocks.size(); i++)
            if (blocks.get(i) instanceof Monster && isInRange(blocks.get(i))) {
                inRange[a] = (Monster) blocks.get(i);
                indexOfMonster[a] = i;
                a++;
            }

        int max = 0; // To find the monster with max health
        int indexToDamage = 0; // To find the index of the monster with max health
        for (int i = 0; i < inRange.length; i++)
            if (inRange[i].getHealth() > max) {
                max = inRange[i].getHealth();
                indexToDamage = indexOfMonster[i];
            }
        if (count > 0) {
            if (blocks.get(indexToDamage) instanceof Monster) {
                ((Monster) blocks.get(indexToDamage)).damage(getPower()); // Damage that monster

                // To find that monster's neighbors
                int x = blocks.get(indexToDamage).getRow();
                int y = blocks.get(indexToDamage).getCol();

                // Damage all the neighbors
                for (int i = 0; i < blocks.size(); i++)
                    if (blocks.get(i) instanceof Monster)
                        if (Math.abs(blocks.get(i).getRow() - x) == 1 && Math.abs(blocks.get(i).getCol() - y) == 1)
                            ((Monster) blocks.get(i)).damage(getPower());
                for (int i = 0; i < blocks.size(); i++)
                    if (blocks.get(i) instanceof Monster)
                        if (Math.abs(blocks.get(i).getRow() - x) == 1 && Math.abs(blocks.get(i).getCol() - y) == 0)
                            ((Monster) blocks.get(i)).damage(getPower());
                for (int i = 0; i < blocks.size(); i++)
                    if (blocks.get(i) instanceof Monster)
                        if (Math.abs(blocks.get(i).getRow() - x) == 0 && Math.abs(blocks.get(i).getCol() - y) == 1)
                            ((Monster) blocks.get(i)).damage(getPower());
            }
        }
    }
}
