package com.company.view;

import com.company.controller.AppController;
import com.company.model.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class FlightPanel extends JPanel {
    private final FlightDetailPanel flightDetailPanel;
    private final JTable table;

    public FlightPanel(AppController listener, List<Flight> flights, FlightTypeList flightTypeList) {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel title = new JLabel("Airline Reservation System (Flight)");
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

        table = new JTable(new FlightTableModel(flights));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        add(scrollPane, BorderLayout.CENTER);

        table.setFillsViewportHeight(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Set selection mode to single selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set Column Widths
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(40);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(8).setPreferredWidth(60);

        DefaultRowSorter<TableModel, Integer> sorter = new TableRowSorter<>(table.getModel());
        sorter.setComparator(4, Comparator.comparing(DateTime::getDateTime));
        sorter.setComparator(5, Comparator.comparing(DateTime::getDateTime));
        sorter.setComparator(7, Comparator.comparing(FlightType::getType));
        table.setRowSorter(sorter);

        table.getSelectionModel().addListSelectionListener(listener);

        add(bottomPanel, BorderLayout.SOUTH);

        Dimension buttonSize = new Dimension(100, 30);
        nextButton.setPreferredSize(buttonSize);
        previousButton.setPreferredSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);

        flightDetailPanel = new FlightDetailPanel(listener, flightTypeList);
        add(flightDetailPanel, BorderLayout.EAST);
    }

    public FlightDetailPanel getDetailPanel() {
        return flightDetailPanel;
    }

    public JTable getTable() {
        return table;
    }
}
