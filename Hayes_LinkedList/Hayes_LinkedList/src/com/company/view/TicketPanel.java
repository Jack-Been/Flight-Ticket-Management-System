package com.company.view;

import com.company.controller.AppController;
import com.company.model.Ticket;
import com.company.model.TicketTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class TicketPanel extends JPanel {
    private final TicketDetailPanel ticketDetailPanel;
    private final JTable table;

    public TicketPanel(AppController listener, List<Ticket> tickets) {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel title = new JLabel("Airline Reservation System (Ticket)");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous");
        JButton addButton = new JButton("Add");
        JButton quitButton = new JButton("Quit");

        bottomPanel.add(previousButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(addButton);
        bottomPanel.add(quitButton);

        nextButton.addActionListener(listener);
        previousButton.addActionListener(listener);
        addButton.addActionListener(listener);
        quitButton.addActionListener(listener);

        table = new JTable(new TicketTableModel(tickets));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        add(scrollPane, BorderLayout.CENTER);

        table.setFillsViewportHeight(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Set selection mode to single selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set Column Widths
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);

        table.getSelectionModel().addListSelectionListener(listener);

        table.setRowSorter(new TableRowSorter<>(table.getModel()));

        add(bottomPanel, BorderLayout.SOUTH);

        Dimension buttonSize = new Dimension(100, 30);
        nextButton.setPreferredSize(buttonSize);
        previousButton.setPreferredSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);

        ticketDetailPanel = new TicketDetailPanel(listener);
        add(ticketDetailPanel, BorderLayout.EAST);
    }

    public TicketDetailPanel getDetailPanel() {
        return ticketDetailPanel;
    }

    public JTable getTable() {
        return table;
    }
}
