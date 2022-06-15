package Sample;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
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

public class EJDicGUI extends JFrame {
    JTextField english, japanese;
    JList list;
    JButton addButton, removeButton, updateButton;
    JPanel pane;
    EJDic dictionary;

    public static void main( String[] args ){
		JFrame w = new EJDicGUI( "EJDicGUI" );
		w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		w.setSize( 300, 300 );
		w.setVisible( true );
    }

    public EJDicGUI( String title ){
		super( title );
		dictionary = new EJDic();
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
	
		JPanel fields = new JPanel(new GridLayout(1, 2));
		english = new JTextField();
		english.setBorder( new TitledBorder( "英語" ) );
		fields.add(english);
		japanese = new JTextField();
		japanese.setBorder( new TitledBorder( "日本語" ) );
		fields.add(japanese);
		pane.add( fields, BorderLayout.SOUTH );
	
		DefaultListModel listModel = new DefaultListModel();
		list = new JList( listModel );
		list.addListSelectionListener( new WordSelect() );
		JScrollPane sc = new JScrollPane( list );
		sc.setBorder( new TitledBorder( "項目一覧" ) );
		pane.add( sc, BorderLayout.CENTER );
		
		JPanel buttons = new JPanel();
		buttons.setLayout( new GridLayout(1, 3) );
		addButton = new JButton( new AddAction() );
		buttons.add( addButton );
		updateButton = new JButton( new UpdateAction() );
		buttons.add( updateButton );
		removeButton = new JButton( new RemoveAction() );
		buttons.add( removeButton ); 
		pane.add( buttons, BorderLayout.NORTH );
    }

    class WordSelect implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
		    if ( e.getValueIsAdjusting() == false ) {
		    	int index = list.getSelectedIndex();
			if ( index < 0 ) { 
			    return; 
			};
			DefaultListModel model = (DefaultListModel) list.getModel();
			String select = (String) model.get( index );
			english.setText( select );
			japanese.setText( dictionary.get( select ) );
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
		    dictionary.open( fileName );
		    DefaultListModel model = (DefaultListModel) list.getModel();
		    model.clear();
		    for ( String eng : dictionary.keySet() ) {
		    	model.addElement( eng );
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
		    dictionary.save( fileName );
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
		    String eng, jap;
		    eng = english.getText();
		    jap = japanese.getText();
		    if (eng.length() == 0 || jap.length() == 0) {
		    	return;
		    }
		    DefaultListModel model = (DefaultListModel)list.getModel();
		    if ( !model.contains( eng ) ) {
				model.addElement( eng );
				dictionary.put(eng, jap);
				english.setText( "" );
				japanese.setText( "" );
		    }
		}
    }

    class UpdateAction extends AbstractAction {
		UpdateAction() {
		    putValue( Action.NAME, "更新" );
		    putValue( Action.SHORT_DESCRIPTION, "更新" );
		}
		public void actionPerformed( ActionEvent e) {
		    String eng, jap;
		    eng = english.getText();
		    jap = japanese.getText();
		    if (eng.length() == 0 || jap.length() == 0) {
		    	return;
		    }
		    DefaultListModel model = (DefaultListModel)list.getModel();
		    if ( model.contains( eng ) ) {
		    	dictionary.put( eng, jap );
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
		    DefaultListModel model = (DefaultListModel)list.getModel();
		    String str = (String) model.getElementAt( index );
		    Object[] msg = { str, "を削除します。" };
		    int ans = JOptionPane.showConfirmDialog( pane, msg );
		    if ( ans == 0 ) {
				model.remove( index );
				dictionary.remove( str );
		    }
		}
    }

}