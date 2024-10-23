package hu.bme.mit.spaceship;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GT4500Test {

    private GT4500 ship;

    @BeforeEach
    public void init() {
        this.ship = new GT4500(5, 0.2, 5, 0.1); // Mindkét tár 5 torpedóval, különböző hibaráta
    }

    @Test
    void fireTorpedo_Single_PrimaryFails_DoesNotRetry() {
        // Primary meghibásodik, Secondary nem
        boolean firstAttempt = ship.fireTorpedo(FiringMode.SINGLE); // Elsődleges
        boolean secondAttempt = ship.fireTorpedo(FiringMode.SINGLE); // Másodlagos

        assertTrue(firstAttempt || secondAttempt, "A hajónak egyszer tüzelnie kell, ha egyik tár hibátlan.");
    }

    @Test
    void fireTorpedo_All_BothFail_ShouldReturnFalse() {
        // Szimuláljuk, hogy mindkét tár hibás
        ship = new GT4500(2, 1.0, 2, 1.0); // 100% meghibásodási ráta mindkét tárban

        boolean result = ship.fireTorpedo(FiringMode.ALL);

        assertFalse(result, "Ha mindkét tár meghibásodik, a tüzelésnek sikertelennek kell lennie.");
    }

    @Test
    void fireTorpedo_Single_AlternatingMultipleTimes() {
        boolean shot1 = ship.fireTorpedo(FiringMode.SINGLE); // Primary
        boolean shot2 = ship.fireTorpedo(FiringMode.SINGLE); // Secondary
        boolean shot3 = ship.fireTorpedo(FiringMode.SINGLE); // Primary újra
        boolean shot4 = ship.fireTorpedo(FiringMode.SINGLE); // Secondary újra

        assertTrue(shot1 && shot2 && shot3 && shot4, "A tüzeléseknek váltakozva kell működniük.");
    }
}
