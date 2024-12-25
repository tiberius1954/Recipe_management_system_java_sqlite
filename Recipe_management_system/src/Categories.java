import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.LineBorder;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Categories extends JFrame{
	grlib gr = new grlib();
	Hhelper hh = new Hhelper();
	ResultSet rs;
	Connection con = null;
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	
	Categories(){
		initcomponens();
	 	table_update("");		
	}
	
	private void initcomponens() {
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setUndecorated(true);
		setLayout(null);
		setResizable(true);
		setBounds(10, 10, 560, 350);
		getContentPane().setBackground(new Color(128, 255, 128));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		lbheader = hh.flabel("Categories");
		lbheader.setBounds(80, 10, 150, 35);
		add(lbheader);

		exitPanel = gr.makeexitpanel(this);
		exitPanel.setBounds(450, 10, 100, 35);
		exitPanel.setOpaque(true);
		exitPanel.setBackground(new Color(128, 255, 128));
		add(exitPanel);
		bPanel = new JPanel();
		bPanel.setLayout(null);
		bPanel.setBounds(10, 40, 570, 280);
		bPanel.setBackground(new Color(128, 255, 128));
		ePanel = new JPanel(null);
		ePanel.setBounds(10, 20, 260, 260);
		ePanel.setBackground(new Color(128, 255, 128));
	//	ePanel.setBorder(hh.borderf);
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		tPanel = new JPanel(null);
		tPanel.setBounds(274, 20, 260, 260);
		tPanel.setBackground(new Color(128, 255, 128));
	//	tPanel.setBorder(hh.borderf);
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(ePanel);
		bPanel.add(tPanel);
		add(bPanel);
		
		lbname = hh.clabel("Name");
		lbname.setBounds(0, 40, 60, 25);
		ePanel.add(lbname);

		txcname = hh.cTextField(25);
		txcname.setBounds(70, 40, 180, 25);
		ePanel.add(txcname);
		txcname.addKeyListener( hh.MUpper());
		
    	btnsave = gr.sbcs("Save");
//		btnsave = hh.cbutton("Save");
		btnsave.setBounds(110, 100, 100, 30);
		btnsave.setForeground(Color.black);
		btnsave.setBackground(Color.red);
		ePanel.add(btnsave);
		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = gr.sbcs("Cancel");
	//	btncancel = hh.cbutton("Cancel");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(Color.green);
		btncancel.setBounds(110, 140, 100, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});
		btndelete = gr.sbcs("Delete");
	//	btndelete = hh.cbutton("Delete");
		btndelete.setBounds(110, 180, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);

		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(ctable, "delete from categories  where cid =");
				clearFields();
			}
		});
		
		ctable = hh.ztable();
		ctable.setTableHeader(new JTableHeader(ctable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ctable);
		ctable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ctable.scrollRectToVisible(ctable.getCellRect(ctable.getRowCount() - 1, 0, true));
			}
		});
		ctable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = ctable.getSelectedRow();
				if (row >= 0) {
					txcname.setText(ctable.getValueAt(row, 1).toString());
					rowid = ctable.getValueAt(row, 0).toString();
					myrow = row;
				}
			}
		});
		jScrollPane1 = new JScrollPane(ctable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ctable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null }, },new String[] { "cid", "Name" }));		
		hh.setJTableColumnsWidth(ctable, 230, 0, 100);
	
		jScrollPane1.setViewportView(ctable);
		jScrollPane1.setBounds(10, 10, 240, 230);
		tPanel.add(jScrollPane1);
		setVisible(true);
	}
	
	private void savebuttrun() {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		String sql = "";
		String jel = "";
		String cname = txcname.getText();
		if (hh.zempty(cname)) {
			JOptionPane.showMessageDialog(null, "Please fill aame");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  categories set cname= '" + cname + "' where cid = " + rowid;
		} else {
			sql = "insert into categories (cname) " + "values ('" + cname + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag >0) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(cname);
				} else {			
					int myid = dd.table_maxid("SELECT MAX(cid) AS max_id from categories");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, cname});						
						int irow = d1.getRowCount();
						hh.gotolastrow(ctable);
						ctable.setRowSelectionInterval(irow - 1, irow - 1);						
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}	

		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
	}
	
	private void table_rowrefresh(String cname) {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		d1.setValueAt(cname, myrow, 1);
	}

	private void clearFields() {
		txcname.setText("");
		txcname.requestFocus();
		rowid = "";
		myrow = 0;
	}
	
	private void table_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) ctable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  cid, cname from categories order by upper(cname)";
		} else {
			Sql = "select  cid, cname  where " + what;
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String cid = rs.getString("cid");
				String cname = rs.getString("cname");
				m1.addRow(new Object[] { cid, cname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "cid", "Name" };
		((DefaultTableModel) ctable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ctable, 230, 0, 100);
		ctable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ctable.scrollRectToVisible(ctable.getCellRect(ctable.getRowCount() - 1, 0, true));
			}
		});
		if (ctable.getRowCount() > 0) {
			int row = ctable.getRowCount() - 1;
			ctable.setRowSelectionInterval(row, row);
		}
	}
	
	public static void main(String[] argv) {	
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {	            
	                	Categories ts = new Categories();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}
JLabel lbheader, lbname;
JTextField txcname;
JPanel exitPanel, bPanel, ePanel, tPanel;
JButton btnsave, btncancel, btndelete;
JTable ctable;
JScrollPane jScrollPane1;
}
