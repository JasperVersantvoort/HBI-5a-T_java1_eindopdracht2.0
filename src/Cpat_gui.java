/**
 * @author Jasper Versantvoort
 * studentennummer 634664
 * datum 06-11-2020
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Cpat_gui {
    private JPanel panel1;
    private JTextField geefBestandTextField;
    private JButton blader1Button;
    private JTextField geefBestandTextField1;
    private JButton blader2Button;
    private JButton analyseerHetConsensusProteoomButton;
    private JTextArea textArea1;
    private final JPanel tekenpanel;

    public static void main(String[] args) {
        /**
         * main functie van deze code
         * Het frame wordt gemaakt en gepakt
         */
        JFrame frame = new JFrame("Cpat_gui");
        frame.setContentPane(new Cpat_gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Grote ingesteld en er wordt gezorgd dat je het frame niet kan veranderen.
        frame.setPreferredSize(new Dimension(700, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public Cpat_gui() {
        /**
         * functie: de files worden gelezen, file1 kijk per regel of de protein code in file 2 zit.
         * Wanneer dit zo is wordt dit opgeslagen
         * uiteindelijke worden de overlappingen, en de unieke regels gevisualiseerd met behulp van ovalen.
         * @exception wanneer het bestand niet gevonden kan worden wordt er een fileNotFoundException gegeven
         * @exception wanneer een protein code niet begint met 'AT' wordt er een NotValidProteinCode exception gegeven
         */
        tekenpanel = new JPanel();
        tekenpanel.setPreferredSize(new Dimension(600, 200));
        tekenpanel.setBackground(Color.white);
        panel1.add(tekenpanel);
        tekenpanel.setVisible(true);
        blader1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * @functie Laat je een bestand kiezen deze wordt in geefBestandTextField geplaatst
                 */
                File selectedFile;
                JFileChooser fileChooser = new JFileChooser();
                int reply = fileChooser.showOpenDialog(null);
                if (reply == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    geefBestandTextField.setText(selectedFile.getAbsolutePath());
                }

            }
        });
        blader2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * functie: Laat je een bestand kiezen deze wordt in geefBestandTextField1 geplaatst
                 */
                File selectedFile;
                JFileChooser fileChooser = new JFileChooser();
                int reply = fileChooser.showOpenDialog(null);
                if (reply == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    geefBestandTextField1.setText(selectedFile.getAbsolutePath());
                }

            }
        });
        analyseerHetConsensusProteoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * functie: de aantal overeenkomende en unieke protein codes worden berekend.
                 * Daarna wordt dit gevisualiseerd
                 * @exception wanneer het bestand niet gevonden kan worden wordt er een fileNotFoundException gegeven
                 * @exception wanneer een protein code niet begint met 'AT' wordt er een NotValidProteinCode exception gegeven
                 */
                String line;
                String line2;
                int gelijk = 0;
                int aantal_file1 = 0;
                int aantal_file2 = 0;
                int uniek_bestand1 = 0;
                int uniek_bestand2 = 0;
                try {
                    BufferedReader inFile = new BufferedReader(new FileReader(geefBestandTextField.getText()));
                    inFile.readLine();
                    while ((line = inFile.readLine()) != null) {
                        int mogelijk_aantal_file2 = 0;
                        aantal_file1 += 1;
                        BufferedReader inFile2 = new BufferedReader(new FileReader(geefBestandTextField1.getText()));
                        inFile2.readLine();
                        System.out.println("nieuw file1");
                        if (line.substring(0, 2).equals("AT")) {

                            while ((line2 = inFile2.readLine()) != null) {
                                mogelijk_aantal_file2 += 1;
                                System.out.println("nieuw file2");
                                if (line.substring(0, 2).equals("AT")) {
                                    System.out.println("goed met AT");
                                    if (line.substring(0, 11).equals(line2.substring(0, 11))) {
                                        System.out.println("Overeenkomst");
                                        gelijk += 1;
                                        break;
                                    } else {
                                        System.out.println("geen overeenkomst");
                                    }

                                } else {
                                    throw new NotValidProteinCode();

                                }
                            }
                            if (mogelijk_aantal_file2 > aantal_file2) {
                                aantal_file2 = mogelijk_aantal_file2;
                            }
                        } else {
                            throw new NotValidProteinCode();
                        }
                    }
                    uniek_bestand1 = aantal_file1 - gelijk;
                    uniek_bestand2 = aantal_file2 - gelijk;
                    textArea1.setText(
                            "Regels bestand 1 = " + aantal_file1 + "\n" +
                                    "Regels bestand 2 = " + aantal_file2 + "\n" +
                                    "Overlappende regels = " + gelijk + "\n" +
                                    "Unieke regels bestand 1 = " + uniek_bestand1 + "\n" +
                                    "Unieke regels bestand 2 = " + uniek_bestand2);

                    Graphics tekenveld = tekenpanel.getGraphics();

                    tekenveld.setColor(Color.red);
                    tekenveld.drawOval(50, 50, 300, 100);
                    tekenveld.drawString("# Uniek bestand 1", 20, 20);
                    tekenveld.drawString(Integer.toString(uniek_bestand1), 150, 100);

                    tekenveld.setColor(Color.blue);
                    tekenveld.drawOval(250, 50, 300, 100);
                    tekenveld.drawString("# Uniek bestand 2", 350, 20);
                    tekenveld.drawString(Integer.toString(uniek_bestand2), 400, 100);

                    tekenveld.setColor(Color.green);
                    tekenveld.drawString("# Overlap in bestanden", 250, 170);
                    tekenveld.drawString(Integer.toString(gelijk), 300, 100);
                } catch (IOException fileNotFoundException) {
                    JOptionPane.showMessageDialog(null,
                            "bestand(en) niet gevonden probeer opnieuw");
                } catch (NotValidProteinCode er) {
                    JOptionPane.showMessageDialog(null, er.getMessage());
                }


            }
        });
    }
}
