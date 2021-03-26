package io.dukecvar.monpoke;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GameCommandsTest {

    private final Team team1 = new Team("Rocket");
    private final Team team2 = new Team("Green");
    private final Monpoke monpoke1 = new Monpoke("Rastly", 5, 6);
    private final Monpoke monpoke2 = new Monpoke("Smorelax", 2, 1);

    @Test
    void createOutput() {
        Assertions.assertEquals(
                "Rastly has been assigned to team Rocket!",
                GameCommands.createOutput(team1, monpoke1)
        );
    }

    @Test
    void attackOutput() {
        Assertions.assertEquals(
                "Rastly attacked Smorelax for 6 damage!",
                GameCommands.attackOutput(monpoke1, monpoke2)
        );
    }

    @Test
    void ichooseyouOutput() {
        Assertions.assertEquals(
                "Rastly has entered the battle!",
                GameCommands.ichooseyouOutput(monpoke1)
        );
    }

    @Test
    void defeatedOutput() {
        Assertions.assertEquals(
                "Rastly has been defeated!",
                GameCommands.defeatedOutput(monpoke1)
        );
    }

    @Test
    void winnerOutput() {
        Assertions.assertEquals(
                "Rocket is the winner!",
                GameCommands.winnerOutput(team1)
        );
    }

    @Test
    void healOutput() {
        Monpoke monpoke = new Monpoke("Rastly", 5, 6);
        Assertions.assertEquals(
                "Rastly healed 10 to 5",
                GameCommands.healOutput(monpoke, 10)
        );
    }
}