package io.dukecvar.monpoke;

import io.dukecvar.monpoke.exceptions.MonpokeException;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MonpokeTest {
    @Test
    void pojoTest() {
        Monpoke mp = new Monpoke("Meekachu", 3, 1);

        Assertions.assertAll(
                () -> assertEquals("Meekachu", mp.getId()),
                () -> assertEquals(3, mp.getHitPoints()),
                () -> assertEquals(1, mp.getAttackPower())
        );
    }

    @Test
    void createAndHitAndTestForIsDefeated() {
        Monpoke mp = new Monpoke("Meekachu", 3, 1);
        Assertions.assertFalse(mp.isDefeated());
        mp.hit(3);
        Assertions.assertTrue(mp.isDefeated());

        mp = new Monpoke("Meekachu", 3, 1);
        Assertions.assertFalse(mp.isDefeated());
        mp.hit(2);
        Assertions.assertFalse(mp.isDefeated());
        mp.hit(2);
        Assertions.assertTrue(mp.isDefeated());
    }

    @Test
    void constructorExceptionTestForInsufficientHitPoints() {
        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> {
                    new Monpoke("blah", 0, 1);
                }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("insufficient hit points")
        );
    }

    @Test
    void constructorExceptionTestForInsufficientAttackPower() {
        Exception exception = Assertions.assertThrows(
                MonpokeException.class,
                () -> { new Monpoke("blah", 1, 0); }
        );
        Assertions.assertTrue(
                exception.getMessage().contains("insufficient attack power")
        );

    }

}
