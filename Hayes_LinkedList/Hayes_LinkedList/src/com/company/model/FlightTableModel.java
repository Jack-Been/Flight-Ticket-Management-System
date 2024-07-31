package com.company.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FlightTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "Flight Number",
            "Airline",
            "Origin",
            "Destination",
            "Departure Time",
            "Arrival Time",
            "Aircraft Type",
            "Flight Type",
            "Price"
    };
    private final List<Flight> flights;

    public FlightTableModel(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public int getRowCount() {
        return flights.size();
    }

    @Override
    public int getColumnCount() {
        return Flight.ATTRIBUTES_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> flights.get(rowIndex).getFlightNumber();
            case 1 -> flights.get(rowIndex).getAirline();
            case 2 -> flights.get(rowIndex).getOrigin();
            case 3 -> flights.get(rowIndex).getDestination();
            case 4 -> flights.get(rowIndex).getDepartureTime();
            case 5 -> flights.get(rowIndex).getArrivalTime();
            case 6 -> flights.get(rowIndex).getAircraftType();
            case 7 -> flights.get(rowIndex).getFlightType();
            case 8 -> String.format("$%.2f", flights.get(rowIndex).getPrice());

            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
