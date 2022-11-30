package org.example;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Source extends JPanel implements ActionListener, ListSelectionListener {

    JPanel panelTitle = new JPanel();
    JPanel panelControl = new JPanel();
    JPanel panelButton = new JPanel();
    JPanel panelText = new JPanel();
    JLabel labelNoteTitle = new JLabel("Note title: ");
    JTextField fieldNoteTitle = new JTextField();
    JButton buttonAddNote = new JButton("Add/Update");
    JButton buttonRemoveNote = new JButton("Remove");
    JButton buttonClean = new JButton("Clean");
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> listNotes = new JList<>(listModel);
    JScrollPane listNotesScroll = new JScrollPane(listNotes);
    JTextArea textAreaNote = new JTextArea();
    JScrollPane textAreaScroll = new JScrollPane(textAreaNote);

    public Source() {
        Database.initiateDatabase();
        applyListModel();

        this.setLayout(new BorderLayout());

        this.add(panelTitle, BorderLayout.NORTH);
        panelTitle.setLayout(new BorderLayout());
        panelTitle.add(labelNoteTitle, BorderLayout.WEST);
        panelTitle.add(fieldNoteTitle, BorderLayout.CENTER);

        this.add(panelControl, BorderLayout.WEST);
        panelControl.setLayout(new BorderLayout());
        panelControl.add(panelButton, BorderLayout.NORTH);
        panelButton.add(buttonAddNote);
        panelButton.add(buttonRemoveNote);
        panelButton.add(buttonClean);
        buttonAddNote.addActionListener(this);
        buttonRemoveNote.addActionListener(this);
        buttonClean.addActionListener(this);
        panelControl.add(listNotesScroll, BorderLayout.CENTER);
        listNotesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listNotes.addListSelectionListener(this);

        this.add(panelText, BorderLayout.CENTER);
        panelText.setLayout(new BorderLayout());
        textAreaNote.setLineWrap(true);
        textAreaNote.setWrapStyleWord(true);
        textAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelText.add(textAreaScroll, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonAddNote && !fieldNoteTitle.getText().isBlank()){
            Database.addNote(fieldNoteTitle.getText(), textAreaNote.getText());
            applyListModel();
        }

        if(e.getSource() == buttonRemoveNote){
            Database.removeNote();
            applyListModel();
            cleanAll();
        }

        if(e.getSource() == buttonClean){
            cleanAll();
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            String selectedRow = listNotes.getSelectedValue();
            int rowIndex = Integer.parseInt(selectedRow.substring(0, selectedRow.indexOf(":")));
            Database.setRowIndex(rowIndex);
            fieldNoteTitle.setText(selectedRow.substring(selectedRow.indexOf(":") + 2));
            textAreaNote.setText(Database.getNoteContent(rowIndex));
        }
    }

    private void applyListModel(){
        listModel.clear();
        for(String element : Database.getNoteList()){
            listModel.addElement(element);
        }
    }

    private void cleanAll(){
        fieldNoteTitle.setText(null);
        textAreaNote.setText(null);
        listNotes.clearSelection();
        Database.setRowIndex(-1);
    }

}
