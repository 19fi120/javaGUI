package Sample;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AddressBookGUI extends JFrame {
    JTextField nameField, addressField, telField, emailField; 
    DefaultListModel model;
    JList list;
    JButton addButton, removeButton, updateButton;
    JPanel pane;
    AddressBook book;

    public static void main( String[] args ){
		JFrame w = new AddressBookGUI( "AddressBookGUI" );
		w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		w.setSize( 400, 300 );
		w.setVisible( true );
    }

    public AddressBookGUI( String title ){
		super( title );
		book = new AddressBook();
		pane = (JPanel)getContentPane();
	
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar( menuBar );
		JMenu fileMenu = new JMenu( "ファイル" );
		menuBar.add( fileMenu );
		JMenuItem item;
		item = new JMenuItem( new OpenAction() );
		fileMenu.add( item );
		item = new JMenuItem( new SaveAction() );
		fileMenu.add( item );
		fileMenu.addSeparator();
		item = new JMenuItem( new ExitAction() );
		fileMenu.add( item );
	
		model = new DefaultListModel();
		list = new JList( model );
		list.addListSelectionListener( new NameSelect() );
		JScrollPane sc = new JScrollPane( list );
		sc.setBorder( new TitledBorder( "名前一覧" ) );
		pane.add( sc, BorderLayout.CENTER );
		
		JPanel fields = new JPanel();
		fields.setLayout( new BoxLayout( fields, BoxLayout.Y_AXIS) );
		nameField = new JTextField( 20 );
		nameField.setBorder( new TitledBorder( "名前" ) );
		fields.add(nameField);
		addressField = new JTextField( 20 );
		addressField.setBorder( new TitledBorder( "住所" ) );
		fields.add(addressField);
		telField = new JTextField( 20 );
		telField.setBorder( new TitledBorder( "電話" ) );
		fields.add(telField);
		emailField = new JTextField( 20 );
		emailField.setBorder( new TitledBorder( "メール" ) );
		fields.add(emailField);
	
		addButton = new JButton( new AddAction() );
		fields.add( addButton );
		updateButton = new JButton( new UpdateAction() );
		fields.add( updateButton );
		removeButton = new JButton( new RemoveAction() );
		fields.add( removeButton ); 
	
		pane.add( fields, BorderLayout.EAST );
    }

    class NameSelect implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
		    if ( e.getValueIsAdjusting() == false ) {
				int index = list.getSelectedIndex();
				if ( index < 0 ) { 
				    return; 
				};
				String select = (String) model.get( index );
				Address address = book.findName(select);
				if (address != null) {
				    nameField.setText( address.getName() );
				    addressField.setText( address.getAddress() );
				    telField.setText( address.getTel() );
				    emailField.setText( address.getEmail() );
				}
		    }
		}
    }

    class OpenAction extends AbstractAction {
		OpenAction() {
		    putValue( Action.NAME, "開く" );
		    putValue( Action.SHORT_DESCRIPTION, "開く" );
		}
		public void actionPerformed( ActionEvent e) {
		    JFileChooser fileChooser = new JFileChooser( "." ); 
		    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY ); 
		    fileChooser.setDialogTitle( "ファイル選択" ); 
		    int ret = fileChooser.showOpenDialog( pane ); 
		    if( ret != JFileChooser.APPROVE_OPTION ) {
		    	return; 
		    }
		    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
		    book.open( fileName );
		    model = (DefaultListModel) list.getModel();
		    model.clear();
		    for ( String name : book.getNames() ) {
		    	model.addElement( name );
		    }
		}
    }

    class SaveAction extends AbstractAction {
		SaveAction() {
		    putValue( Action.NAME, "保存" );
		    putValue( Action.SHORT_DESCRIPTION, "保存" );
		}
		public void actionPerformed( ActionEvent e) {
		    JFileChooser fileChooser = new JFileChooser( "." ); 
		    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY ); 
		    fileChooser.setDialogTitle( "ファイル選択" ); 
		    int ret = fileChooser.showSaveDialog( pane ); 
		    if( ret != JFileChooser.APPROVE_OPTION ) {
		    	return; 
		    }
		    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
		    book.save( fileName );
		}
    }

    class ExitAction extends AbstractAction {
		ExitAction() {
		    putValue( Action.NAME, "終了" );
		    putValue( Action.SHORT_DESCRIPTION, "終了" );
		}
		public void actionPerformed( ActionEvent e) {
		    Object[] msg = { "終了しますか？" };
		    int ans = JOptionPane.showConfirmDialog( pane, msg );
		    if ( ans == 0 ) {
		    	System.exit(0);
		    }
		}
    }

    class AddAction extends AbstractAction {
		AddAction() {
		    putValue( Action.NAME, "追加" );
		    putValue( Action.SHORT_DESCRIPTION, "追加" );
		}
		public void actionPerformed( ActionEvent e) {
		    String name, address, tel, email;
		    name = nameField.getText();
		    address = addressField.getText();
		    tel = telField.getText();
		    email = emailField.getText();
		    if (name.length() == 0 || address.length() == 0 || tel.length() == 0 || email.length() == 0) {
		    	return;
		    }
		    model = (DefaultListModel)list.getModel();
		    if ( !model.contains( name ) ) {
				model.addElement( name );
				book.add( new Address(name, address, tel, email) );
				nameField.setText( "" );
				addressField.setText( "" );
				telField.setText( "" );
				emailField.setText( "" );
		    }
		}
    }

    class UpdateAction extends AbstractAction {
		UpdateAction() {
		    putValue( Action.NAME, "更新" );
		    putValue( Action.SHORT_DESCRIPTION, "更新" );
		}
		public void actionPerformed( ActionEvent e) {
		    String name, address, tel, email;
		    name = nameField.getText();
		    address = addressField.getText();
		    tel = telField.getText();
		    email = emailField.getText();
		    if (name.length() == 0 || address.length() == 0 || tel.length() == 0 || email.length() == 0) {
		    	return;
		    }
		    model = (DefaultListModel)list.getModel();
		    if ( model.contains( name ) ) {
				Address add = book.findName(name);
				add.setAddress(address);
				add.setTel(tel);
				add.setEmail(email);
		    }
		}
    }

    class RemoveAction extends AbstractAction {
		RemoveAction() {
		    putValue( Action.NAME, "削除" );
		    putValue( Action.SHORT_DESCRIPTION, "削除" );
		}
		public void actionPerformed( ActionEvent e) {
		    int index = list.getSelectedIndex();
		    if (index < 0) {
		    	return;
		    } 
		    model = (DefaultListModel)list.getModel();
		    String str = (String) model.getElementAt( index );
		    Object[] msg = { str, "を削除します。" };
		    int ans = JOptionPane.showConfirmDialog( pane, msg );
		    if ( ans == 0 ) {
				model.remove( index );
				book.remove( book.findName(str) );
		    }
		}
    }

}