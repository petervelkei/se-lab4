package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GT4500Test {

    private GT4500 ship;

    @BeforeEach
    public void init() {
        this.ship = new GT4500();
    }

    @Test
    void fireTorpedo_Single_Success() {
        // Arrange

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_All_Success() {
        // Arrange

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_Single_PrimaryFires() {
        GT4500 ship = new GT4500(5, 0.0, 5, 0.0); // Mindkét tár 5 torpedóval
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);
        assertTrue(result, "Az elsődleges tárnak sikeresen kellene tüzelnie.");
    }

    @Test
    void fireTorpedo_Single_AlternatesBetweenPrimaryAndSecondary() {
        GT4500 ship = new GT4500(5, 0.0, 5, 0.0);
        
        // Első tüzelés az elsődleges tárból
        assertTrue(ship.fireTorpedo(FiringMode.SINGLE), "Az első tüzelésnek az elsődleges tárból kell történnie.");
        
        // Második tüzelés a másodlagos tárból
        assertTrue(ship.fireTorpedo(FiringMode.SINGLE), "A második tüzelésnek a másodlagos tárból kell történnie.");
        
        // Harmadik tüzelés az elsődleges tárból
        assertTrue(ship.fireTorpedo(FiringMode.SINGLE), "A harmadik tüzelésnek ismét az elsődleges tárból kell történnie.");
    }


    @Test
    void fireTorpedo_Single_PrimaryEmpty_UsesSecondary() {
        GT4500 ship = new GT4500(0, 0.0, 5, 0.0); // Elsődleges tár üres, másodlagosban 5 torpedó
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);
        assertTrue(result, "Ha az elsődleges tár üres, a másodlagosból kell tüzelnie.");
    }


    @Test
    void fireTorpedo_All_FiresBothStores() {
        GT4500 ship = new GT4500(3, 0.0, 3, 0.0);
        boolean result = ship.fireTorpedo(FiringMode.ALL);
        assertTrue(result, "Az ALL módban mindkét tárból kell tüzelnie, ha van torpedó.");
    }


    @Test
    void fireTorpedo_All_BothEmpty_Failure() {
        GT4500 ship = new GT4500(0, 0.0, 0, 0.0);
        boolean result = ship.fireTorpedo(FiringMode.ALL);
        assertFalse(result, "Ha mindkét tár üres, az ALL módban a tüzelésnek sikertelennek kell lennie.");
    }


    @Test
    void fireTorpedo_Single_PrimaryFails_NoRetryOnSecondary() {
        GT4500 ship = new GT4500(5, 1.0, 5, 0.0); // Elsődleges tár hibás, másodlagos hibamentes
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);
        assertFalse(result, "Ha az elsődleges tár hibás, nem szabad tüzelnie a másodlagosból.");
    }


}
