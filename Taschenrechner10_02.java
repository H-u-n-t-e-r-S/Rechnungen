package java10_C;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Taschenrechner10_02 extends JFrame {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
// 	die Liste für Kombinationsfeld über eine Array erstellen
	private String[] operationsAuswahl = { "addition", "subtraktion", "multiplikation", "division" };

	// Zwei Eingabefelder
	private JTextField eingabe1, eingabe2;

//	ein Label für Ausgabe
	private JLabel ausgabe;

//	vier RadioButtons. Wir brauchen sie nicht mehr.
//	private JRadioButton addition, subtraktion, multiplikation, division;
//	die Kombinationsfeld erstellen
	private JComboBox<String> operations;

//	zwei Schaltflächen
	private JButton beenden, berechnen;

	class MeinListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object ausloeser = e.getSource();
			if(ausloeser instanceof JComboBox) {
				if (operations.getSelectedItem().equals("addition"))
					ausgabe.setText(berechnen());
			}
			if (ausloeser instanceof JButton) {
//				 wurde auf Beenden geklickt? dann das Programm beenden
				if (e.getActionCommand().equals("ende"))
					System.exit(0);
//				 wurde auf Berechnen geklickt?dann eine Methode für die Berechnung aufrufen und das Ergebnis anzeigen
				if (e.getActionCommand().equals("rechnen")) {
					ausgabe.setText(berechnen());
				}
			}
		}
	}

//		 der Konstruktor
	public Taschenrechner10_02(String titel) {

		super(titel);

//		 insgesamt drei Panels
//		JPanel panelBerechnung, panelButtons;

		// die Panels über die Methoden erstellen
//		panelEinAus = panelEinAus();
//		panelBerechnung = panelBerechnung();
//		panelButtons = panelButtons();

//		ein Gridlayout mit 3 Spalten
		setLayout(new GridLayout(0, 3));

//		die Panels hinzufügen. Ohne neue Veriablen erzeugen
		add(panelEinAus());
		add(panelBerechnung());
		add(panelButtons());

//		 die Standard-Aktion setzen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		 packen und anzeigen
		pack();
		setVisible(true);

		// Größenänderungen sind nicht zugelassen
		// damit das mühsam erstellte Layout nicht durcheinander kommt
		setResizable(false);
	}

//	die Methode erzeugt das Panel für die Ein- und Ausgabe und liefert es zurück
	private JPanel panelEinAus() {
		JPanel tempPanel = new JPanel();
//		es enthält die Eingabefelder mit beschreibendem Text. Die Länge der Felder isst auf 10 Zeichen beschränkt
		eingabe1 = new JTextField(10);
		eingabe2 = new JTextField(10);
		ausgabe = new JLabel("");

//		das Panel bekommt ein GridLayout mit 2 Spalten und etwas Abstand
		tempPanel.setLayout(new GridLayout(0, 2, 10, 10));
//		beschreibendes Label fur erste eingabe
		tempPanel.add(new JLabel("Zahl 1:"));
//		erste Eingabefeld
		tempPanel.add(eingabe1);
		tempPanel.add(new JLabel("Zahl 2:"));
		tempPanel.add(eingabe2);

//		das Ausgabefeld für das Ergebnis
		tempPanel.add(new JLabel("Ergebnis:"));
		tempPanel.add(ausgabe);

//		eine Rahmen um das Panel ziehen
		tempPanel.setBorder(new TitledBorder("Ein- und Ausgabe"));

		return tempPanel;
	}

//	die Methode erzeugt das Panel für die Auswahl der Rechenoperation und liefert es zurück
	private JPanel panelBerechnung() {
		JPanel tempPanel = new JPanel();
//		die Liste erzeugen. Die Daten kommen aus dem Array farbAuswahl
		operations = new JComboBox<String>(operationsAuswahl);

//		das Panel bekommt ein GridLayout mit 1 Spalte
		tempPanel.setLayout(new GridLayout(4, 0));
		tempPanel.add(operations);

//		und eine Rahmen
		tempPanel.setBorder(new TitledBorder("Operation: "));

		return tempPanel;
	}

//	die Methode erzeugt das panel für die Schaltflächen und liefert es zurück
	private JPanel panelButtons() {
		JPanel tempPanel = new JPanel();

		beenden = new JButton("Beenden");
//		das ActionCommand setzen
		beenden.setActionCommand("ende");
		berechnen = new JButton("Berechnen");
		berechnen.setActionCommand("rechnen");

//		Zwieschen Panel für die Schaltflächen. Das Panel wir linksbündig ausgerichtet mit mehr Abstand
		tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		tempPanel.add(beenden);
		tempPanel.add(berechnen);

//		die Schaltflächen mit dem Listener verbinden
		MeinListener listener = new MeinListener();
		beenden.addActionListener(listener);
		berechnen.addActionListener(listener);

		return tempPanel;

	}

	// die Methode berechnet das Ergebnis und liefert es als
	// Zeichenkette zurück
	private String berechnen() {
		// ergebnis muss initialisiert werden
		// obwohl es in jedem Fall einen Wert bekommt
		// das kann der Compiler aber nicht erkennen :)
		double zahl1, zahl2, ergebnis = 0;
		// für die Steuerung der Rückgabe
		boolean fehlerFlag = false;
		
		try {
			// die Werte beschaffen, umformen und zuweisen
			zahl1 = Double.parseDouble(eingabe1.getText());
			zahl2 = Double.parseDouble(eingabe2.getText());
		} catch (NumberFormatException e) {
			fehlermeldung(eingabe1);
			return ("Nicht definiert");
		}
//		 welche Operation ist ausgewählt?
		if (operations.getSelectedItem().toString().equals("addition"))
			ergebnis = zahl1 + zahl2;
		if (operations.getSelectedItem().toString().equals("subtraktion"))
			ergebnis = zahl1 - zahl2;
		if (operations.getSelectedItem().toString().equals("multiplikation"))
			ergebnis = zahl1 * zahl2;
//		 bei der Division überprüfen wir den zweiten Wert auf 0
		if (operations.getSelectedItem().toString().equals("division")) {
			if (zahl2 != 0)
				ergebnis = zahl1 / zahl2;
			else
				fehlerFlag = true;
		}

//		 wenn es keine Probleme gegeben hat, liefern wir das Ergebnis zurück.# zeigt
//		 wie viele Zahlen nach dem Komma würden gezeigt
		if (fehlerFlag == false) {
			DecimalFormat format = new DecimalFormat("0.###");
			return (format.format(ergebnis));
		} else
			return ("Nicht definiert");

	}

	private void fehlermeldung(JTextField eingabefeld) {
		JOptionPane.showMessageDialog(this, "Ihre Eingabe ist nicht gültig", "Eingabefehler",
				JOptionPane.ERROR_MESSAGE);
		eingabefeld.requestFocus();
	}

	public static void main(String[] args) {
		new Taschenrechner10_02("Taschenrechner GUI");

	}

}