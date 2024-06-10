package repositories;

import models.Vehicle;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class VehicleRepository {
    private Map<Long, Vehicle> vehicles = new TreeMap<>();
    private int previousId = 0;

    public Optional<Vehicle> getVehicleByNumber(String number) {
        if (vehicles.containsKey(number)){
            return Optional.of(vehicles.get(number));
        }
        return Optional.empty();
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        previousId+=1;
        vehicle.setId((long) previousId);
        vehicles.put((long) previousId, vehicle);

        return vehicle;
    }
}
