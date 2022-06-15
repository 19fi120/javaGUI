package Sample;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CalcExcel extends JFrame {
	
	private JTable table;
	private JScrollPane scr;
	private DefaultTableModel model;
	private JMenuBar menuBar;
	private JTextField number1, number2, answer;
	List<Integer> list = new ArrayList<>();

	/** main() */
	public static void main(String[] args) {
		JFrame w = new CalcExcel( "CalcExcel" );
		w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		w.setSize( 500, 300 );
		w.setVisible( true );
	}
	
	/**  コンストラクタ */
	CalcExcel( String title ) {
		super( title );
		JPanel pane = (JPanel)getContentPane();
		pane.setLayout( new BorderLayout() );
		
		// メニュー設定
		menuBar = new JMenuBar();
		JMenu editMenu = new JMenu( "Edit" );
		JMenuItem insertItem = new JMenuItem( "Insert" );
		JMenuItem removeItem = new JMenuItem( "Delete" );
		JMenuItem moveItem = new JMenuItem( "MoveFirst" );
		editMenu.add( insertItem );
		editMenu.add( removeItem );
		editMenu.add( moveItem );
		menuBar.add( editMenu );
		setJMenuBar( menuBar );	// メニューバーの追加
		
		model = new DefaultTableModel( 40, 30 );
		table = new JTable( model );
		JTableHeader columnHeader = table.getTableHeader(); // ヘッダ部を得る
		//Component rowHeader = table.getSelectedRow();
		pane.add( columnHeader, BorderLayout.NORTH ); // ヘッダを追加
		//getContentPane().add( rowHeader, BorderLayout.WEST ); // ヘッダを追加
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		scr = new JScrollPane( table ); // スクロールペインにセットする	
		pane.add( scr, BorderLayout.CENTER ); // テーブル本体を追加
		
		JPanel westPanel = new JPanel( );
		westPanel.setLayout( new GridLayout( 2, 1 ) ); // レイアウト方法を指定
		number1 = new JTextField( 5 );
	    number1.setBorder( new TitledBorder( "整数1" ) );
	    number1.setDocument( new NumericDocument() );
	    westPanel.add( number1 );

	    number2 = new JTextField( 5 );
	    number2.setBorder( new TitledBorder( "整数2" ) );
	    number2.setDocument( new NumericDocument() );
	    westPanel.add( number2 );
	    pane.add( westPanel, BorderLayout.WEST  );
	    
	    
		answer = new JTextField( 10 );
	    answer.setBorder( new TitledBorder( "答" ) );
	    answer.setDocument( new NumericDocument() );
	    pane.add( answer, BorderLayout.SOUTH );
	    
	    JPanel panel = new JPanel();
	    panel.setLayout( new GridLayout(5, 1) );   
	    JButton plus = new JButton( new PlusAction() );
	    panel.add( plus );
	    JButton minus = new JButton( new MinusAction() );
	    panel.add( minus );
	    JButton times = new JButton( new TimesAction() );
	    panel.add( times );
	    JButton divide = new JButton( new DivideAction() );
	    panel.add( divide );
	    JButton surplus = new JButton( new SurplusAction() );
	    panel.add( surplus );
	    pane.add( panel, BorderLayout.EAST );
	    
	    pack();
	}
	
	class PlusAction extends AbstractAction {
		PlusAction() {
			putValue( Action.NAME, "+" );
			putValue( Action.SHORT_DESCRIPTION, "足す");
		}
		public void actionPerformed( ActionEvent e ) {
			int ans = Integer.parseInt( number1.getText() ) + Integer.parseInt( number2.getText() );
			answer.setText( String.valueOf( ans ) );
		}
	}
	    
	class MinusAction extends AbstractAction {
		MinusAction() {
		  putValue( Action.NAME, "-" );
		  putValue( Action.SHORT_DESCRIPTION, "引く");
		}
		public void actionPerformed( ActionEvent e ) {
		  int ans = Integer.parseInt( number1.getText() ) - Integer.parseInt( number2.getText() );
		  answer.setText( String.valueOf( ans ) );
		}
	  }
	  
	  class TimesAction extends AbstractAction {
	    TimesAction() {
		  putValue( Action.NAME, "*" );
		  putValue( Action.SHORT_DESCRIPTION, "掛ける");
		}
		public void actionPerformed( ActionEvent e ) {
		  int ans = Integer.parseInt( number1.getText() ) * Integer.parseInt( number2.getText() );
		  answer.setText( String.valueOf( ans ) );
		}
	  }
	  
	  class DivideAction extends AbstractAction {
		DivideAction() {
		  putValue( Action.NAME, "/" );
		  putValue( Action.SHORT_DESCRIPTION, "割る");
		}
		public void actionPerformed( ActionEvent e ) {
		  int ans = Integer.parseInt( number1.getText() ) / Integer.parseInt( number2.getText() );
		  answer.setText( String.valueOf( ans ) );
		}
	  }
	    
	  class SurplusAction extends AbstractAction {
	    SurplusAction() {
	      putValue( Action.NAME, "%" );
	      putValue( Action.SHORT_DESCRIPTION, "割った余り");
	    }
	    public void actionPerformed( ActionEvent e ) {
	      int ans = Integer.parseInt( number1.getText() ) % Integer.parseInt( number2.getText() );
	      answer.setText( String.valueOf( ans ) );
	    }
	  }
	  
	class NumericDocument extends PlainDocument { // PlainDocument を拡張して定義
	    String validValues = "0123456789.+-";
	    @Override
	    public void insertString( int offset, String str, AttributeSet a ) {
	    	if( validValues.indexOf( str ) == -1 ){ // 有効文字でないか？
	    		return;
	    	}
	    	try{
	    		super.insertString( offset, str, a ); // スーパクラスのメソッドを呼び出し
	    	} catch( BadLocationException e ) {
	    		System.out.println( e );
	    	}
	    }
	}
}
