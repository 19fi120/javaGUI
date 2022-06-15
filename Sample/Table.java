package Sample;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

class Table {
	public static void main(String[] args) {
		Table t = new Table();
	}

	Table() {
		Vector columnNames = new Vector();
		for (int i = 0; i < 10; i++) {
			columnNames.add(String.valueOf(i));
		}
		Vector data = new Vector();
		Vector tmp;
		for (int i = 0; i < 10; i++) {
			tmp = new Vector();
			for (int j = 0; j < 10; j++) {
				tmp.add(String.valueOf(j));
			}
			data.add(tmp);
		}
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTable t = new JTable(new TableView(columnNames, data));
		if (t.getColumnCount() > 5) {
			t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnModel tcm = t.getColumnModel();
			for (int i = 0; i < t.getColumnCount(); i++) {
				tcm.getColumn(i).setPreferredWidth(100);
			}
		}
		JScrollPane js = new JScrollPane(t);
		jf.getContentPane().add(js);
		jf.setSize(500,500);
		jf.setVisible(true);
	}
}

class TableView extends AbstractTableModel {
	Vector columnNames;
	Vector data;

	TableView() {
		columnNames = new Vector();
		data = new Vector();
	}

	TableView(Vector columnNames, Vector data) {
		this.columnNames = columnNames;
		this.data = data;
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return (String) columnNames.elementAt(col);
	}

	public Object getValueAt(int row, int col) {
		return ((Vector) data.elementAt(row)).elementAt(col);
	}

	public Class getColumnClass(int col) {
		return columnNames.elementAt(col).getClass();
	}

	public boolean isCellEditable(int col) {
		return true;
	}

	public void setValueAt(Object value, int row, int col) {
		((Vector) data.elementAt(row)).removeElementAt(col);
		((Vector) data.elementAt(row)).add(col, value);
	}
}
