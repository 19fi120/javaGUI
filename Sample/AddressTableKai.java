package Sample;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AddressTableKai extends JFrame {
    
    BufferedReader reader;
    JButton addRowButton, readButton, writeButton; 
    JPanel pane;
    String filename = "address.txt";
    DefaultTableModel model;
    JTable table;
    
    protected String[] columnNames = 
    { "名前", "住所", "電話", "メール" };

    public static void main(String[] args) {
	AddressTableKai w = new AddressTableKai( "住所録" );
	w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	w.setSize( 600, 300 );
	w.setVisible( true );
    }
    
    public AddressTableKai( String title ) {
	super( title );
	pane = (JPanel) getContentPane();

	model =  new DefaultTableModel( columnNames, 0 );
	
	table = new JTable();
	JScrollPane scroll = new JScrollPane( table );
	table.setModel( model );

	pane.add( scroll, BorderLayout.CENTER );

	JPanel buttons = new JPanel();
	buttons.setLayout( new GridLayout(1, 3));
	addRowButton = new JButton( new AddRowAction() );
	buttons.add( addRowButton );
	readButton = new JButton( new ReadAction() );
	buttons.add( readButton );
	writeButton = new JButton( new WriteAction() );
	buttons.add( writeButton );
	pane.add( buttons, BorderLayout.NORTH );

    }

    void readData( DefaultTableModel model, String dataFileName ){
    } 

    class AddRowAction extends AbstractAction {
	AddRowAction() {
	    putValue( Action.NAME, "行の追加" );
	    putValue( Action.SHORT_DESCRIPTION, "行の追加" );
	}
	public void actionPerformed( ActionEvent e) {
	    int colNum = model.getColumnCount();
	    Vector<String> newData = new Vector<String>();
	    for (int i=0; i<colNum; i++) {
		newData.addElement("");
	    }
	    model.addRow(newData);
	}
    }
    
    class ReadAction extends AbstractAction {
	ReadAction() {
	    putValue( Action.NAME, "読み込み" );
	    putValue( Action.SHORT_DESCRIPTION, "読み込み" );
	}
	public void actionPerformed( ActionEvent e) {
	    try {
		int num = model.getRowCount();
		for (int i=0; i<num; i++) {
		    model.removeRow(0);
		}
		reader = new BufferedReader( new FileReader( filename ) );
		String line;
		while ( (line = reader.readLine() ) != null ) {
		    String[] row = line.split(",");
		    model.addRow( row );
		}
		reader.close();
	    }
	    catch ( IOException ie ) {
		ie.printStackTrace();
	    }

	}
    }
    
    class WriteAction extends AbstractAction {
	WriteAction() {
	    putValue( Action.NAME, "書き込み" );
	    putValue( Action.SHORT_DESCRIPTION, "書き込み" );
	}
	public void actionPerformed( ActionEvent e) {
	    try {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		int rowNum = model.getRowCount();
		int colNum = model.getColumnCount();
		for (int i=0; i<rowNum; i++) {
		    for (int j=0; j<colNum; j++) {
			writer.print(model.getValueAt(i, j));
			if (j < colNum-1) {
			    writer.print(",");
			}
		    }
		    writer.println();
		}
		writer.close();
	    }
	    catch ( IOException ie ) {
		ie.printStackTrace();
	    }
	}
    }
    

}