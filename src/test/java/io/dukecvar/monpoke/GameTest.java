package io.dukecvar.monpoke;

import io.dukecvar.monpoke.exceptions.MonpokeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void executeCommandTest() {
        Game game = new Game();

        // test CREATE command
        // error on invalid command
        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.executeCommand("CREATE Rocket Meekachu 3 1");
                    game.executeCommand("CREATE Green Smorelax 5 6");
                    game.executeCommand("INVALIDCMD");
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Invalid command: INVALIDCMD")
        );

        // test ICHOOSEYOU command
        // error on invalid command
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.executeCommand("ICHOOSEYOU Meekachu");
                    game.executeCommand("ICHOOSEYOU Smorelax");
                    game.executeCommand("INVALIDCMD");
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Invalid command: INVALIDCMD")
        );

        // test ATTACK command
        // error on invalid command
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.executeCommand("ATTACK");
                    game.executeCommand("INVALIDCMD");
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Invalid command: INVALIDCMD")
        );

    }

    @Test
    void cmdCreateTestBattery() {
        Game game = new Game();
        game.cmdCreate("CREATE Rocket Meekachu 3 1".split(" "));
        Assertions.assertTrue(game.teams[0] != null && game.teams[1] == null);

        // we can't add a Monpoke with same Id to the same team
        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdCreate("CREATE Rocket Meekachu 3 1".split(" "));
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("already a part of the team")
        );

        game.cmdCreate("CREATE Green Meekachu 3 1".split(" "));
        // both teams are defined now
        Assertions.assertTrue(game.teams[0] != null && game.teams[1] != null);

        // we can't create a 3rd team
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdCreate("CREATE Biff Bobachu 3 1".split(" "));
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Too many teams")
        );

        //assume cmdIchooseYou works and use it to change inBattleStage to true for next test
        // then we should error when we create a new Monpoke
        game.cmdIchooseYou("ICHOOSEYOU Meekachu".split(" "));
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdCreate("CREATE Rocket Moopachu 3 1".split(" "));
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("In battle stage")
        );

    }

    @Test
    void getCreateTeamTest() {
        Game game = new Game();

        Assertions.assertAll(
                () -> Assertions.assertEquals("Rocket", game.getCreateTeam("Rocket").getId()),
                () -> Assertions.assertEquals("Green", game.getCreateTeam("Green").getId()),
                () -> Assertions.assertThrows(MonpokeException.class,
                        () -> {
                            // we can't create a third team
                            game.getCreateTeam("Kablooee");
                        }),
                () -> Assertions.assertEquals("Rocket", game.getCreateTeam("Rocket").getId())
        );
    }

    @Test
    void cmdIchooseYouTestBattery() {
        String[] args = "ICHOOSEYOU Meekachu".split(" ");

        Game game = new Game();

        // both teams are null; throw exception
        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdIchooseYou(args);
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Game setup not complete")
        );

        Team rocket = game.getCreateTeam("Rocket");
        Team green = game.getCreateTeam("Green");

        // Teams are defined but no Monpoke have been add to the team
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdIchooseYou(args);
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("needs at least one Monpoke")
        );

        rocket.addMonpoke( new Monpoke("Meekachu", 3, 1));
        rocket.addMonpoke( new Monpoke("Rastly", 5, 6));
        green.addMonpoke(( new Monpoke("Smorelax", 2, 1)));

        // teams are created and Monpoke have been added to each but getting the chosen one is still invalid
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    rocket.getChosenMonpoke();
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Monpoke has not been chosen")
        );

        // we should be good by now
        game.cmdIchooseYou(args);
        Assertions.assertEquals("Meekachu", rocket.getChosenMonpoke().getId());
    }

    @Test
    void cmdAttackTestBattery() {
        String[] args = "ATTACK".split(" ");

        Game game = new Game();

        // both teams are null; throw exception
        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdAttack(args);
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Game setup not complete")
        );

        Team rocket = game.getCreateTeam("Rocket");
        Team green = game.getCreateTeam("Green");

        // Teams are defined but no Monpoke have been add to the team
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdAttack(args);
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Monpoke has not been chosen")
        );

        rocket.addMonpoke( new Monpoke("Meekachu", 3, 1));
        rocket.addMonpoke( new Monpoke("Rastly", 5, 6));
        green.addMonpoke(( new Monpoke("Smorelax", 2, 1)));

        // teams are created and Monpoke have been added to each but getting the chosen one is still invalid
        exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    game.cmdAttack(args);
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("Monpoke has not been chosen")
        );

        rocket.chooseMonpoke("Meekachu");
        green.chooseMonpoke("Smorelax");

        // Rocket:Meekachu attacks Green:Smorelax
        game.cmdAttack(args);

    }
}
