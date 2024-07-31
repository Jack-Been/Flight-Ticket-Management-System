package com.company.view;

import com.company.controller.AppController;
import com.company.model.DateTime;
import com.company.model.Flight;
import com.company.model.FlightType;
import com.company.model.FlightTypeList;

import javax.swing.*;
import java.awt.*;

public class FlightDetailPanel extends JPanel {
    private final JLabel title;
    private final JPanel dataPanel;
    private final JPanel operationPanel;
    private final JPanel confirmPanel;

    private final JTextField flightNumberField;
    private final JTextField airlineField;
    private final JTextField originField;
    private final JTextField destinationField;
    private final JTextField departureTimeField;
    private final JTextField arrivalTimeField;
    private final JTextField aircraftTypeField;
    private final JComboBox<FlightType> flightTypeField;
    private final JTextField priceField;

    private final JLabel flightNumberLabel;
    private final JLabel airlineLabel;
    private final JLabel originLabel;
    private final JLabel destinationLabel;
    private final JLabel departureTimeLabel;
    private final JLabel arrivalTimeLabel;
    private final JLabel aircraftTypeLabel;
    private final JLabel flightTypeLabel;
    private final JLabel priceLabel;

    private Flight selectedFlight;

    public FlightDetailPanel(AppController listener, FlightTypeList flightTypeList) {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 800));

        title = new JLabel();
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);

        add(Box.createRigidArea(new Dimension(0, 10)));

        dataPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        flightNumberField = new JTextField();
        airlineField = new JTextField();
        originField = new JTextField();
        destinationField = new JTextField();
        departureTimeField = new JTextField();
        arrivalTimeField = new JTextField();
        aircraftTypeField = new JTextField();
        flightTypeField = new JComboBox<>(flightTypeList.getFlightTypes().toArray(new FlightType[0]));
        priceField = new JTextField();

        flightNumberLabel = new JLabel();
        airlineLabel = new JLabel();
        originLabel = new JLabel();
        destinationLabel = new JLabel();
        departureTimeLabel = new JLabel();
        arrivalTimeLabel = new JLabel();
        aircraftTypeLabel = new JLabel();
        flightTypeLabel = new JLabel();
        priceLabel = new JLabel();

        Font font = new Font("Calibri", Font.BOLD, 16);
        gbc.weightx = 0.2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dataPanel.add(new JLabel("Flight Number") {{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Airline"){{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Origin"){{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Destination"){{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Departure Time"){{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Arrival Time"){{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Aircraft Type"){{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Flight Type"){{setFont(font);}}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Price ($)"){{setFont(font);}}, gbc);

        gbc.weightx = 0.8;
        gbc.gridx = 1;
        gbc.ipadx = -50;
        gbc.gridy = 0;
        dataPanel.add(flightNumberLabel, gbc);
        dataPanel.add(flightNumberField, gbc);
        gbc.gridy++;
        dataPanel.add(airlineLabel, gbc);
        dataPanel.add(airlineField, gbc);
        gbc.gridy++;
        dataPanel.add(originLabel, gbc);
        dataPanel.add(originField, gbc);
        gbc.gridy++;
        dataPanel.add(destinationLabel, gbc);
        dataPanel.add(destinationField, gbc);
        gbc.gridy++;
        dataPanel.add(departureTimeLabel, gbc);
        dataPanel.add(departureTimeField, gbc);
        gbc.gridy++;
        dataPanel.add(arrivalTimeLabel, gbc);
        dataPanel.add(arrivalTimeField, gbc);
        gbc.gridy++;
        dataPanel.add(aircraftTypeLabel, gbc);
        dataPanel.add(aircraftTypeField, gbc);
        gbc.gridy++;
        dataPanel.add(flightTypeLabel, gbc);
        dataPanel.add(flightTypeField, gbc);
        gbc.gridy++;
        dataPanel.add(priceLabel, gbc);
        dataPanel.add(priceField, gbc);

        add(dataPanel);

        Dimension buttonSize = new Dimension(100, 30);
        operationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(listener);
        updateButton.setPreferredSize(buttonSize);
        operationPanel.add(updateButton);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(listener);
        deleteButton.setPreferredSize(buttonSize);
        operationPanel.add(deleteButton);
        add(operationPanel);

        confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(listener);
        confirmButton.setPreferredSize(buttonSize);
        confirmPanel.add(confirmButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(listener);
        cancelButton.setPreferredSize(buttonSize);
        confirmPanel.add(cancelButton);
        add(confirmPanel);

        hideAll();
    }

    public void refresh(Flight flight) {
        hideAll();

        selectedFlight = flight;

        title.setText("Flight Details");

        flightNumberLabel.setText(flight.getFlightNumber());
        airlineLabel.setText(flight.getAirline());
        originLabel.setText(flight.getOrigin());
        destinationLabel.setText(flight.getDestination());
        departureTimeLabel.setText(flight.getDepartureTime().toString());
        arrivalTimeLabel.setText(flight.getArrivalTime().toString());
        aircraftTypeLabel.setText(flight.getAircraftType());
        flightTypeLabel.setText(flight.getFlightType().getType());
        priceLabel.setText(String.format("%.2f", flight.getPrice()));


        flightNumberLabel.setVisible(true);
        airlineLabel.setVisible(true);
        originLabel.setVisible(true);
        destinationLabel.setVisible(true);
        departureTimeLabel.setVisible(true);
        arrivalTimeLabel.setVisible(true);
        aircraftTypeLabel.setVisible(true);
        flightTypeLabel.setVisible(true);
        priceLabel.setVisible(true);

        title.setVisible(true);
        dataPanel.setVisible(true);
        operationPanel.setVisible(true);
    }

    public void refreshUpdate(Flight flight) {
        hideAll();

        selectedFlight = flight;

        title.setText("Update Flight");

        flightNumberField.setText(flight.getFlightNumber());
        airlineField.setText(flight.getAirline());
        originField.setText(flight.getOrigin());
        destinationField.setText(flight.getDestination());
        departureTimeField.setText(flight.getDepartureTime().getDateTime().toString());
        arrivalTimeField.setText(flight.getArrivalTime().getDateTime().toString());
        aircraftTypeField.setText(flight.getAircraftType());
        flightTypeField.setSelectedItem(flight.getFlightType());
        priceField.setText(String.format("%.2f", flight.getPrice()));


        flightNumberField.setVisible(true);
        airlineField.setVisible(true);
        originField.setVisible(true);
        destinationField.setVisible(true);
        departureTimeField.setVisible(true);
        arrivalTimeField.setVisible(true);
        aircraftTypeField.setVisible(true);
        flightTypeField.setVisible(true);
        priceField.setVisible(true);

        title.setVisible(true);
        dataPanel.setVisible(true);
        confirmPanel.setVisible(true);
    }

    public void refreshAdd() {
        hideAll();

        title.setText("Add Flight");

        flightNumberField.setText("");
        airlineField.setText("");
        originField.setText("");
        destinationField.setText("");
        departureTimeField.setText("");
        arrivalTimeField.setText("");
        aircraftTypeField.setText("");
        flightTypeField.setSelectedIndex(0);
        priceField.setText("");

        flightNumberField.setVisible(true);
        airlineField.setVisible(true);
        originField.setVisible(true);
        destinationField.setVisible(true);
        departureTimeField.setVisible(true);
        arrivalTimeField.setVisible(true);
        aircraftTypeField.setVisible(true);
        flightTypeField.setVisible(true);
        priceField.setVisible(true);

        title.setVisible(true);
        dataPanel.setVisible(true);
        confirmPanel.setVisible(true);
    }

    public void hideAll() {
        selectedFlight = null;

        flightNumberLabel.setVisible(false);
        airlineLabel.setVisible(false);
        originLabel.setVisible(false);
        destinationLabel.setVisible(false);
        departureTimeLabel.setVisible(false);
        arrivalTimeLabel.setVisible(false);
        aircraftTypeLabel.setVisible(false);
        flightTypeLabel.setVisible(false);
        priceLabel.setVisible(false);
        flightNumberField.setVisible(false);
        airlineField.setVisible(false);
        originField.setVisible(false);
        destinationField.setVisible(false);
        departureTimeField.setVisible(false);
        arrivalTimeField.setVisible(false);
        aircraftTypeField.setVisible(false);
        flightTypeField.setVisible(false);
        priceField.setVisible(false);

        title.setVisible(false);
        dataPanel.setVisible(false);
        operationPanel.setVisible(false);
        confirmPanel.setVisible(false);
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public Flight getFlight() {
        if (
                flightNumberField.getText().isEmpty() ||
                airlineField.getText().isEmpty() ||
                originField.getText().isEmpty() ||
                destinationField.getText().isEmpty() ||
                departureTimeField.getText().isEmpty() ||
                arrivalTimeField.getText().isEmpty() ||
                aircraftTypeField.getText().isEmpty() ||
                priceField.getText().isEmpty()
        ) return null;


        return new Flight(
                flightNumberField.getText(),
                airlineField.getText(),
                originField.getText(),
                destinationField.getText(),
                new DateTime(departureTimeField.getText()),
                new DateTime(arrivalTimeField.getText()),
                aircraftTypeField.getText(),
                (FlightType) flightTypeField.getSelectedItem(),
                Double.parseDouble(priceField.getText())
        );
    }
}
