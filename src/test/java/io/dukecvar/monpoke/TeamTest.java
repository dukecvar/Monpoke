package io.dukecvar.monpoke;

import io.dukecvar.monpoke.exceptions.MonpokeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeamTest {

    @Test
    void addMonPokeTest () {
        Team teamRocket = new Team("Rocket");
        teamRocket.addMonpoke(new Monpoke("Meekachu", 3, 1));

        Assertions.assertFalse(teamRocket.isDefeated());

        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    teamRocket.addMonpoke(new Monpoke("Meekachu", 3, 1));
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("already a part of the team")
        );
    }

    @Test
    void chooseMonpokeExceptionAtleastOneMonpokeRequirement() {
        Team teamRocket = new Team("Rocket");
        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    teamRocket.chooseMonpoke("blah");
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("needs at least one Monpoke")
        );

    }

    @Test
    void chooseMonpokeExceptionNotAvailableOnTeam() {
        Team teamRocket = new Team("Rocket");
        Monpoke mp = new Monpoke("stuff", 1, 2);
        teamRocket.addMonpoke(mp);

        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    teamRocket.chooseMonpoke("blah");
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("is not available on team")
        );
    }

    @Test
    void chooseMonpokeExceptionHasBeenDefeated() {
        Team teamRocket = new Team("Rocket");
        Monpoke mp = new Monpoke("stuff", 1, 2);
        teamRocket.addMonpoke(mp);
        mp.hit(1);

        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    teamRocket.chooseMonpoke("stuff");
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("has been defeated")
        );
    }

    @Test
    void getChosenMonpokeNormalOp() {
        Team teamRocket = new Team("Rocket");
        Monpoke mp = new Monpoke("stuff", 1, 2);
        teamRocket.addMonpoke(mp);
        teamRocket.chooseMonpoke("stuff");
        Assertions.assertEquals("stuff", teamRocket.getChosenMonpoke().getId());
    }

    @Test
    void getChosenMonpokeExceptionNoMonpokeChosen() {
        Team teamRocket = new Team("Rocket");
        Monpoke mp = new Monpoke("stuff", 1, 2);
        teamRocket.addMonpoke(mp);

        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    teamRocket.getChosenMonpoke();
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Monpoke has not been chosen")
        );
    }

    @Test
    void updateTeamShouldRemoveChosenWhenDefeated() {
        Team teamRocket = new Team("Rocket");
        Monpoke mp = new Monpoke("stuff", 1, 2);
        teamRocket.addMonpoke(mp);
        teamRocket.chooseMonpoke("stuff");
        mp.hit(1);
        teamRocket.updateTeam();

        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    teamRocket.chooseMonpoke("stuff");
                }
        );

        Assertions.assertTrue(
                exception.getMessage().contains("needs at least one Monpoke")
        );
    }

    @Test
    void updateTeamTest2() {
        Team teamRocket = new Team("Rocket");
        Monpoke mp = new Monpoke("stuff", 1, 2);
        teamRocket.addMonpoke(mp);
        teamRocket.addMonpoke(new Monpoke("blah", 1, 2));
        teamRocket.chooseMonpoke("stuff");
        mp.hit(1);
        teamRocket.updateTeam();

        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    teamRocket.chooseMonpoke("stuff");
                }
        );

        Assertions.assertTrue(
                exception.getMessage().contains("is not available on team")
        );
    }

    @Test
    void isDefeatedTest() {
        Team teamRocket = new Team("Rocket");
        teamRocket.addMonpoke(new Monpoke("blah", 1, 2));
        teamRocket.addMonpoke(new Monpoke("stuff", 1, 2));

        teamRocket.chooseMonpoke("blah");
        teamRocket.getChosenMonpoke().hit(1);
        Assertions.assertFalse(teamRocket.isDefeated());

        teamRocket.chooseMonpoke("stuff");
        teamRocket.getChosenMonpoke().hit(1);
        Assertions.assertTrue(teamRocket.isDefeated());

    }

}
