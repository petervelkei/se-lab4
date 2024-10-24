package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GT4500Test {

    private GT4500 ship;
    private TorpedoStore primaryMock;
    private TorpedoStore secondaryMock;

    @BeforeEach
    public void init() {
        // Mockoljuk a torpedo store-okat
        primaryMock = Mockito.mock(TorpedoStore.class);
        secondaryMock = Mockito.mock(TorpedoStore.class);
        // Használjuk a mockokat a GT4500 példány létrehozásához
        this.ship = new GT4500(primaryMock, secondaryMock);
    }

    @Test
    void fireTorpedo_Single_Success() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_All_Success() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);
        Mockito.when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_Single_PrimaryEmpty_ShouldFireSecondary() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(true);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
        Mockito.verify(secondaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_Single_SecondaryEmpty_ShouldFirePrimary() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(true);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
        Mockito.verify(primaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_Single_BothEmpty_ShouldFail() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(true);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(false, result);
    }

    @Test
    void fireTorpedo_All_PrimaryEmpty_SecondaryFires() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(true);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
        Mockito.verify(secondaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_All_SecondaryEmpty_PrimaryFires() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(true);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
        Mockito.verify(primaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_All_BothFail_ShouldFail() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(false);
        Mockito.when(secondaryMock.fire(1)).thenReturn(false);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(false, result);
    }
    
    @Test
    void fireTorpedo_All_BothSuccess_ShouldPass() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);
        Mockito.when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
        Mockito.verify(primaryMock, Mockito.times(1)).fire(1);
        Mockito.verify(secondaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_Single_PrimaryFiresTwice_ShouldAlternate() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);
        Mockito.when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean firstFire = ship.fireTorpedo(FiringMode.SINGLE); // Primary should fire
        boolean secondFire = ship.fireTorpedo(FiringMode.SINGLE); // Secondary should fire

        // Assert
        assertEquals(true, firstFire);
        assertEquals(true, secondFire);
        Mockito.verify(primaryMock, Mockito.times(1)).fire(1);
        Mockito.verify(secondaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_Single_PrimaryFails_ShouldNotAttemptSecondary() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(false);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(false, result);
        Mockito.verify(primaryMock, Mockito.times(1)).fire(1);
        Mockito.verify(secondaryMock, Mockito.times(0)).fire(Mockito.anyInt());
    }

    @Test
    void fireTorpedo_Single_SecondaryFails_ShouldNotAttemptPrimary() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(true);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.fire(1)).thenReturn(false);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(false, result);
        Mockito.verify(primaryMock, Mockito.times(0)).fire(Mockito.anyInt());
        Mockito.verify(secondaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_All_OnlyPrimaryFiresSuccessfully() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);
        Mockito.when(secondaryMock.fire(1)).thenReturn(false);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
        Mockito.verify(primaryMock, Mockito.times(1)).fire(1);
        Mockito.verify(secondaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_All_OnlySecondaryFiresSuccessfully() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(false);
        Mockito.when(primaryMock.fire(1)).thenReturn(false);
        Mockito.when(secondaryMock.fire(1)).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
        Mockito.verify(primaryMock, Mockito.times(1)).fire(1);
        Mockito.verify(secondaryMock, Mockito.times(1)).fire(1);
    }

    @Test
    void fireTorpedo_Single_AlternateWithSecondaryEmpty() {
        // Arrange
        Mockito.when(primaryMock.isEmpty()).thenReturn(false);
        Mockito.when(secondaryMock.isEmpty()).thenReturn(true);
        Mockito.when(primaryMock.fire(1)).thenReturn(true);

        // Act
        boolean firstFire = ship.fireTorpedo(FiringMode.SINGLE); // Primary fires
        boolean secondFire = ship.fireTorpedo(FiringMode.SINGLE); // Should attempt to alternate but revert to primary

        // Assert
        assertEquals(true, firstFire);
        assertEquals(true, secondFire);
        Mockito.verify(primaryMock, Mockito.times(2)).fire(1);
        Mockito.verify(secondaryMock, Mockito.times(0)).fire(Mockito.anyInt());
    }

}
