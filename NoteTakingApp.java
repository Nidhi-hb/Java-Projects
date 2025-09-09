import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NoteTakingApp extends JFrame {

    private JTextArea textArea;
    private JFileChooser fileChooser;

    public NoteTakingApp() {
        setTitle("Colorful Note Taking App");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Text Area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setBackground(new Color(250, 250, 210)); // Light Yellow background
        textArea.setForeground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu colorMenu = new JMenu("Colors");

        // File options
        JMenuItem newNote = new JMenuItem("New");
        JMenuItem openNote = new JMenuItem("Open");
        JMenuItem saveNote = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Exit");

        fileMenu.add(newNote);
        fileMenu.add(openNote);
        fileMenu.add(saveNote);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        // Color options
        JMenuItem textColor = new JMenuItem("Change Text Color");
        JMenuItem bgColor = new JMenuItem("Change Background Color");

        colorMenu.add(textColor);
        colorMenu.add(bgColor);

        menuBar.add(fileMenu);
        menuBar.add(colorMenu);

        setJMenuBar(menuBar);

        // File chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        // Actions
        newNote.addActionListener(e -> textArea.setText(""));

        openNote.addActionListener(e -> {
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    textArea.read(br, null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saveNote.addActionListener(e -> {
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    textArea.write(bw);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exit.addActionListener(e -> System.exit(0));

        textColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose Text Color", textArea.getForeground());
            if (color != null) {
                textArea.setForeground(color);
            }
        });

        bgColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose Background Color", textArea.getBackground());
            if (color != null) {
                textArea.setBackground(color);
            }
        });

        add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NoteTakingApp app = new NoteTakingApp();
            app.setVisible(true);
        });
    }
}
