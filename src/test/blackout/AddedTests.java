package blackout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import unsw.blackout.BlackoutController;
import unsw.response.models.EntityInfoResponse;
import unsw.response.models.FileInfoResponse;
import unsw.utils.Angle;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static blackout.TestHelpers.assertListAreEqualIgnoringOrder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static unsw.utils.MathsHelper.RADIUS_OF_JUPITER;

@TestInstance(value = Lifecycle.PER_CLASS)
public class AddedTests {
    @Test
    public void testMultipleRelayRecursive() {
        // Task 2 - part of testing communicableEntitiesInRange(String id);
        BlackoutController controller = new BlackoutController();

        // Creates 4 satellites, 1 device
        // 3 satellites are relays, 1 is Standard
        // Standard is really far away and must use 2 relays to connect to device
        // 1 relay tests the boundary direction
        controller.createSatellite("Satellite1", "RelaySatellite", 100 + RADIUS_OF_JUPITER, Angle.fromDegrees(345));
        controller.createSatellite("Satellite2", "RelaySatellite", 10000 + RADIUS_OF_JUPITER, Angle.fromDegrees(140));
        controller.createSatellite("Satellite3", "RelaySatellite", 200000 + RADIUS_OF_JUPITER, Angle.fromDegrees(160));
        controller.createSatellite("Satellite4", "StandardSatellite", 200000  + RADIUS_OF_JUPITER, Angle.fromDegrees(160.1));
        controller.createSatellite("Satellite5", "StandardSatellite", 100 + RADIUS_OF_JUPITER, Angle.fromDegrees(345));


        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(160));
        String msg = "Hi";
        controller.addFileToDevice("DeviceA", "FileAlpha", msg);
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "DeviceA", "Satellite4"));

        
        controller.simulate();
        assertEquals(new FileInfoResponse("FileAlpha", "H", 2, false), controller.getInfo("Satellite4").getFiles().get("FileAlpha"));
        controller.simulate();
        assertEquals(new FileInfoResponse("FileAlpha", "Hi", 2, true), controller.getInfo("Satellite4").getFiles().get("FileAlpha"));
        
    }

    @Test
    public void testDeviceRemovingUnfinished() {
        // Task 2 - part of testing simulation file removal for device
        BlackoutController controller = new BlackoutController();

        // Creates 1 Satellite, 1 Device
        // Satellite is Standard
        // Satellite is Laptop
        controller.createSatellite("Satellite1", "StandardSatellite", 10 + RADIUS_OF_JUPITER, Angle.fromDegrees(160));

        controller.createDevice("DeviceA", "LaptopDevice", Angle.fromDegrees(160));
        String msg = "Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooongmsg";
        controller.getSatellites().get("Satellite1").addFile("FileAlpha", msg, msg.length(), true, "Original");
        assertDoesNotThrow(() -> controller.sendFile("FileAlpha", "Satellite1", "DeviceA"));

        
        controller.simulate(81);
        assert(controller.getInfo("DeviceA").getFiles().containsKey("FileAlpha") == false);
        
    }
}