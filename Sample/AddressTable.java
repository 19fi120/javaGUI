package Sample;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AddressTable extends JFrame {

  BufferedReader reader;
  protected DefaultTableModel model;

  protected String[] columnNames = 
    { "名前", "住所", "電話", "メール" };
  public AddressTable( String title ) {
      super( title );
      JPanel pane = (JPanel) getContentPane();

      DefaultTableModel model =  new DefaultTableModel( columnNames, 0 );
      readData( model, "address.txt" );

      JTable table = new JTable();
      JScrollPane scroll = new JScrollPane( table );
      table.setModel( model );

      pane.add( scroll, BorderLayout.CENTER );

  }

  void readData( DefaultTableModel model, String dataFileName ){
    try {
      reader = new BufferedReader( new FileReader( dataFileName ) );
      String line;
      while ( (line = reader.readLine() ) != null ) {
	String[] row = line.split(",");
        model.addRow( row );
      }
    }
    catch ( IOException e ) {
       System.out.println( e );
    }
  } 

  public static void main(String[] args) {
    AddressTable w = new AddressTable( "住所録" );
    w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    w.setSize( 600, 150 );
    w.setVisible( true );
  }

}