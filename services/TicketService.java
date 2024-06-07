package services;

import exceptions.GateNotFoundException;
import models.*;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.VehicleRepository;
import strategies.spotassignmentstrategy.SpotAssignmentStrategy;
import strategies.spotassignmentstrategy.SpotAssignmentStrategyFactory;

import java.sql.Date;
import java.util.Optional;

public class TicketService {
    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private ParkingLotRepository parkingLotRepository;

    public Ticket issueTicket(
            VehicleType vehicleType,
            String vehicleNumber,
            String vehicleOwnerName,
            Long gateId
    ) throws GateNotFoundException {
        // 1. create ticket object
        // 2. assign spot
        // 3. return
        Ticket ticket = new Ticket();
        ticket.setEntryTime(new Date(System.currentTimeMillis()));

        Optional<Gate> gateOptional = gateRepository.findGateById(gateId);

        if (gateOptional.isEmpty()) {
            throw new GateNotFoundException();
        }

        Gate gate = gateOptional.get();
        ticket.setGeneratedAt(gate);
        ticket.setGeneratedBy(gate.getCurrentOperator());
        Vehicle savedVehicle;

        Optional<Vehicle> vehicleOptional = vehicleRepository.getVehicleByNumber(vehicleNumber);

        if (vehicleOptional.isEmpty()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleType(vehicleType);
            vehicle.setNumber(vehicleNumber);
            vehicle.setVehicleOwnerName(vehicleOwnerName);

            savedVehicle = vehicleRepository.saveVehicle(vehicle);
        } else {
            savedVehicle = vehicleOptional.get();
        }

        ticket.setVehicle(savedVehicle);

        //check is vehicle in the database
        // 1. Yes
        //     -- Get vehicle from database and
        //     -- put in the ticket object
        // 2. No
        //     -- create new vehicle object
        //     -- save it to database
        //     -- put in the ticket object

        SpotAssignmentStrategyType spotAssignmentStrategyType = parkingLotRepository.getParkingLotForGate(gate).getSpotAssignmentStrategyType();
        SpotAssignmentStrategy spotAssignmentStrategy = SpotAssignmentStrategyFactory.getSpotForType(spotAssignmentStrategyType);
        ticket.setAssignedSpot(spotAssignmentStrategy.getSpot(
                gate, vehicleType
        ));

        return null;
    }
}
