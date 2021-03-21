package io.dukecvar.monpoke;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main class for starting the Monpoke game play
 * @author Duke Cvar
 */
public class Play {

    /**
     * Run game
     * @param args Empty of using console, otherwise 1st argument is used as filename.  All other arguments are ignored.
     */
    public static void main(String[] args) {
        if(args.length > 0) {
            runFromFile(args[0]);
        } else {
            runFromConsole();
        }
    }

    /**
     * Run game from input file
     * @param filename Name of input file
     */
    static void runFromFile(String filename) {
        Game game = new Game();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (!game.isGameOver() && scanner.hasNextLine()) {
                game.executeCommand(scanner.nextLine());
            }
            scanner.close();
            game.printOut();
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Run game by entering command via console
     */
    static void runFromConsole() {
        BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(System.in));
        try {
            Game game = new Game();
            String line = null;
            while (!game.isGameOver() && (line = bufReader.readLine()) != null) {
                game.executeCommand(line);
            }
            bufReader.close();
            game.printOut();
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


}
