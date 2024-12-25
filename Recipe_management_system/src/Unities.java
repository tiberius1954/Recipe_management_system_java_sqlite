import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import net.proteanit.sql.DbUtils;

public class Unities extends JFrame {
	grlib gr = new grlib();
	Hhelper hh = new Hhelper();
	ResultSet rs;
	Connection con = null;
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;

	Unities() {
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
		lbheader = hh.flabel("Unities");
		lbheader.setBounds(80, 10, 150, 30);
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
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		tPanel = new JPanel(null);
		tPanel.setBounds(274, 20, 260, 260);
		tPanel.setBackground(new Color(128, 255, 128));	
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(ePanel);
		bPanel.add(tPanel);
		add(bPanel);

		lbunity = hh.clabel("Unity name");
		lbunity.setBounds(0, 40, 100, 25);
		ePanel.add(lbunity);

		txunity = hh.cTextField(25);
		txunity.setBounds(110, 40, 140, 25);
		ePanel.add(txunity);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(130, 100, 100, 30);
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
		btncancel.setForeground(Color.black);
		btncancel.setBackground(Color.green);
		btncancel.setBounds(130, 140, 100, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});
    	btndelete = gr.sbcs("Delete");	
		btndelete.setBounds(130, 180, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);

		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(utable, "delete from unities  where uid =");
				clearFields();
			}
		});

		utable = hh.ztable();
		utable.setTableHeader(new JTableHeader(utable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(utable);
		utable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				utable.scrollRectToVisible(utable.getCellRect(utable.getRowCount() - 1, 0, true));
			}
		});
		utable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = utable.getSelectedRow();
				if (row >= 0) {
					txunity.setText(utable.getValueAt(row, 1).toString());
					rowid = utable.getValueAt(row, 0).toString();
					myrow = row;
				}
			}
		});
		jScrollPane1 = new JScrollPane(utable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		utable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null }, },new String[] { "uid", "Name" }));		
		hh.setJTableColumnsWidth(utable, 240, 0, 100);
	
		jScrollPane1.setViewportView(utable);
		jScrollPane1.setBounds(10, 10, 240, 230);
		tPanel.add(jScrollPane1);
		setVisible(true);
	}

	private void savebuttrun() {
		DefaultTableModel d1 = (DefaultTableModel) utable.getModel();
		String sql = "";
		String jel = "";
		String uname = txunity.getText();
		if (hh.zempty(uname)) {
			JOptionPane.showMessageDialog(null, "Please fill name");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  unities set uname= '" + uname + "' where uid = " + rowid;
		} else {
			sql = "insert into unities (uname) values ('" + uname + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag >0) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(uname);
				} else {				
						int myid = dd.table_maxid("SELECT MAX(uid) AS max_id from unities");
						d1.insertRow(d1.getRowCount(),
								new Object[] { myid, uname});
						int irow = d1.getRowCount();
						hh.gotolastrow(utable);
						utable.setRowSelectionInterval(irow - 1, irow - 1);						
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

	private void table_rowrefresh(String unity) {
		DefaultTableModel d1 = (DefaultTableModel) utable.getModel();
		d1.setValueAt(unity, myrow, 1);
	}

	private void clearFields() {
		txunity.setText("");
		txunity.requestFocus();
		rowid = "";
		myrow = 0;
	}

	private void table_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) utable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  uid, uname from unities order by upper(uname)";
		} else {
			Sql = "select  uid, uname  from unities where " + what;
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String uid = rs.getString("uid");
				String uname = rs.getString("uname");
				m1.addRow(new Object[] { uid, uname });
			}
	} catch (SQLException e) {
		System.out.println("Caught an ArrayIndexOutOfBoundsException: " + e.getMessage());
			
	//		e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "uid", "Name" };
		((DefaultTableModel) utable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(utable, 240, 0, 100);
		utable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				utable.scrollRectToVisible(utable.getCellRect(utable.getRowCount() - 1, 0, true));
			}
		});
		if (utable.getRowCount() > 0) {
			int row = utable.getRowCount() - 1;
			utable.setRowSelectionInterval(row, row);
		}
	}

	public static void main(String[] argv) {
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {	            
	                	Unities ts = new Unities();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}

	JPanel exitPanel;
	JLabel lbheader, lbunity;
	JTextField txunity;
	JPanel bPanel, tPanel, ePanel;
	JTable utable;
	JButton btndelete, btnsave, btncancel;
	JScrollPane jScrollPane1;
}
