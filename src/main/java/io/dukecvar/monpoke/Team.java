package io.dukecvar.monpoke;

import io.dukecvar.monpoke.exceptions.*;

import java.util.HashMap;

/**
 * Structure of a team.  Contains Monpoke game characters including
 * which is selected for game play
 * @author Duke Cvar
 */
public class Team {
    private String id;
    private Monpoke chosenMonpoke = null;
    private HashMap<String, Monpoke> monpokesMap = new HashMap<>();

    /**
     *
     * @param id team id (name)
     */
    public Team(String id) {
        this.id = id;
    }

    /**
     *
     * @return Team ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Add a Monpoke to the team.
     * @param monpoke the Monpoke to add
     * @throws MonpokeException (a RuntimeException) if Monpoke with this Id is already on the team
     */
    public void addMonpoke(Monpoke monpoke) {
        if (monpokesMap.get(monpoke.getId()) != null) {
            throw new MonpokeException(monpoke.getId() + " already a part of the team");
        }
        monpokesMap.put(monpoke.getId(), monpoke);
    }

    /**
     * Selects a Monpoke for battle by Id
     * @param id Id of the desired Monpoke
     * @throws MonpokeException (a RuntimeException) if Monpoke isn't available or doesn't exist
     */
    public void chooseMonpoke(String id) {
        if(monpokesMap.isEmpty()) {
            throw new MonpokeException(this.id + " needs at least one Monpoke");
        }
        Monpoke mp = monpokesMap.get(id);
        if(mp == null) {
            throw new MonpokeException(id + " is not available on team " + this.id);
        }
        if(mp.isDefeated()) {
            throw new MonpokeException(id + " has been defeated.");
        }
        chosenMonpoke = mp;
    }

    /**
     * @return the chosen Monpoke
     * @throws MonpokeException (a RuntimeException) if no Monpoke has been chosen
     */
    public Monpoke getChosenMonpoke() {
        if(chosenMonpoke == null) {
            throw new MonpokeException("Team " + id + " Monpoke has not been chosen.");
        }
        return chosenMonpoke;
    }

    /**
     * Updates the team's chosen Monpoke by removing it if it has been defeated
     */
    public void updateTeam() {
        if (chosenMonpoke != null && chosenMonpoke.isDefeated()) {
            monpokesMap.remove(chosenMonpoke.getId());
            chosenMonpoke = null;
        }
    }

    /**
     * @return true if there are no more undefeated Monpoke on this team
     */
    public boolean isDefeated() {
        updateTeam();
        return monpokesMap.isEmpty();
    }
}
