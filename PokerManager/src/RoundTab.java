import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bachka.pokerManager.utils.player.IPlayer;
import com.bachka.pokerManager.utils.player.Player;
import com.bachka.pokerManager.utils.pokerRound.PokerRound;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class RoundTab extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	RoundTableModel model;

	/**
	 * Create the frame.
	 */
	public RoundTab() {
		model = new RoundTableModel();

		table = new JTable(model);
		table.setPreferredSize(new Dimension(300, 300));

		JButton addRow = new JButton("Add Empty Row");
		addRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Player("", 0, 0));
				model.fireTableDataChanged();
			}
		});

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(300, 100));

		setLayout(new BorderLayout());
		this.add(scroll);
		this.add(addRow, BorderLayout.SOUTH);
	}

	public HashMap<String, Double> getPlayerListProfit() {
		HashMap<String, Double> _playersProfit = new HashMap<String, Double>();
		PokerRound pkR2 = new PokerRound(model.getPlayer());
		pkR2.calcProfit();
		for (IPlayer p : pkR2.getPlayers().values()) {
			_playersProfit.put(p.getName(), p.getProfit());
			System.out.println("PP " + p.getName() + ": " + p.getProfit());
		}

		return _playersProfit;
	}

	public boolean hasData() {
		return model.getRowCount() > 0;
	}

	public boolean isEditing() {
		return table.isEditing();
	}

	public void stopCellEditing() {
		table.getCellEditor().stopCellEditing();
	}

}
