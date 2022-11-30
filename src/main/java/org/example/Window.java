package org.example;

import javax.swing.JFrame;

public class Window extends JFrame {

    Window(){
        this.add(new Source());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(640,480);
        this.setTitle("NoteLite");
    }

}
