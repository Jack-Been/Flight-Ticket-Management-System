package com.company.view;

import com.company.controller.AppController;
import com.company.model.Flight;
import com.company.model.Ticket;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.*;

public class TicketDetailPanel extends JPanel {
    private final JLabel title;
    private final JPanel dataPanel;
    private final JPanel operationPanel;
    private final JPanel confirmPanel;

    private final JTextField passengerNameField;
    private final JTextField passengerNationalityField;
    private final JTextField passengerPhoneNumberField;
    private final JTextField flightNumberField;
    private final JTextField seatNumberField;

    private final JLabel passengerNameLabel;
    private final JLabel passengerNationalityLabel;
    private final JLabel passengerPhoneNumberLabel;
    private final JLabel flightNumberLabel;
    private final JLabel seatNumberLabel;

    private final JComboBox<String> flightNumberComboBox;
    private final JComboBox<String> seatNumberComboBox;

    private final Set<String> flightNumbers;
    private final Map<String, Set<String>> availableSeats;

    private Ticket selectedTicket;

    public TicketDetailPanel(AppController listener) {
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

        passengerNameField = new JTextField();
        passengerNationalityField = new JTextField();
        passengerPhoneNumberField = new JTextField();
        flightNumberField = new JTextField();
        seatNumberField = new JTextField();

        passengerNameLabel = new JLabel();
        passengerNationalityLabel = new JLabel();
        passengerPhoneNumberLabel = new JLabel();
        flightNumberLabel = new JLabel();
        seatNumberLabel = new JLabel();

        flightNumberComboBox = new JComboBox<>();
        seatNumberComboBox = new JComboBox<>();

        flightNumberComboBox.getEditor().getEditorComponent().setVisible(false);
        seatNumberComboBox.getEditor().getEditorComponent().setVisible(false);

        flightNumberField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFlightNumberSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFlightNumberSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFlightNumberSuggestions();
            }
        });

        seatNumberField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSeatNumberSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSeatNumberSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSeatNumberSuggestions();
            }
        });

        flightNumberComboBox.addActionListener(e -> {
            if (flightNumberComboBox.getSelectedItem() != null) {
                flightNumberField.setText(flightNumberComboBox.getSelectedItem().toString());
                flightNumberField.setForeground(Color.BLACK);
                flightNumberComboBox.setVisible(false);
            }
        });

        seatNumberComboBox.addActionListener(e -> {
            if (seatNumberComboBox.getSelectedItem() != null) {
                seatNumberField.setText(seatNumberComboBox.getSelectedItem().toString());
                seatNumberField.setForeground(Color.BLACK);
                seatNumberComboBox.setVisible(false);
            }
        });

        flightNumberComboBox.setEditable(false);
        seatNumberComboBox.setEditable(false);
        flightNumberComboBox.setVisible(false);
        seatNumberComboBox.setVisible(false);

        Font font = new Font("Calibri", Font.BOLD, 16);
        gbc.weightx = 0.3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dataPanel.add(new JLabel("Passenger Name") {{
            setFont(font);
        }}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Nationality") {{
            setFont(font);
        }}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Phone number") {{
            setFont(font);
        }}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Flight Number") {{
            setFont(font);
        }}, gbc);
        gbc.gridy++;
        dataPanel.add(new JLabel("Seat Number") {{
            setFont(font);
        }}, gbc);

        gbc.weightx = 0.7;
        gbc.gridx = 1;
        gbc.ipadx = -50;
        gbc.gridy = 0;
        dataPanel.add(passengerNameLabel, gbc);
        dataPanel.add(passengerNameField, gbc);
        gbc.gridy++;
        dataPanel.add(passengerNationalityLabel, gbc);
        dataPanel.add(passengerNationalityField, gbc);
        gbc.gridy++;
        dataPanel.add(passengerPhoneNumberLabel, gbc);
        dataPanel.add(passengerPhoneNumberField, gbc);
        gbc.gridy++;
        dataPanel.add(flightNumberLabel, gbc);
        dataPanel.add(flightNumberField, gbc);
        dataPanel.add(flightNumberComboBox, gbc);
        gbc.gridy++;
        dataPanel.add(seatNumberLabel, gbc);
        dataPanel.add(seatNumberField, gbc);
        dataPanel.add(seatNumberComboBox, gbc);

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

        flightNumbers = new HashSet<>();
        availableSeats = new HashMap<>();

        hideAll();
    }

    public void refresh(Ticket ticket) {
        hideAll();

        selectedTicket = ticket;

        title.setText("Ticket Details");

        passengerNameLabel.setText(ticket.getPassengerName());
        passengerNationalityLabel.setText(ticket.getPassengerNationality());
        passengerPhoneNumberLabel.setText(ticket.getPassengerPhone());
        flightNumberLabel.setText(ticket.getFlightNumber());
        seatNumberLabel.setText(ticket.getSeatNumber());

        passengerNameLabel.setVisible(true);
        passengerNationalityLabel.setVisible(true);
        passengerPhoneNumberLabel.setVisible(true);
        flightNumberLabel.setVisible(true);
        seatNumberLabel.setVisible(true);

        title.setVisible(true);
        dataPanel.setVisible(true);
        operationPanel.setVisible(true);
    }

    public void refreshUpdate(Ticket ticket, List<Flight> flights, List<Ticket> tickets) {
        hideAll();

        updateComboBoxData(flights, tickets);

        selectedTicket = ticket;

        title.setText("Update Ticket");

        passengerNameField.setText(ticket.getPassengerName());
        passengerNationalityField.setText(ticket.getPassengerNationality());
        passengerPhoneNumberField.setText(ticket.getPassengerPhone());
        flightNumberField.setText(ticket.getFlightNumber());
        seatNumberField.setText(ticket.getSeatNumber());

        passengerNameField.setVisible(true);
        passengerNationalityField.setVisible(true);
        passengerPhoneNumberField.setVisible(true);
        flightNumberField.setVisible(true);
        seatNumberField.setVisible(true);

        title.setVisible(true);
        dataPanel.setVisible(true);
        confirmPanel.setVisible(true);

        flightNumberComboBox.setVisible(true);
        seatNumberComboBox.setVisible(true);
    }

    public void refreshAdd(List<Flight> flights, List<Ticket> tickets) {
        hideAll();

        updateComboBoxData(flights, tickets);

        title.setText("Add Ticket");

        passengerNameField.setText("");
        passengerNationalityField.setText("");
        passengerPhoneNumberField.setText("");
        flightNumberField.setText("");
        seatNumberField.setText("");

        passengerNameField.setVisible(true);
        passengerNationalityField.setVisible(true);
        passengerPhoneNumberField.setVisible(true);
        flightNumberField.setVisible(true);
        seatNumberField.setVisible(true);

        title.setVisible(true);
        dataPanel.setVisible(true);
        confirmPanel.setVisible(true);

        flightNumberComboBox.setVisible(true);
        seatNumberComboBox.setVisible(true);
    }

    public void hideAll() {
        selectedTicket = null;

        passengerNameLabel.setVisible(false);
        passengerNationalityLabel.setVisible(false);
        passengerPhoneNumberLabel.setVisible(false);
        flightNumberLabel.setVisible(false);
        seatNumberLabel.setVisible(false);
        passengerNameField.setVisible(false);
        passengerNationalityField.setVisible(false);
        passengerPhoneNumberField.setVisible(false);
        flightNumberField.setVisible(false);
        seatNumberField.setVisible(false);

        title.setVisible(false);
        dataPanel.setVisible(false);
        operationPanel.setVisible(false);
        confirmPanel.setVisible(false);

        flightNumberComboBox.setVisible(false);
        seatNumberComboBox.setVisible(false);
    }

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public Ticket getTicket() {
        if (
                passengerNameField.getText().isEmpty() ||
                        passengerNationalityField.getText().isEmpty() ||
                        passengerPhoneNumberField.getText().isEmpty() ||
                        flightNumberField.getText().isEmpty() ||
                        flightNumberField.getForeground().equals(Color.RED) ||
                        seatNumberField.getText().isEmpty() ||
                        seatNumberField.getForeground().equals(Color.RED)
        ) return null;

        return new Ticket(
                seatNumberField.getText(),
                flightNumberField.getText(),
                passengerNameField.getText(),
                passengerNationalityField.getText(),
                passengerPhoneNumberField.getText()
        );
    }

    private void updateComboBoxData(List<Flight> flights, List<Ticket> tickets) {
        flightNumbers.clear();
        availableSeats.clear();

        for (Flight flight : flights) {
            flightNumbers.add(flight.getFlightNumber());
            availableSeats.put(flight.getFlightNumber(), new HashSet<>());
        }

        for (Ticket ticket : tickets)
            availableSeats.get(ticket.getFlightNumber()).add(ticket.getSeatNumber());

        for (Flight flight : flights) {
            Set<String> reservedSeats = availableSeats.get(flight.getFlightNumber());
            availableSeats.replace(flight.getFlightNumber(), new HashSet<>());
            for (int i = 1; i <= Ticket.TOTAL_SEATS; i++) {
                String seatNumber = String.valueOf(i);
                if (!reservedSeats.contains(seatNumber))
                    availableSeats.get(flight.getFlightNumber()).add(seatNumber);
            }
        }
    }

    private void updateFlightNumberSuggestions() {
        if (!flightNumberField.isVisible())
            return;

        String input = flightNumberField.getText();
        if (input.isEmpty()) {
            flightNumberComboBox.setPopupVisible(false);
            return;
        }

        List<String> suggestions = new ArrayList<>();
        for (String suggestion : flightNumbers)
            if (suggestion.toLowerCase().startsWith(input.toLowerCase()))
                suggestions.add(suggestion);

        if (!suggestions.isEmpty()) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addAll(suggestions);
            flightNumberComboBox.setModel(model);
            flightNumberComboBox.setPopupVisible(true);
            flightNumberComboBox.showPopup();
        } else
            flightNumberComboBox.setPopupVisible(false);

        if (suggestions.contains(input)) {
            flightNumberField.setForeground(Color.BLACK);
            if (suggestions.size() == 1)
                flightNumberComboBox.setPopupVisible(false);
        } else
            flightNumberField.setForeground(Color.RED);
    }

    private void updateSeatNumberSuggestions() {
        if (!seatNumberField.isVisible())
            return;

        String input = seatNumberField.getText();
        if (input.isEmpty()) {
            seatNumberComboBox.setPopupVisible(false);
            return;
        }

        if (!availableSeats.containsKey(flightNumberField.getText())) {
            seatNumberField.setForeground(Color.RED);
            seatNumberComboBox.setPopupVisible(false);
            return;
        }

        List<String> suggestions = new ArrayList<>();
        for (String suggestion : availableSeats.get(flightNumberField.getText()))
            if (suggestion.toLowerCase().startsWith(input.toLowerCase()))
                suggestions.add(suggestion);

        if (selectedTicket != null && flightNumberField.getText().equals(selectedTicket.getFlightNumber()))
            if (selectedTicket.getSeatNumber().toLowerCase().startsWith(input.toLowerCase()))
                suggestions.add(selectedTicket.getSeatNumber());

        if (!suggestions.isEmpty()) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addAll(suggestions);
            seatNumberComboBox.setModel(model);
            seatNumberComboBox.setPopupVisible(true);
            seatNumberComboBox.showPopup();
        } else
            seatNumberComboBox.setPopupVisible(false);

        if (suggestions.contains(input)) {
            seatNumberField.setForeground(Color.BLACK);
            if (suggestions.size() == 1)
                seatNumberComboBox.setPopupVisible(false);
        } else
            seatNumberField.setForeground(Color.RED);
    }
}
