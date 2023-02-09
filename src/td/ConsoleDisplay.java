package td;

import java.util.Scanner;

import td.tower.*;

/**
 * A View class. To display the information on screen and also 
 * to take user's control.
 */
public class ConsoleDisplay implements Displayable {
    /**
     * The controller object game.
     */
    protected Game game;

    /**
     * Entry point. Don't touch
     */
    public static void main(String[] args) {
        new ConsoleDisplay();
    }

    /**
     * Constructor. To construct the game object and call game.run();
     */
    public ConsoleDisplay() {
        this.game = new Game(this);
        game.run();
    }

    /**
     * To display the score, money, map and character on screen.
     */
    @Override
    public void display() {
        System.out.println("Score: " + game.getScore() + " | Money: " + game.getMoney()); //Print score and money

        //Print the board
        for (int i = 0; i < Game.WIDTH; i++)
            System.out.print('-');
        for (int i = 0; i < 4; i++)
            System.out.print('-');
        System.out.println();
        for (int x = 0; x < Game.HEIGHT; x++) {
            for (int y = 0; y < Game.WIDTH; y++) {
                if (game.getBlockByLocation(x, y) != null)
                    System.out.print(game.getBlockByLocation(x, y).getSymbol());
                else
                    System.out.print(' ');
            }
            System.out.println("oooo");
        }
    }
    /**
     * To accept user input (build tower, upgrade tower, view blocks).
     * 
     * This method has been done for you.
     * You should not modify it.
     * You are not allowed to modify it.
     */
    @Override
    public void userInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            printInstruction();
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        return;
                    default:
                        throw new InvalidInputException("Invalid option! Pick only 1-4");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            display();
        }
    }

    /**
     * Given method.
     * 
     * You are not supposed to change this method.
     * But you can change if you wish.
     */
    private void printInstruction() {
        System.out.println("Please pick one of the following: ");
        System.out.println("1. View a tower/monster");
        System.out.println("2. Build a new Tower");
        System.out.println("3. Upgrade a Tower");
        System.out.println("4. End a turn");
    }



    public void option1(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the coordinate of the tower/monster row followed by column");
            try {
                int r = in.nextInt();
                int c = in.nextInt();
                if (game.getBlockByLocation(r,c) != null){ // If there is a block
                    if (game.getBlockByLocation(r,c) instanceof Tower){ // If it is a tower
                        if (game.getBlockByLocation(r,c) instanceof LaserTower){ // if it is a laser tower
                            //Print its range
                            for (int i = 0; i < Game.WIDTH; i++)
                                System.out.print('-');
                            for (int i = 0; i < 4; i++)
                                System.out.print('-');
                            System.out.println();
                            for (int x = 0; x < Game.HEIGHT; x++) {
                                for (int y = 0; y < Game.WIDTH; y++) {
                                    if (game.getBlockByLocation(x, y) != null)
                                        System.out.print(game.getBlockByLocation(x, y).getSymbol());
                                    else if (y < c && x == r) // Within its range
                                        System.out.print('#');
                                    else
                                        System.out.print(' ');
                                }
                                System.out.println("oooo");
                            }
                            System.out.println(game.getBlockByLocation(r, c)); // Print the tower's details

                        } else if (game.getBlockByLocation(r,c) instanceof ArcheryTower){ // If it is an archery tower
                            //Print its range
                            for (int i = 0; i < Game.WIDTH; i++)
                                System.out.print('-');
                            for (int i = 0; i < 4; i++)
                                System.out.print('-');
                            System.out.println();
                            for (int x = 0; x < Game.HEIGHT; x++) {
                                for (int y = 0; y < Game.WIDTH; y++) {
                                    if (game.getBlockByLocation(x, y) != null)
                                        System.out.print(game.getBlockByLocation(x, y).getSymbol());
                                    else if (Math.abs(x - r) + Math.abs(y - c) <= ((ArcheryTower) game.getBlockByLocation(r,c)).getRange()) // Within its range
                                        System.out.print('#');
                                    else
                                        System.out.print(' ');
                                }
                                System.out.println("oooo");
                            }
                            System.out.println(game.getBlockByLocation(r, c)); // Print the tower's details

                        } else { // If it is a catapult tower
                            //Print its range
                            for (int i = 0; i < Game.WIDTH; i++)
                                System.out.print('-');
                            for (int i = 0; i < 4; i++)
                                System.out.print('-');
                            System.out.println();
                            for (int x = 0; x < Game.HEIGHT; x++) {
                                for (int y = 0; y < Game.WIDTH; y++) {
                                    if (game.getBlockByLocation(x, y) != null)
                                        System.out.print(game.getBlockByLocation(x, y).getSymbol());
                                    else if (Math.abs(x - r) + Math.abs(y - c) <= ((CatapultTower) game.getBlockByLocation(r,c)).getRange()) // Within its range
                                        System.out.print('#');
                                    else
                                        System.out.print(' ');
                                }
                                System.out.println("oooo");
                            }
                            System.out.println(game.getBlockByLocation(r, c)); // Print the tower's details
                        }
                    } else { // If it is a monster
                        System.out.println(game.getBlockByLocation(r, c)); //Print the monster's details
                    }
                } else { // If not a block
                    throw new InvalidInputException("null");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    public void option2(){
        Scanner sc = new Scanner(System.in);
        System.out.println("You can build the following towers:\n" +
                "1. ArcheryTower ($5); 2. LaserTower ($7);3. CatapultTower ($7)");
            try{
                int tower = sc.nextInt();
                System.out.println("Which row?");
                int r = sc.nextInt();
                System.out.println("Which column?");
                int c = sc.nextInt();

                if (!game.build(tower,r,c)) { // If cannot build
                    throw new InvalidInputException("Sorry, the option is invalid. " +
                            "Please check if you have enough money. " +
                            "You can only build on a cell without any monster or tower. You cannot build on column 0 too!");
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
    }

    public void option3(){
        Scanner in = new Scanner(System.in);
        try{
            System.out.println("Enter the row of your tower: ");
            int r = in.nextInt();
            System.out.println("Enter the column of your tower: ");
            int c = in.nextInt();
            if (!game.upgrade(r,c)){ //If upgrade isn't possible
                throw new InvalidInputException("Sorry, the option is invalid. " +
                        "Please check if you have enough money to upgrade and there is already a tower for you to upgrade.");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void gameOver() {
        System.out.println("Bye");
    }
}
