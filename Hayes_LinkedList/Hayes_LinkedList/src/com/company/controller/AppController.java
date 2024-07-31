package com.company.controller;

import com.company.model.*;
import com.company.view.FlightPanel;
import com.company.view.TicketPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppController extends JFrame implements ActionListener, ListSelectionListener {
    private final FlightPanel flightPanel;
    private final TicketPanel ticketPanel;
    JTabbedPane tabbedPane;
    private List<Flight> flights;
    private List<Ticket> tickets;

    public AppController(FlightTypeList flightTypeList) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"));
            flights = (ArrayList<Flight>) ois.readObject();
            tickets = (ArrayList<Ticket>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            flights = new ArrayList<>();
            tickets = new ArrayList<>();

            flights.add(new Flight("AA100", "American Airlines", "New York", "Los Angeles", new DateTime("2024-01-01T10:00"), new DateTime("2024-01-01T13:00"), "Boeing 737", new FlightType("Economy"), 300.00));
            flights.add(new Flight("DL200", "Delta Airlines", "Atlanta", "Chicago", new DateTime("2024-01-02T15:00"), new DateTime("2024-01-02T17:00"), "Airbus A320", new FlightType("Economy"),200.00));
            flights.add(new Flight("UA300", "United Airlines", "San Francisco", "Seattle", new DateTime("2024-01-03T20:00"), new DateTime("2024-01-03T22:00"), "Boeing 757", new FlightType("Economy"),150.00));
            flights.add(new Flight("SW400", "Southwest Airlines", "Dallas", "Houston", new DateTime("2024-01-04T08:00"), new DateTime("2024-01-04T09:00"), "Boeing 737", new FlightType("Economy"),100.00));
            flights.add(new Flight("AS500", "Alaska Airlines", "Anchorage", "Seattle", new DateTime("2024-01-05T12:00"), new DateTime("2024-01-05T16:00"), "Boeing 737", new FlightType("Economy"),250.00));

            tickets.add(new Ticket("1", "AA100", "John Smith", "America", "+123423423423"));
            tickets.add(new Ticket("3", "UA300", "Akash John", "United Kingdom", "+422312312"));
            tickets.add(new Ticket("15", "AA100", "Harry Denis", "America", "+2322342342"));
            tickets.add(new Ticket("10", "AA100", "Kale Wale", "France", "+23234234234"));
            tickets.add(new Ticket("1", "UA300", "Mikylah Furra", "Egypt", "+534332323"));

            saveData();
        }

        flightPanel = new FlightPanel(this, flights, flightTypeList);
        ticketPanel = new TicketPanel(this, tickets);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Flight", flightPanel);
        tabbedPane.addTab("Ticket", ticketPanel);
        setContentPane(tabbedPane);

        setPreferredSize(new Dimension(1200, 600));

        pack();

        setTitle("Airline Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"));
            oos.writeObject(flights);
            oos.writeObject(tickets);
            oos.close();
        } catch (IOException ex) {
            System.out.println("'data.ser' file cannot be created!\nSystem exiting...");
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Next": {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0 -> {
                        int selectedRow = flightPanel.getTable().getSelectedRow();
                        if (selectedRow == -1)
                            flightPanel.getTable().getSelectionModel().setSelectionInterval(0, 0);
                        else if (selectedRow < flightPanel.getTable().getRowCount() - 1)
                            flightPanel.getTable().getSelectionModel().setSelectionInterval(selectedRow + 1, selectedRow + 1);
                    }
                    case 1 -> {
                        int selectedRow = ticketPanel.getTable().getSelectedRow();
                        if (selectedRow == -1)
                            ticketPanel.getTable().getSelectionModel().setSelectionInterval(0, 0);
                        else if (selectedRow < ticketPanel.getTable().getRowCount() - 1)
                            ticketPanel.getTable().getSelectionModel().setSelectionInterval(selectedRow + 1, selectedRow + 1);
                    }
                }
                break;
            }
            case "Previous": {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0 -> {
                        int selectedRow2 = flightPanel.getTable().getSelectedRow();
                        if (selectedRow2 == -1)
                            flightPanel.getTable().getSelectionModel().setSelectionInterval(0, 0);
                        else if (selectedRow2 > 0)
                            flightPanel.getTable().getSelectionModel().setSelectionInterval(selectedRow2 - 1, selectedRow2 - 1);
                    }
                    case 1 -> {
                        int selectedRow2 = ticketPanel.getTable().getSelectedRow();
                        if (selectedRow2 == -1)
                            ticketPanel.getTable().getSelectionModel().setSelectionInterval(0, 0);
                        else if (selectedRow2 > 0)
                            ticketPanel.getTable().getSelectionModel().setSelectionInterval(selectedRow2 - 1, selectedRow2 - 1);
                    }
                }
                break;
            }
            case "Add": {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0 -> flightPanel.getDetailPanel().refreshAdd();
                    case 1 -> ticketPanel.getDetailPanel().refreshAdd(flights, tickets);
                }
                break;
            }
            case "Update": {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0 -> {
                        int selectedRow = flightPanel.getTable().getSelectedRow();
                        Flight flight = getFlightFromTable(selectedRow);
                        if (selectedRow != -1 && flight != null)
                            flightPanel.getDetailPanel().refreshUpdate(flight);
                    }
                    case 1 -> {
                        int selectedRow = ticketPanel.getTable().getSelectedRow();
                        Ticket ticket = getTicketFromTable(selectedRow);
                        if (selectedRow != -1 && ticket != null)
                            ticketPanel.getDetailPanel().refreshUpdate(ticket, flights, tickets);
                    }
                }
                break;
            }
            case "Delete": {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0 -> {
                        Flight selectedFlight = flightPanel.getDetailPanel().getSelectedFlight();
                        if (selectedFlight != null) {
                            flights.remove(selectedFlight);
                            saveData();
                            flightPanel.getDetailPanel().hideAll();
                            flightPanel.getTable().getSelectionModel().clearSelection();
                            flightPanel.getTable().getRowSorter().modelStructureChanged();
                            flightPanel.updateUI();
                        }
                    }
                    case 1 -> {
                        Ticket selectedTicket = ticketPanel.getDetailPanel().getSelectedTicket();
                        if (selectedTicket != null) {
                            tickets.remove(selectedTicket);
                            saveData();
                            ticketPanel.getDetailPanel().hideAll();
                            ticketPanel.getTable().getSelectionModel().clearSelection();
                            ticketPanel.getTable().getRowSorter().modelStructureChanged();
                            ticketPanel.updateUI();
                        }
                    }
                }
                break;
            }
            case "Confirm": {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0 -> {
                        Flight selectedFlight = flightPanel.getDetailPanel().getSelectedFlight();
                        Flight flight = flightPanel.getDetailPanel().getFlight();

                        if (flight == null)
                            return;

                        if (selectedFlight != null) {
                            selectedFlight.setFlightType(flight.getFlightType());
                            selectedFlight.setAircraftType(flight.getAircraftType());
                            selectedFlight.setAirline(flight.getAirline());
                            selectedFlight.setArrivalTime(flight.getArrivalTime());
                            selectedFlight.setDepartureTime(flight.getDepartureTime());
                            selectedFlight.setDestination(flight.getDestination());
                            selectedFlight.setFlightNumber(flight.getFlightNumber());
                            selectedFlight.setOrigin(flight.getOrigin());
                            selectedFlight.setPrice(flight.getPrice());
                            saveData();

                            flightPanel.getDetailPanel().refresh(selectedFlight);
                        } else {
                            int selectedRow = flightPanel.getTable().getSelectedRow();
                            if (selectedRow == -1) {
                                flights.add(flight);
                                saveData();
                                flightPanel.getTable().getRowSorter().modelStructureChanged();
                                flightPanel.getTable().getSelectionModel().setSelectionInterval(flights.size() - 1, flights.size() - 1);
                            } else {
                                flights.add(selectedRow, flight);
                                saveData();
                                flightPanel.getTable().getRowSorter().modelStructureChanged();
                                flightPanel.getTable().getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
                            }
                            flightPanel.getDetailPanel().refresh(flight);
                        }

                        flightPanel.updateUI();
                    }
                    case 1 -> {
                        Ticket selectedTicket = ticketPanel.getDetailPanel().getSelectedTicket();
                        Ticket ticket = ticketPanel.getDetailPanel().getTicket();

                        if (ticket == null)
                            return;

                        if (selectedTicket != null) {
                            selectedTicket.setFlightNumber(ticket.getFlightNumber());
                            selectedTicket.setPassengerName(ticket.getPassengerName());
                            selectedTicket.setPassengerNationality(ticket.getPassengerNationality());
                            selectedTicket.setPassengerPhone(ticket.getPassengerPhone());
                            selectedTicket.setSeatNumber(ticket.getSeatNumber());
                            saveData();

                            ticketPanel.getDetailPanel().refresh(selectedTicket);
                        } else {
                            int selectedRow = ticketPanel.getTable().getSelectedRow();
                            if (selectedRow == -1) {
                                tickets.add(ticket);
                                saveData();
                                ticketPanel.getTable().getRowSorter().modelStructureChanged();
                                ticketPanel.getTable().getSelectionModel().setSelectionInterval(tickets.size() - 1, tickets.size() - 1);
                            } else {
                                tickets.add(selectedRow, ticket);
                                saveData();
                                ticketPanel.getTable().getRowSorter().modelStructureChanged();
                                ticketPanel.getTable().getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
                            }
                            ticketPanel.getDetailPanel().refresh(ticket);
                        }

                        ticketPanel.updateUI();
                    }
                }
                break;
            }
            case "Cancel": {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0 -> {
                        int selectedRow = flightPanel.getTable().getSelectedRow();
                        Flight flight = getFlightFromTable(selectedRow);
                        if (selectedRow != -1 && flight != null)
                            flightPanel.getDetailPanel().refresh(flight);
                        else
                            flightPanel.getDetailPanel().hideAll();
                    }
                    case 1 -> {
                        int selectedRow = ticketPanel.getTable().getSelectedRow();
                        Ticket ticket = getTicketFromTable(selectedRow);
                        if (selectedRow != -1 && ticket != null)
                            ticketPanel.getDetailPanel().refresh(ticket);
                        else
                            ticketPanel.getDetailPanel().hideAll();
                    }
                }
                break;
            }
            case "Quit": {
                System.exit(0);
                break;
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        switch (tabbedPane.getSelectedIndex()) {
            case 0 -> {
                int selectedRow = flightPanel.getTable().getSelectedRow();
                Flight flight = getFlightFromTable(selectedRow);
                if (selectedRow != -1 && flight != null) flightPanel.getDetailPanel().refresh(flight);
                flightPanel.updateUI();
            }
            case 1 -> {
                int selectedRow = ticketPanel.getTable().getSelectedRow();
                Ticket ticket = getTicketFromTable(selectedRow);
                if (selectedRow != -1 && ticket != null) ticketPanel.getDetailPanel().refresh(ticket);
                ticketPanel.updateUI();
            }
        }
    }

    private Flight getFlightFromTable(int selectedRow) {
        if (selectedRow == -1) return null;
        String flightNumber = flightPanel.getTable().getValueAt(selectedRow, 0).toString();
        return flights.stream().filter(f -> f.getFlightNumber().equals(flightNumber)).findFirst().orElse(null);
    }

    private Ticket getTicketFromTable(int selectedRow) {
        if (selectedRow == -1) return null;
        String seatNumber = ticketPanel.getTable().getValueAt(selectedRow, 4).toString();
        String flightNumber = ticketPanel.getTable().getValueAt(selectedRow, 3).toString();
        return tickets.stream().filter(t -> t.getSeatNumber().equals(seatNumber) && t.getFlightNumber().equals(flightNumber)).findFirst().orElse(null);
    }
}

