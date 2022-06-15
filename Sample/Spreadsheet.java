package Sample;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Spreadsheet extends JFrame implements ActionListener, TableModelListener {
	private JTable table;
	private JScrollPane scr;
	private DefaultTableModel model;
	private JMenuBar menuBar;
	
	/** main() */
	public static void main(String[] args) {
		JFrame w = new Spreadsheet( "Spreadsheet" );
		w.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		w.setSize( 600, 300 );
		w.setVisible( true );	
	}
	
	/**  コンストラクタ */
	public Spreadsheet( String title ) {
		super( title );
		JPanel pane = (JPanel)getContentPane();
		
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
		
		// headerで定義したヘッダを元に５行のテーブルモデルを生成
		String[] header = { "Number1", "Number", "plus", "minus", "times", "divide", "surplus" };
		model = new DefaultTableModel( header, 15 );
		table = new JTable( model );	
		scr = new JScrollPane( table );
		model.addTableModelListener( this );	// modelのイベント通知登録
		// メニューのイベント通知登録
		insertItem.addActionListener( this );
		removeItem.addActionListener( this );
		moveItem.addActionListener( this );
		
		pane.add( scr, BorderLayout.CENTER );
		setJMenuBar( menuBar );	// メニューバーの追加
		pack();
	}
	
	/** テーブルに編集が加えられたときの処理 */
	public void tableChanged(TableModelEvent evt) {
		int number1, number2;
		int row = evt.getFirstRow();	// 選択されている(先頭)行を取得
		if ( evt.getColumn() == 2 ) {	// 3列目が編集されたら何もしない
			return;
		} else if( evt.getColumn() == 3 ) {	// 4列目が編集されたら何もしない
			return;
		} else if( evt.getColumn() == 4 ) {	// 5列目が編集されたら何もしない
			return;
		} else if( evt.getColumn() == 5 ) {	// 6列目が編集されたら何もしない
			return;
		} else if( evt.getColumn() == 6 ) {	// 7列目が編集されたら何もしない
			return;
		} 
		// 「削除」のときは何もしない(最終行を削除したときにエラーになるため)
		if(evt.getType() == TableModelEvent.DELETE) {
			return;
		}
		// １列目と２列目の内容を整数へ変換(不正な値は０とする)
		try {
			number1 = Integer.parseInt((String)model.getValueAt( row, 0 ));
		} catch(NumberFormatException nfex) {
			model.setValueAt( "0", row, 0 );
			number1 = 0;
		}
		try {
			number2 = Integer.parseInt((String)model.getValueAt( row, 1 ));
		} catch(NumberFormatException nfex) {
			model.setValueAt( "0", row, 1 );
			number2 = 0;
        }
	
		// １〜２列目の計算結果を設定
		model.setValueAt( number1 + number2 , row, 2 );
		model.setValueAt( number1 - number2 , row, 3 );
		model.setValueAt( number1 * number2 , row, 4 );
		if(number2 != 0) {
			model.setValueAt( number1 / number2 , row, 5 );
			model.setValueAt( number1 % number2 , row, 6 );
		}
	}
	/** メニュー選択時の処理 */
	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		Object[] data = { "", "" };	// 「挿入」時の仮データ
		int row = table.getSelectedRow();	// 選択されている行を取得
		if (command.equals( "Insert" )) {
			model.insertRow( row + 1, data );	//選択行の下に行追加
		}
		else if(command.equals( "Delete" )) {
			try {
				model.removeRow( row );	//選択行を削除
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.err.println( "削除行を選択してください" );
			}
		}
		else if(command.equals( "MoveFirst" )) {
			model.moveRow( row, row, 0 );	// 選択行を１行目へ移動
		}
	}
	
}
