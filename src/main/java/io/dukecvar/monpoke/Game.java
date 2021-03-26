package io.dukecvar.monpoke;

import io.dukecvar.monpoke.exceptions.MonpokeException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class for running game commands and maintaining game state
 * @author Duke Cvar
 */
public class Game {
    Team[] teams = new Team[2]; // made package accessible for unit testing
    private int activeTeam = 0;
    private boolean inBattleStage = false;
    private boolean gameOver = false;
    private ArrayList<String> outputBuffer = new ArrayList<>();

    /**
     * Primary decisions for command execution
     * @param line Whole line of command input
     * @throws MonpokeException if command fails
     */
    void executeCommand(String line) {
        String[] lineSplit = line.split(" ");
        switch(lineSplit[0]) {
            case GameCommands.CREATE_INPUT:
                cmdCreate(lineSplit);
                break;
            case GameCommands.ICHOOSEYOU_INPUT:
                cmdIchooseYou(lineSplit);
                break;
            case GameCommands.ATTACK_INPUT:
                cmdAttack(lineSplit);
                break;
            case GameCommands.HEAL_INPUT:
                cmdHeal(lineSplit);
                break;
            default:
                throw new MonpokeException("Invalid command: " + lineSplit[0]);
        }
    }

    /**
     * Handles CREATE command execution
     * @param lineSplit Command input as an array of strings
     * @throws MonpokeException if Either team or Monpoke creation fails or game mode is in battle mode
     */
    void cmdCreate(String[] lineSplit) {
        if(inBattleStage) {
            throw new MonpokeException("In battle stage; Cannot create.");
        }
        Team team = getCreateTeam(lineSplit[1]);
        Monpoke mp = new Monpoke(
                lineSplit[2],
                Integer.parseInt(lineSplit[3]),
                Integer.parseInt(lineSplit[4]));
        team.addMonpoke(mp);
        out(GameCommands.createOutput(team, mp));
    }

    /**
     * Handles ICHOOSEYOU commands
     * @param lineSplit Command input as an array of strings
     * @throws MonpokeException If choosing a Monpoke fails
     */
    void cmdIchooseYou(String[] lineSplit) {
        inBattleStage = true;
        readyToPlay();
        teams[activeTeam].chooseMonpoke(lineSplit[1]);
        Monpoke mp = teams[activeTeam].getChosenMonpoke();
        switchActiveTeam();
        out(GameCommands.ichooseyouOutput(mp));
    }

    /**
     * Handles ATTACK commands
     * @param lineSplit Command input as an array of strings
     * @throws MonpokeException if attack command fails
     */
    void cmdAttack(String[] lineSplit) {
        inBattleStage = true;
        readyToPlay();
        Team attackTeam = teams[activeTeam];
        Team enemyTeam = teams[otherTeam()];
        Monpoke attackMonpoke = attackTeam.getChosenMonpoke();
        Monpoke enemyMonpoke = enemyTeam.getChosenMonpoke();

        enemyMonpoke.hit(attackMonpoke.getAttackPower());
        out(GameCommands.attackOutput(attackMonpoke, enemyMonpoke));
        if(enemyMonpoke.isDefeated()) {
            out(GameCommands.defeatedOutput(enemyMonpoke));
            enemyTeam.updateTeam();
        }
        if(enemyTeam.isDefeated()) {
            out(GameCommands.winnerOutput(attackTeam));
            gameOver = true;
        }
        switchActiveTeam();
    }

    /**
     * Handles HEAL commands
     * @param lineSplit Command input as an array of strings
     * @throws MonpokeException if attack command fails
     */
    void cmdHeal(String [] lineSplit) {
        readyToPlay();
        if (!inBattleStage) {
            throw new MonpokeException("generic");
        }
        int healPoints = Integer.parseInt(lineSplit[1]);
        Monpoke mp = teams[activeTeam].getChosenMonpoke();
        mp.heal(healPoints);
        switchActiveTeam();
        out(GameCommands.healOutput(mp, healPoints));
    }


    // helper methods

    /**
     * Helper method to get or create a team and assign it as one of the 2 game teams
     * @param teamId ID of team
     * @return the created Team
     * @throws MonpokeException if there are too many teams
     */
    Team getCreateTeam(String teamId) {
        Team team = null;
        if (teams[0] == null) {
            team = teams[0] = new Team(teamId);
        } else if (teams[0].getId().equals(teamId)) {
            team = teams[0];
        } else if (teams[1] == null) {
            team = teams[1] = new Team(teamId);
        } else if (teams[1].getId().equals(teamId)) {
            team = teams[1];
        } else {
            throw new MonpokeException("Too many teams");
        }
        return team;
    }

    /**
     * Switch to the other team for active game play
     */
    void switchActiveTeam() {
        activeTeam = otherTeam();
    }

    /**
     * Helper method to get the other team index
     * @return index of other team
     */
    private int otherTeam() {
        return activeTeam == 0 ? 1 : 0;
    }

    /**
     * Check for null teams, a minimum requirement for starting game play
     * @throws MonpokeException if setup is not complete
     */
    void readyToPlay() {
        if(teams[0] == null || teams[1] == null) {
            throw new MonpokeException("Game setup not complete");
        }
    }

    /**
     * Track application output
     * @param output Output message
     */
    private void out(String output) {
        outputBuffer.add(output);
        //System.out.println(output);
    }

    /**
     * Print out all game command responses to System.out
     */
    void printOut() {
        for(String s : outputBuffer) {
            System.out.println(s);
        }
    }

    /**
     * Helper method for tests to get the last output
     * @return last output written to the output buffer
     */
    String getLastOutput() {
        return outputBuffer.size() > 0 ? outputBuffer.get(outputBuffer.size() - 1) : "" ;
    }

    /**
     *
     * @return true if game over
     */
    boolean isGameOver() {
        return gameOver;
    }
}
