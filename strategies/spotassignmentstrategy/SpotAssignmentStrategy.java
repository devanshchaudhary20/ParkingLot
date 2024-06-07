package strategies.spotassignmentstrategy;

import models.Gate;
import models.ParkingSpot;
import models.VehicleType;

public interface SpotAssignmentStrategy {

    ParkingSpot getSpot(Gate gate, VehicleType vehicleType);
}
