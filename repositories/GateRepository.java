package repositories;

import models.Gate;
import models.ParkingLot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class GateRepository {
    private Map<Long, Gate> gates = new TreeMap<>();

    public Optional<Gate> findGateById(Long id){
        if (gates.containsKey(id)){
            return Optional.of(gates.get(id));
        }

        return Optional.empty();
    }
}
//Optional is like a wrapper over an object