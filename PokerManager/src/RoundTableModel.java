import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.bachka.pokerManager.utils.player.IPlayer;
import com.bachka.pokerManager.utils.player.Player;
import com.bachka.pokerManager.utils.pokerRound.PokerRound;

public class RoundTableModel extends AbstractTableModel {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private List<IPlayer> playerList;

	private final String[] columnNames = new String[] { "Name", "Total Buy-In", "Chips" };
	private final Class[] columnClass = new Class[] { String.class, Double.class, Double.class };

	public RoundTableModel(List<IPlayer> employeeList) {
		this.playerList = employeeList;
	}

	public RoundTableModel() {
		this.playerList = new ArrayList<IPlayer>();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClass[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return playerList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		IPlayer row = playerList.get(rowIndex);
		if (0 == columnIndex) {
			return row.getName();
		} else if (1 == columnIndex) {
			return row.getEuroTotalBuyIn();
		} else if (2 == columnIndex) {
			return row.getChips();
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		IPlayer row = playerList.get(rowIndex);

		if (0 == columnIndex) {
			System.out.println("edited name");
			row.setName((String) aValue);
			System.out.println("new name: " + aValue);
		} else if (1 == columnIndex) {
			row.setEuroTotalBuyIn((double) aValue);
		} else if (2 == columnIndex) {
			row.setChips((double) aValue);
		}
	}

	public List<IPlayer> getPlayer() {
		return playerList;
	}

	public void addRow(IPlayer player) {
		playerList.add(player);
		System.out.println("lisz size: " + playerList.size());
	}
}
