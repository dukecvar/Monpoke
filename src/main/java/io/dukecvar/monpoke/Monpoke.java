package io.dukecvar.monpoke;

import io.dukecvar.monpoke.exceptions.MonpokeException;

/**
 * @author Duke Cvar
 *
 * Simple pojo for the Monpoke game's characters.
 *
 */
public class Monpoke {
    // members
    private String id;
    private int hitPoints;
    private int originalHitPoints;
    private int attackPower;

    /**
     * Sole constructor.  All needed fields set here
     *
     * @param id name Monpoke
     * @param hitPoints - hit points
     * @param attackPower - attack power
     * @throws MonpokeException if hitPoints or attackPower are less than zero
     */
    public Monpoke(String id, int hitPoints, int attackPower) {
        if(hitPoints < 1) {
            throw new MonpokeException(id + " has insufficient hit points (" + hitPoints + ")");
        } else if (attackPower < 1) {
            throw new MonpokeException(id + " has insufficient attack power (" + attackPower + ")");
        }
        this.id = id;
        this.hitPoints = hitPoints;
        this.originalHitPoints = hitPoints;
        this.attackPower = attackPower;
    }

    //
    // Accessors
    //

    /**
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     *
     * @return attack power
     */
    public int getAttackPower() {
        return attackPower;
    }

    //
    // worker methods
    //

    /**
     * Attack this character with $hitPoints worth of damage
     * @param hitPoints this character's hit points will be reduced by this ammount
     */
    public void hit(int hitPoints) {
        if (hitPoints > this.hitPoints) {
            this.hitPoints = 0;
        } else {
            this.hitPoints -= hitPoints;
        }
    }

    /**
     *
     * @param healPoints
     */
    public void heal(int healPoints) {
        if (hitPoints <= 0) {
            throw new MonpokeException("Can't heal the dead, sicko");
        }
        if(healPoints < 0) {
            healPoints *= -1;
        }
        hitPoints = hitPoints + healPoints;
        if (hitPoints > originalHitPoints) {
            hitPoints = originalHitPoints;
        }
    }

    /**
     *
     * @return true if this Monpoke has no more hit points
     */
    public boolean isDefeated() {
        return hitPoints <= 0;
    }
}
