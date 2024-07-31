package com.company;

import com.company.controller.AppController;
import com.company.model.FlightTypeList;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        FlightTypeList flightTypeList = new FlightTypeList();
        new AppController(flightTypeList).setVisible(true);
    }
}