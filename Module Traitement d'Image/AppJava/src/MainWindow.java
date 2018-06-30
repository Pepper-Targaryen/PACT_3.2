import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MainWindow {
	
	private static String currentName;
	
	private static String currentMode = "name";
	private static String nameOfPlan = "plan1";
	private static String adminName = "defaultName";
	private static Stack deuxEscalier = new Stack();
	
	private static void affMessage(String message) {
		/*JFrame window2 = new JFrame("Message");
		JLabel label = new JLabel(message);
		window2.getContentPane().add(label,BorderLayout.NORTH);
		
		JButton okButton = new JButton("D'accord");
		okButton.addActionListener(event -> { 
			window2.setVisible(false);	
			window2.dispose();FileOutputStream
		});
		window2.getContentPane().add(okButton,BorderLayout.SOUTH);
		window2.dispose();
		window2.setPreferredSize(new Dimension(800, 100));
		window2.pack();
		window2.setVisible(true);*/
	}
	
	private static void nommer(int x,int y,JLabel dernierAjout) {
		JFrame window2 = new JFrame("Ecran de nom");
		JLabel label = new JLabel("Qu'y a t-il a cet endroit?");
		JTextField textField = new JTextField(20);
		window2.getContentPane().add(label,BorderLayout.NORTH);
		window2.getContentPane().add(textField);
		
		JButton okButton = new JButton("D'accord");
		okButton.addActionListener(event -> { 
			String nom = textField.getText();//recuperer ici le texte
			addName(nom,x,y);
			dernierAjout.setText(nom);
			window2.setVisible(false);	
			window2.dispose();
		});
		window2.getContentPane().add(okButton,BorderLayout.SOUTH);
		window2.dispose();
		window2.setPreferredSize(new Dimension(400, 300));
		window2.pack();
		window2.setVisible(true);
		
	}
	
	
	
	public static void addName(String nom,int x,int y) { 
	    
		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("./data/NomEscalier.txt", true)));
		    out.println(nameOfPlan+"/"+nom+"/"+x+"/"+y);
		    
		} catch (IOException e) {
		    System.err.println(e);
		}finally{
		    if(out != null){
		        out.close();
		    }
		} 
		
		
	}
	
	public static void addEscalier(int x,int y, String nameOfPlan) { // TODO
		
		//System.out.println(nameOfPlan+": "+x+", "+y);
		deuxEscalier.push(nameOfPlan+"/"+x+"/"+y);
		if(deuxEscalier.size()==2){
			String message = deuxEscalier.pop()+"/"+deuxEscalier.pop();
			System.out.println(message);
			
			PrintWriter out = null;

			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("./data/NomEscalier.txt", true)));
			    out.println(message);
			    
			} catch (IOException e) {
			    System.err.println(e);
			}finally{
			    if(out != null){
			        out.close();
			    }
			} 
		}
		
	}
	
	public static void printQR(int x,int y) {
		//https://pact32.regar42/buildings/<id_de_l'administrateur>/<nom_du_plan_sans_extension>;coordX,coordY
		String txt = "https://pact32.regar42.fr/buildings/" + adminName+"/"+nameOfPlan+";"+x+","+y;
		
		JFrame window2 = new JFrame("Votre QR code:");
		JLabel label = new JLabel("Le texte de votre QR code:");
		JLabel label2 = new JLabel(txt);
		window2.getContentPane().add(label,BorderLayout.NORTH);
		window2.getContentPane().add(label2,BorderLayout.CENTER);

		
		JButton okButton = new JButton("D'accord");
		okButton.addActionListener(event -> { 
			window2.setVisible(false);	
			window2.dispose();
		});
		window2.getContentPane().add(okButton,BorderLayout.SOUTH);
		window2.dispose();
		window2.setPreferredSize(new Dimension(900, 300));
		window2.pack();
		window2.setVisible(true);
	}

	public static void addStairs(int x,int y) { // TODO
	
		//System.out.println("addStairs n'est pas implementee pour l'instant.");
		JFrame window2 = new JFrame("Ecran d'escalier");
		JLabel label = new JLabel("C'est un escalier?");
		//JTextField textField = new JTextField(20);
		window2.getContentPane().add(label,BorderLayout.NORTH);
		//window2.getContentPane().add(textField);
		
		JButton okButton = new JButton("D'accord");
		okButton.addActionListener(event -> { 
			//String nom = textField.getText();//recuperer ici le texte
			addEscalier(x,y, nameOfPlan);
			//dernierAjout.setText(nom);
			window2.setVisible(false);	
			window2.dispose();
		});
		window2.getContentPane().add(okButton,BorderLayout.SOUTH);
		window2.dispose();
		window2.setPreferredSize(new Dimension(400, 300));
		window2.pack();
		window2.setVisible(true);
	
	}
		
	
	
	// Affiche une fenetre pour enregistrer le fichier
	private static void askName() {
		JFrame window2 = new JFrame("Enregistrer fichier");
		
		JPanel textLabel = new JPanel();
		JTextField textField = new JTextField(12);
		window2.getContentPane().add(textLabel,BorderLayout.CENTER);
		textLabel.add(textField, BorderLayout.NORTH) ;
		textField.setVisible(true) ;
		
		JLabel label = new JLabel("Nom du fichier :");
		window2.getContentPane().add(label,BorderLayout.NORTH);
		
		JButton saveButton = new JButton("Enregistrer");
		saveButton.addActionListener(event -> { 
			String name = textField.getText();
			File repertoire = null;
			try {
		    	repertoire = new File(".").getCanonicalFile() ;
			} catch(IOException e) {affMessage("Erreur lors de l'accès au répertoire courant");}
			
			window2.setVisible(false);	
			window2.dispose();
		});
		window2.getContentPane().add(saveButton,BorderLayout.SOUTH);
		
		
		window2.dispose() ;
		window2.setPreferredSize(new Dimension(300, 120));
		window2.pack();
		window2.setVisible(true);
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Bienvenue sur le programme cdumery-bmasse !");
		
		
		//TODO donner leurs valeurs à nameOfPlan et adminName

		
		
		//MenuBar
		
		//Debut	*************************************************
		
		JMenuBar menuBar = new JMenuBar() ;
		JMenu fileMenu = new JMenu("File") ;
		JMenu helpMenu = new JMenu("Help") ;
		JMenuItem newItem = new JMenuItem("New") ;
		JMenuItem saveItem = new JMenuItem("Save") ;
		JMenuItem helpItem = new JMenuItem("Help") ;
		JMenuItem aboutItem = new JMenuItem("About us") ;
		
		
		newItem.addActionListener(event -> {
			
			} ) ;
		/*	
		openItem.addActionListener(event -> {
			canvas.clean() ;	
			File repertoire = null;
			try {
		    	repertoire = new File(".").getCanonicalFile() ;		// "getCanonicalFile" permet de recuperer le repertoire courant
			} catch(IOException e) {affMessage("Erreur lors de l'accès au répertoire courant");}
			
			System.out.println(repertoire);
			JFileChooser fenetre = new JFileChooser(repertoire + "/data");
			fenetre.showOpenDialog(null) ;				// On affiche la boite de dialogue
			String fileName = fenetre.getSelectedFile().getName();
			
			try {
				nameOfPlan = 
				//open 
				
			}

			//catch (FileNotFoundException e) {affMessage("Fichier introuvable");}
			catch (Exception e) {affMessage("Erreur");}"addName n'est pas implementee pour l'instant."
			
		}) ;Name
		*/
		
		saveItem.addActionListener(event -> askName()) ;
		
		helpItem.addActionListener(event -> affMessage("Aide non implementee"));
		
		
		
		menuBar.add(fileMenu) ;
		menuBar.add(helpMenu) ;
		fileMenu.add(newItem) ;
		fileMenu.add(saveItem) ;
		helpMenu.add(helpItem) ;
		helpMenu.add(aboutItem) ;
		
		
		window.setJMenuBar(menuBar);
		
		//Fin	*************************************************
		
		
		window.getContentPane().setLayout(new BorderLayout());
		
		JPanel crayonPanel = new JPanel();
		JTabbedPane jtp = new JTabbedPane();  //Tab
		jtp.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    nameOfPlan = "plan" + (1+pane.getSelectedIndex());
            		
                	

                    //System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                }
            }
        });
		
		JButton addName = new JButton("Ajouter des noms");
		addName.addActionListener(event -> {currentMode="name";		
		});
		JButton addStairs = new JButton("Ajouter des escaliers");
		addStairs.addActionListener(event -> {currentMode="stairs";		
		});
		JButton addQR = new JButton("Ajouter des QR codes");
		addQR.addActionListener(event -> { currentMode ="QR";
		});
//		JButton lierEscalier = new JButton("Lier des escaliers");
//		addStairs.addActionListener(event -> {currentMode="lier";		
//		});
		crayonPanel.add(addName);
		crayonPanel.add(addStairs);
		crayonPanel.add(addQR);
//		crayonPanel.add(lierEscalier);
		
		
		JLabel der = new JLabel("Dernier ajout:");
		crayonPanel.add(der);
		JLabel dernierAjout = new JLabel("");
		crayonPanel.add(dernierAjout);

		window.getContentPane().add(crayonPanel, BorderLayout.SOUTH);
		
		
		
		
	
		MouseAdapter adapter = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				int y = e.getX();
				int x = e.getY();
				System.out.println(Integer.toString(x)+" , "+Integer.toString(y));
				if (currentMode=="name") {nommer(x,y,dernierAjout);}
				else if (currentMode=="stairs") {addStairs(x,y);}
				else if (currentMode=="QR") {printQR(x,y);}

				
			}
			
			
		};
		//canvas.addMouseListener(adapter);
		//canvas.addMouseMotionListener(adapter);
		//window.getContentPane().add(canvas, BorderLayout.CENTER);
		
		File rep = null;
		try {
	    	rep = new File(".").getCanonicalFile() ;		// "getCanonicalFile" permet de recuperer le repertoire courant
		} catch(IOException ex) {affMessage("Erreur lors de l'accès au répertoire courant");}
		
		System.out.println(rep);
		JFileChooser fen = new JFileChooser(rep+ "/data");
		fen.showOpenDialog(null) ;				// On affiche la boite de dialogue
		String fil = fen.getSelectedFile().getName();
		
		try {
			System.out.println("data"+fil);
			ImageIcon ii = new ImageIcon("data/"+fil);
		    JLabel lable = new JLabel(ii);
		    lable.addMouseListener(adapter);
		    lable.addMouseMotionListener(adapter);
		    JScrollPane jsp = new JScrollPane(lable);
		    //window.getContentPane().add(jsp);
		    System.out.println("je suis passé par ici");
		    jtp.add("plan1", jsp);
		}

		//catch (FileNotFoundException e) {affMessage("Fichier introuvable");}
		catch (Exception e) {affMessage("Erreur");}
		
		 ;
		
		/*
		ImageIcon ii = new ImageIcoStringn("data/planC2V2.png");
	    JLabel lable = new JLabel(ii);
	    
	    
	    lable.addMouseListener(adapter);
	    lable.addMouseMotionListener(adapter);
	    JScrollPane jsp = new JScrollPane(lable);
	    window.getContentPane().add(jsp);
	    */
		try {
		    	rep = new File(".").getCanonicalFile() ;		// "getCanonicalFile" permet de recuperer le repertoire courant
		} catch(IOException ex) {affMessage("Erreur lors de l'accès au répertoire courant");}
			
			System.out.println(rep);
			fen = new JFileChooser(rep+ "/data");
			fen.showOpenDialog(null) ;				// On affiche la boite de dialogue
			fil = fen.getSelectedFile().getName();
			try {
				System.out.println("data"+fil);
				ImageIcon ii = new ImageIcon("data/"+fil);
			    JLabel lable = new JLabel(ii);
			    lable.addMouseListener(adapter);
			    lable.addMouseMotionListener(adapter);
			    JScrollPane jsp = new JScrollPane(lable);
			    //window.getContentPane().add(jsp);
			    System.out.println("je suis passé par ici");
			    jtp.add("plan2", jsp);
			    
			}

			//catch (FileNotFoundException e) {affMessage("Fichier introuvable");}
			catch (Exception e) {affMessage("Erreur");}
			
		window.getContentPane().add(jtp);		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setPreferredSize(new Dimension(1200, 730));
		window.pack();
		//window.setExtendedState(Frame.MAXIMIZED_BOTH); 		// Mode plein ecran
		window.setVisible(true);
		
		
	
	}
}