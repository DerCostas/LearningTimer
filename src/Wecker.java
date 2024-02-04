import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class Wecker extends JFrame {

    private static final long serialVersionUID = 1L;
    public static int minClicks = 1800;
    public static Timer timer;
    JLabel zeit;
    boolean display5Next = true;
    ImageIcon icon = new ImageIcon("D:\\programming\\timer\\lerning timer\\Resources\\sanduhr.png");
    private JButton plusTen, plusFive, plusOne, minusTen, minusFive, minusOne, start, reset, pause;
    private JPanel minusSide, plusSide, startReset;

    private final Color defaultLabelColor;

    public Wecker() {
        bauen();
        addButton();
        methoden();
        defaultLabelColor = zeit.getBackground();
    }

    public static void main(String[] args) {
        JFrame frame = new Wecker();
        frame.setVisible(true);
    }

    public void bauen() {
        this.setSize(400, 280);
        this.setLocationRelativeTo(null);
        this.setTitle("Timer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(false);
        this.setFocusable(true);
        this.requestFocus();
        this.setIconImage(icon.getImage());

        plusTen = createButton("+ 10", 24, new plusTenAction());
        plusFive = createButton("+ 5", 24, new plusFiveAction());
        plusOne = createButton("+ 1", 24, new plusOneAction());
        minusTen = createButton("- 10", 24, new minusTenAction());
        minusFive = createButton("- 5", 24, new minusFiveAction());
        minusOne = createButton("- 1", 24, new minusOneAction());

        pause = createButton("Pause", 14, new PauseAction());
        pause.setEnabled(false);

        start = createButton("Start", 14, new startAction());
        start.setBackground(Color.green);
        reset = createButton("Reset", 14, new resetAction());

        plusTen.addActionListener(new labelColourAction());
        plusFive.addActionListener(new labelColourAction());
        plusOne.addActionListener(new labelColourAction());
        minusTen.addActionListener(new labelColourAction());
        minusFive.addActionListener(new labelColourAction());
        minusOne.addActionListener(new labelColourAction());
        pause.addActionListener(new labelColourAction());
        start.addActionListener(new labelColourAction());
        reset.addActionListener(new labelColourAction());

        zeit = new JLabel("30:00");
        zeit.setHorizontalAlignment(JLabel.CENTER);
        zeit.setFont(new Font("Verdana", Font.PLAIN, 42));
        zeit.setOpaque(true);
        zeit.setBackground(null);

        // Add component listeners to adjust the font size when the components are resized
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustTextSize(plusTen);
                adjustTextSize(plusFive);
                adjustTextSize(plusOne);
                adjustTextSize(minusTen);
                adjustTextSize(minusFive);
                adjustTextSize(minusOne);
                adjustTextSize(pause);
                adjustTextSize(start);
                adjustTextSize(reset);
                adjustLabelTextSize(zeit);
            }
        });
    }
    private JButton createButton(String text, int fontSize, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Verdana", Font.BOLD, fontSize));
        button.addActionListener(action);
        return button;
    }
    public void addButton() {

        minusSide = new JPanel();
        minusSide.setLayout(new GridLayout(3, 1));
        minusSide.add(minusTen);
        minusSide.add(minusFive);
        minusSide.add(minusOne);

        plusSide = new JPanel();
        plusSide.setLayout(new GridLayout(3, 1));
        plusSide.add(plusTen);
        plusSide.add(plusFive);
        plusSide.add(plusOne);

        startReset = new JPanel();
        startReset.setLayout(new GridLayout(1, 2));
        startReset.add(start);
        startReset.add(reset);

        getContentPane().add(startReset, BorderLayout.PAGE_START);
        getContentPane().add(plusSide, BorderLayout.LINE_START);
        getContentPane().add(minusSide, BorderLayout.LINE_END);
        getContentPane().add(pause, BorderLayout.PAGE_END);
        getContentPane().add(zeit, BorderLayout.CENTER);
    }

    public void methoden() {
        Wecker.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Wecker.minClicks--;
                    zeit.setText(displayTime());
                    zeit.setBackground(defaultLabelColor);
                if (minClicks == 3) {
                    setState(NORMAL);
                    setAlwaysOnTop(true);
                }
                if (minClicks == 0) {
                    Wecker.timer.stop();
                    start.setBackground(null);
                    pause.setEnabled(false);
                    minusTen.setEnabled(true);
                    minusFive.setEnabled(true);
                    minusOne.setEnabled(true);
                    setAlwaysOnTop(false);
                    playBuiltInSound("boxing-bell.wav");
                    if(display5Next){
                        minClicks = 5*60;
                        display5Next = !display5Next;
                    }else{
                        minClicks = 30*60;
                        display5Next = !display5Next;
                    }
                    zeit.setBackground(Color.green);
                    zeit.setText(displayTime());
                }
            }
        });
    }

    public void playBuiltInSound(String resourcePath) {
        try (InputStream soundStream = getClass().getResourceAsStream(resourcePath)) {
            if (soundStream != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundStream);

                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Exception during sound playback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String displayTime() {
        if (minClicks % 60 != 0) {
            if (minClicks % 60 >= 10) return ("" + minClicks / 60 + ":" + minClicks % 60);
            else return ("" + minClicks / 60 + ":0" + minClicks % 60);
        } else return ("" + minClicks / 60 + ":00");
    }

    private class labelColourAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            zeit.setBackground(defaultLabelColor);
        }
    }
    private class plusTenAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            minClicks += 600;
            zeit.setText(displayTime());
            setStartColour();
        }
    }

    private class plusFiveAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            minClicks += 300;
            zeit.setText(displayTime());
            setStartColour();
        }
    }

    private class plusOneAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            minClicks += 60;
            zeit.setText(displayTime());
            setStartColour();
        }
    }

    private class minusTenAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks >= 1) {
                minClicks -= 600;
            }
            setStartColour();
            zeit.setText(displayTime());
        }
    }

    private class minusFiveAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks >= 1) {
                minClicks -= 300;
            }
            setStartColour();
            zeit.setText(displayTime());
        }
    }

    private class minusOneAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks >= 1) {
                minClicks -= 60;
            }
            setStartColour();
            zeit.setText(displayTime());
        }
    }

    private class resetAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Wecker.timer.stop();
            minusTen.setEnabled(true);
            minusFive.setEnabled(true);
            minusOne.setEnabled(true);
            pause.setEnabled(false);
            minClicks = 0;
            start.setBackground(null);
            zeit.setText(displayTime());
        }
    }

    private class startAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (minClicks > 0) {
                zeit.setText(displayTime());
                timer.start();
                minusTen.setEnabled(false);
                minusFive.setEnabled(false);
                minusOne.setEnabled(false);
                pause.setEnabled(true);
                start.setBackground(null);
            }
        }
    }

    private class PauseAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (timer.isRunning()) {
                timer.stop();
                zeit.setText("<html>" + displayTime() + "<br>Paused</html>");
                pause.setText("Resume");
            } else {
                timer.start();
                zeit.setText(displayTime());
                pause.setText("Pause");
            }
        }
    }

    private void setStartColour(){
        if (minClicks > 0) {
            start.setBackground(Color.green);
        }else{
            start.setBackground(null);
        }
    }

    private void adjustTextSize(JComponent component) {
        int width = component.getWidth();
        int height = component.getHeight();

        // Calculate the new font size based on the component size
        int fontSize = (int) (Math.min(width, height) * 0.5);

        // Set the new font size for the component
        Font currentFont = component.getFont();
        Font newFont = currentFont.deriveFont((float) fontSize);
        component.setFont(newFont);
    }

    private void adjustLabelTextSize(JLabel label) {
        int width = label.getWidth();
        int height = label.getHeight();
        Font currentFont = label.getFont();

        // Create a FontMetrics object to measure text dimensions
        FontMetrics fontMetrics = label.getFontMetrics(currentFont);
        String labelText = label.getText();
        int textWidth = fontMetrics.stringWidth(labelText);
        int textHeight = fontMetrics.getHeight();

        // Calculate the new font size based on the component size
        int fontSize = calculateMaxFontSize(currentFont, labelText, width, height);

        // Set the new font size for the label
        Font newFont = currentFont.deriveFont((float) fontSize);
        label.setFont(newFont);
    }

    private int calculateMaxFontSize(Font font, String text, int maxWidth, int maxHeight) {
        // Create a temporary JLabel to measure the preferred size of the text
        JLabel tempLabel = new JLabel(text);
        tempLabel.setFont(font);

        // Get the preferred size of the text
        Dimension preferredSize = tempLabel.getPreferredSize();

        // Calculate the maximum font size that fits within the maxWidth and maxHeight
        int widthRatio = (int) Math.floor((double) maxWidth / preferredSize.width);
        int heightRatio = (int) Math.floor((double) maxHeight / preferredSize.height);

        // Use the smaller ratio to ensure that the text fits both in width and height
        int maxFontSize = (int) (font.getSize() * Math.min(widthRatio, heightRatio));

        // Set a minimum font size to prevent the text from becoming too small
        int minFontSize = 10; // Adjust as needed
        return Math.max(maxFontSize, minFontSize);
    }

}