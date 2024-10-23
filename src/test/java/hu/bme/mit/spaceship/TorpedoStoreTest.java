package hu.bme.mit.spaceship;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    @Test
    void fire_SimulateFailure() {
        // Arrange: Store with 100% failure rate
        TorpedoStore store = new TorpedoStore(5, 1.0);

        // Act: Attempt to fire
        boolean result = store.fire(1);

        // Assert: Should fail because of failure rate
        assertFalse(result, "The fire should fail due to the simulated failure rate.");
    }

    @Test
    void fire_EmptyStore_ShouldThrowException() {
        // Arrange: Empty store
        TorpedoStore store = new TorpedoStore(0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(1), "Attempting to fire from an empty store should throw an exception.");
    }

    @Test
    void fire_InvalidNumberOfTorpedoes_ShouldThrowException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(-1), "Attempting to fire an invalid number of torpedoes should throw an exception.");
    }

    
}
