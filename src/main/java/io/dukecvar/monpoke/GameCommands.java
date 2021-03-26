package io.dukecvar.monpoke;

/**
 * Class with static members and methods for game command input and output
 * @author Duke Cvar
 */
public class GameCommands {

    /** CREATE input command */
    public static final String CREATE_INPUT = "CREATE";
    /** CREATE output */
    public static final String CREATE_OUTPUT = "{0} has been assigned to team {1}!";
    /** ATTACK command */
    public static final String ATTACK_INPUT = "ATTACK";
    /** ATTACK output */
    public static final String ATTACK_OUTPUT = "{0} attacked {1} for {2} damage!";
    /** ICHOOSEYOU input command */
    public static final String ICHOOSEYOU_INPUT = "ICHOOSEYOU";
    /** ICHOOSEYOU output */
    public static final String ICHOOSEYOU_OUTPUT = "{0} has entered the battle!";
    /** Defeated output */
    public static final String DEFEATED_OUTPUT = "{0} has been defeated!";
    /** Winner output */
    public static final String WINNER_OUTPUT = "{0} is the winner!";
    /** HEAL in put command */
    public static final String HEAL_INPUT = "HEAL";
    /** HEAL output "<MONPOKE> healed <HEALAMOUNT> to <NEWHEALTH>" */
    public static final String HEAL_OUTPUT = "{0} healed {1} to {2}";


    /**
     * @param team Team object
     * @param monpoke Monpoke object
     * @return {monpoke-id} has been assigned to team {team-id}!"
     */
    public static String createOutput(Team team, Monpoke monpoke) {
        return CREATE_OUTPUT
                .replace("{0}", monpoke.getId())
                .replace("{1}", team.getId());
    }

    /**
     * @param attacker Monpoke object of attacker
     * @param enemy Monpoke object for the enemy
     * @return "{current-monpoke-id} attacked {enemy-monpoke-id} for {current-monpoke-attack-points} damage!"
     */
    public static String attackOutput(Monpoke attacker, Monpoke enemy) {
        return ATTACK_OUTPUT
                .replace("{0}", attacker.getId())
                .replace("{1}", enemy.getId())
                .replace("{2}", Integer.toString(attacker.getAttackPower()));
    }

    /**
     * @param monpoke Monpoke object
     * @return "{monpoke-id} has entered the battle!"
     */
    public static String ichooseyouOutput(Monpoke monpoke) {
        return ICHOOSEYOU_OUTPUT
                .replace("{0}", monpoke.getId());
    }

    /**
     * @param monpoke Monpoke object
     * @return "{enemy-monpoke-id} has been defeated!"
     */
    public static String defeatedOutput(Monpoke monpoke) {
        return DEFEATED_OUTPUT
                .replace("{0}", monpoke.getId());
    }

    /**
     * @param team Team object
     * @return "{team-id} is the winner!"
     */
    public static String winnerOutput(Team team) {
        return WINNER_OUTPUT
                .replace("{0}", team.getId());
    }

    /**
     *
     */
    public static String healOutput(Monpoke monpoke, int healAmount) {
        return HEAL_OUTPUT
                .replace("{0}", monpoke.getId())
                .replace("{1}", Integer.toString(healAmount))
                .replace("{2}", Integer.toString(monpoke.getHitPoints()));
    }
}