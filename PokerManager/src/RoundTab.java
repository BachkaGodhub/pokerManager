import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bachka.pokerManager.utils.player.Player;
import com.bachka.pokerManager.utils.pokerRound.PokerRound;

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

	public PokerRound getPokerRound()
	{
		return new PokerRound(model.getPlayer());
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
