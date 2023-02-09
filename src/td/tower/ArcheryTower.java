package td.tower;

import java.util.List;
import td.monster.Monster;
import td.Block;

/**
 * Archery 
 * 
 * The archery tower will aim only one monster that has positive, non-zero 
 * health point. If there are multiple monster that are in range,
 * pick the one that is nearest to "home".
 * 
 * Propoerty of Archery tower:
 * Symbol : 'A'
 * Inital power: 5
 * Range : 3
 * cost : 5
 * upgrade power: 1 
 * upgrade cost: 2
 */
public class ArcheryTower extends Tower {
    private char symbol;
    private int range = 3;

    public ArcheryTower(int row, int col){
        super(row,col,5,5,2,1,3);
        symbol = 'A';
    }

    // return range
    public int getRange(){
        return range;
    }

    //return symgol
    public char getSymbol(){
        return symbol;
    }

    public void action(List <Block> blocks){
        int count = 0; //To store the number of monsters within range
        for (int i = 0; i < blocks.size(); i++)
            if (blocks.get(i) instanceof Monster && isInRange(blocks.get(i)))
                count++;

        // To store monsters in range and their corresponding index in the blocks ArrayList.
        Monster[] inRange = new Monster[count];
        int[] indexOfMonster = new int[count];

        int a = 0;
        for (int i = 0; i < blocks.size(); i++)
            if (blocks.get(i) instanceof Monster && isInRange(blocks.get(i))){
                inRange[a] = (Monster) blocks.get(i);
                indexOfMonster[a] = i;
                a++;
            }
        int max = 0; // To find the monster closest to Home
        int indexToDamage = 0; // To find the index of the monster closest to home in the arrayList
        for (int i = 0; i < inRange.length; i++)
            if (inRange[i].getCol() > max) {
                max = inRange[i].getCol();
                indexToDamage = indexOfMonster[i];
            }
        if (count > 0)
            if (blocks.get(indexToDamage) instanceof Monster)
                ((Monster) blocks.get(indexToDamage)).damage(getPower()); // Deal damage to that monster
    }
}
