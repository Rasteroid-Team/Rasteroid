package View;

import javax.swing.*;
import java.awt.*;

public class ConnectionPanel {
    public String[] values;

    public ConnectionPanel(Frame holder) {
        this.values = this.showInputs(holder);
    }

    private String[] showInputs(Frame frame) {
        JPanel panel = new JPanel();
        JTextField port = new JTextField();
        JTextField direction = new JTextField();

        port.setVisible(true);
        direction.setVisible(true);

        port.setColumns(5);
        direction.setColumns(2);

        panel.add(port);
        panel.add(direction);

        JOptionPane.showMessageDialog(frame, panel);

        return new String[]{String.valueOf(port.getText()), String.valueOf(direction.getText())};
    }


}
