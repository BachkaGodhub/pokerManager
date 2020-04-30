import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.bachka.pokerManager.utils.pokerday.PokerNight;

public class Main extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnCalcProfit;
	private RoundTab roundTab1;
	private RoundTab roundTab2;
	private RoundTab roundTab3;
	private RoundTab roundTab4;
	private RoundTab roundTab5;
	private JPanel infoPanel;
	private JTextArea schuldenTextArea;
	private JPanel buttonPanel;
	private JPanel subInfoPanel;
	private JPanel eachRoundResult;
	private JTextArea ergebnisTextArea1, ergebnisTextArea2, ergebnisTextArea3, ergebnisTextArea4, ergebnisTextArea5, ergebnisTextAreaGesamt;
	private JButton btnNewButton;
	private LinkedHashMap<RoundTab, JTextArea> roundTabTextAreas = new LinkedHashMap<RoundTab, JTextArea>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
        this.setTitle("Poker Manager");
        this.setPreferredSize(new Dimension(500, 800));
 
        JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );
        tabpane.setMinimumSize(new Dimension(800,200));
        setupErgebnisTextArea();

    	AtomicInteger counter = new AtomicInteger(1);
        roundTabTextAreas.forEach((k,v) ->{
        	tabpane.addTab("round " + counter.getAndIncrement(), null, new JScrollPane(k), null);
        });

        getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
        getContentPane().add(tabpane);
        
        infoPanel = new JPanel();
        infoPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
        eachRoundResult = new JPanel();
        subInfoPanel = new JPanel();
        getContentPane().add(infoPanel);
        
        buttonPanel = new JPanel();
        
        
        schuldenTextArea = new JTextArea();
        schuldenTextArea.setBorder(new TitledBorder("Loser -> Winner"));
        
        GridLayout infoGridLayout = new GridLayout(2, 1, 2, 2);
        GridLayout subInfoGridLayout = new GridLayout(1, 2, 0, 0);
        GridLayout eachRoundResultGridLayout = new GridLayout(1, 5, 2, 2);

        infoPanel.setLayout(infoGridLayout);
        subInfoPanel.setLayout(subInfoGridLayout);
        eachRoundResult.setLayout(eachRoundResultGridLayout);
        
        infoPanel.add(eachRoundResult);
        infoPanel.add(subInfoPanel);
        
        subInfoPanel.add(schuldenTextArea);
        subInfoPanel.add(buttonPanel);
        
        btnCalcProfit = new JButton("Calc profit");
		btnCalcProfit.addActionListener(this);
		buttonPanel.add(btnCalcProfit);
		
		btnNewButton = new JButton("Save as Img");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BufferedImage img = new BufferedImage(infoPanel.getWidth(), infoPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
				infoPanel.paint(img.getGraphics());
				File outputfile = new File("saved.jpg");
				try {
					ImageIO.write(img, "jpg", outputfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally
				{
					Desktop dt = Desktop.getDesktop();
					
					try {
						dt.open(outputfile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		buttonPanel.add(btnNewButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.pack();
        this.setVisible(true);
	}

	private void setupErgebnisTextArea() {
    	roundTab1 = new RoundTab();
    	roundTab2 = new RoundTab();
    	roundTab3 = new RoundTab();
    	roundTab4 = new RoundTab();
    	roundTab5 = new RoundTab();
    	
        ergebnisTextArea1 = new JTextArea();
        ergebnisTextArea2 = new JTextArea();
        ergebnisTextArea3 = new JTextArea();
        ergebnisTextArea4 = new JTextArea();
        ergebnisTextArea5 = new JTextArea();
        
        roundTabTextAreas.put(roundTab1, ergebnisTextArea1);
        roundTabTextAreas.put(roundTab2, ergebnisTextArea2);
        roundTabTextAreas.put(roundTab3, ergebnisTextArea3);
        roundTabTextAreas.put(roundTab4, ergebnisTextArea4);
        roundTabTextAreas.put(roundTab5, ergebnisTextArea5);
        
        ergebnisTextAreaGesamt = new JTextArea();

    	AtomicInteger counter = new AtomicInteger(1);
        roundTabTextAreas.forEach((k,v) ->{
        	v.setBorder(new TitledBorder("Round " + counter.getAndIncrement()));
            v.setRows(10);
            v.setColumns(10);
        });
        
        ergebnisTextAreaGesamt.setBorder(new TitledBorder("Gesamt"));
        ergebnisTextAreaGesamt.setRows(10);
        ergebnisTextAreaGesamt.setColumns(10);
        
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.btnCalcProfit){
        	stopTableEditingMode();
        	clearOutputs();
        	PokerNight pokerNight = new PokerNight();
        	pokerNight.addRound(roundTab1.getPokerRound(), roundTab2.getPokerRound(), roundTab3.getPokerRound(), roundTab4.getPokerRound(), roundTab5.getPokerRound());

        	HashMap<String, Double> gesamtProfit = pokerNight.getTotal();
        	List<String> schuldenText = pokerNight.schuldenRechnen();
        	String txt = schuldenText.stream().map(Object::toString).collect(Collectors.joining("\n"));
        	schuldenTextArea.setText(txt);
        	
        	gesamtProfit.forEach((key,value)->{
        		ergebnisTextAreaGesamt.setText(ergebnisTextAreaGesamt.getText() + key + " : " + value + "\n");
        	});
        	setErgebnisseToJPanel();
        }
	}
	
	private void clearOutputs() {
		schuldenTextArea.setText("");
        roundTabTextAreas.forEach((k,v) ->{
        	v.setText("");
        });
		ergebnisTextAreaGesamt.setText("");
	}

	private void setErgebnisseToJPanel() {
        roundTabTextAreas.forEach((roundTab, textArea) ->{
        	if(roundTab.hasData()) {
        		roundTab.getPokerRound().getPlayerListProfit().forEach((key, value) -> {
        			textArea.setText(textArea.getText() + key + " : " + value + "\n");
        		});
        		eachRoundResult.add(textArea);
        	}
        });

		eachRoundResult.add(ergebnisTextAreaGesamt);
	}

	private void stopTableEditingMode() {
        roundTabTextAreas.forEach((roundTab, textArea) ->{
        	if(roundTab.isEditing())
        		roundTab.stopCellEditing();
        });
	}
}
