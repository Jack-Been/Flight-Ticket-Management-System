package com.company.model;
import java.util.Comparator;
import java.util.LinkedList;

public class FlightTypeList {
    private LinkedList<FlightType> flightTypes;

    public FlightTypeList() {
        flightTypes = new LinkedList<>();
        buildTestData();
        printList();
    }

    private void buildTestData() {
        addItem(new FlightType("Economy"));
        addItem(new FlightType("Business"));
        addItem(new FlightType("First Class"));
        addItem(new FlightType("Premium Economy"));
    }

    public void addItem(FlightType newFlightType) {
        flightTypes.add(newFlightType);
        flightTypes.sort(Comparator.comparing(FlightType::getType));
    }

    public void removeItem(FlightType flightTypeToRemove) {
        flightTypes.remove(flightTypeToRemove);
    }

    public FlightType getItem(String searchTerm) {
        for (FlightType flightType : flightTypes) {
            if (flightType.getType().equalsIgnoreCase(searchTerm)) {
                return flightType;
            }
        }
        return null;
    }

    public void printList() {
        for (FlightType flightType : flightTypes) {
            System.out.println(flightType);
        }
    }

    public LinkedList<FlightType> getFlightTypes() {
        return flightTypes;
    }
}
