package com.company.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TicketTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "Passenger Name",
            "Nationality",
            "Phone Number",
            "Flight Number",
            "Seat Number"
    };

    private final List<Ticket> tickets;

    public TicketTableModel(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public int getColumnCount() {
        return Ticket.ATTRIBUTES_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> tickets.get(rowIndex).getPassengerName();
            case 1 -> tickets.get(rowIndex).getPassengerNationality();
            case 2 -> tickets.get(rowIndex).getPassengerPhone();
            case 3 -> tickets.get(rowIndex).getFlightNumber();
            case 4 -> tickets.get(rowIndex).getSeatNumber();

            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
