import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Hhelper;
import Classes.Unity;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;


public class Ingredients extends JFrame{
	ResultSet res;
	Connection con = null;
	grlib gr = new grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	private String rowid = "";
	private int myrow = 0;
	Ingredients(){
		initcomponents();
		dd.unitiescombofill(cmbunities);
		table_update("");
	}
void initcomponents() {
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
	setSize(920, 470);
	setLayout(null);
	setLocationRelativeTo(null);
	getContentPane().setBackground(new Color(128, 255, 128));
	lbheader = hh.flabel("Ingredients");
	lbheader.setBounds(80, 10, 150, 35);
	add(lbheader);

	exitPanel = gr.makeexitpanel(this);
	exitPanel.setBounds(820, 10, 100, 35);
	exitPanel.setOpaque(true);
	exitPanel.setBackground(new Color(128, 255, 128));
	add(exitPanel);
	
	bPanel = new JPanel(null);
	bPanel.setBounds(10,50,905, 400);
	bPanel.setBackground(new Color(128, 255, 128));
	//bPanel.setBorder(hh.line);
	add(bPanel);
	
	ePanel = new JPanel(null);
	ePanel.setBounds(10,10,440, 385);
	ePanel.setBackground(new Color(128, 255, 128));
	ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
	bPanel.add(ePanel);	
	tPanel = new JPanel(null);
	tPanel.setBounds(460,10,440, 385);
	tPanel.setBackground(new Color(128, 255, 128));
	tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
	bPanel.add(tPanel);
	
	lbname = hh.clabel("Name");
	lbname.setBounds(10,30,100, 25);
	ePanel.add(lbname);	

	txname = gr.gTextField(30);
	txname.setBounds(120,30,200, 30);
	ePanel.add(txname);
	txname.addKeyListener( hh.MUpper());
	
	lbunity = hh.clabel("Unity");
	lbunity.setBounds(10,80,100, 25);
	ePanel.add(lbunity);	

	cmbunities = gr.grcombo();
	cmbunities.setFocusable(true);
	cmbunities.setName("unities");
	cmbunities.setBounds(120, 80, 200, 30);
	ePanel.add(cmbunities);	
 
	btnsave = gr.sbcs("Save");
	btnsave.setBounds(150, 130, 130, 30);
	btnsave.setBackground(Color.red);
	ePanel.add(btnsave);

	btnsave.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			savebuttrun();
		}
	});
	
   btncancel = gr.sbcs("Cancel");
	btncancel.setBackground(Color.green);
	btncancel.setBounds(150, 170, 130, 30);
	ePanel.add(btncancel);
	btncancel.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			clearFields();
		}
	});

   btndelete = gr.sbcs("Delete");
	btndelete.setBounds(150, 210, 130, 30);
	btndelete.setBackground(Color.yellow);
	ePanel.add(btndelete);
	btndelete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			data_delete();
		}
	});	

	intable = hh.ztable();
	intable.setTableHeader(new JTableHeader(intable.getColumnModel()) {
		@Override
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			d.height = 25;
			return d;
		}
	});

	hh.madeheader(intable);
	intable.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			intable.scrollRectToVisible(intable.getCellRect(intable.getRowCount() - 1, 0, true));
		}
	});
	intable.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
			
			int row = intable.getSelectedRow();
			if (row >= 0) {
				txname.setText(intable.getValueAt(row, 1).toString());
				rowid = intable.getValueAt(row, 0).toString();
				int number = 0;
				String cnum = intable.getValueAt(row, 2).toString();
				if (!hh.zempty(cnum)) {
					number = Integer.parseInt(cnum);
				}
				hh.setSelectedValue(cmbunities, number);
				cmbunities.updateUI();
				myrow = row;
			}
		}
	});

	jScrollPane1 = new JScrollPane(intable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	intable.setModel(new javax.swing.table.DefaultTableModel(
			new Object[][] { { null, null, null, null }, },
			new String[] { "iid", "Name", "uid", "Unity" }));	
	hh.setJTableColumnsWidth(intable, 400, 0, 80,0,20	);
	jScrollPane1.setViewportView(intable);
	jScrollPane1.setBounds(10, 10, 420, 300);
	jScrollPane1.setBorder(hh.borderf);
	tPanel.add(jScrollPane1);

	lbsearch = hh.clabel("Search:");
	lbsearch.setBounds(10, 330, 70, 25);
	tPanel.add(lbsearch);

	txsearch = hh.cTextField(25);
	txsearch.setBounds(90, 330, 200, 25);
	tPanel.add(txsearch);

	btnclear = new JButton();
	btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
	btnclear.setMargin(new Insets(0, 0, 0, 0));
	btnclear.setBounds(290, 330, 25, 25);
	btnclear.setBorder(hh.borderf);
	btnclear.setText("x");
	tPanel.add(btnclear);
	btnclear.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			txsearch.setText("");
			txsearch.requestFocus();
			table_update("");
		}
	});

	btnsearch = gr.sbcs("Filter");
	btnsearch.setForeground(hh.skek);
	btnsearch.setBackground(Color.ORANGE);
	btnsearch.setBounds(325, 330, 90, 30);
	btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	tPanel.add(btnsearch);
	btnsearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			sqlgyart();
		}
	});
	
	setVisible(true);	
}
private void savebuttrun() {
	String sql = "";	
	int uid = 0;
	String unity = "";
	DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
	String name = txname.getText();	
	String uname ="";
	if (cmbunities.getSelectedItem() != null) {
		unity = String.valueOf(cmbunities.getSelectedItem());
		Unity  unit = (Unity) cmbunities.getSelectedItem();
		uid = unit.getUid();
		uname = unit.getUname();
	}

	if (hh.zempty(name) || hh.zempty(uname)) {
		JOptionPane.showMessageDialog(null, "Please fill name, unity  fields");
		return;
	}
	if (rowid != "") {	
		sql = "update  ingredients set iname= '" + name + "', uid= '" + uid + "' where iid = "
				+ rowid;
	} else {
		sql = "insert into ingredients (iname,uid) " + "values ('" + name + "','"+ uid + "')";
	}
	try {
		int flag = dh.Insupdel(sql);
		if (flag > 0) {
			hh.ztmessage("Success", "Message");
			if (rowid == "") {
				int myid = dd.table_maxid("SELECT MAX(iid) AS max_id from ingredients");
				d1.insertRow(d1.getRowCount(),
						new Object[] { myid, name, uid, uname });
				hh.gotolastrow(intable);
				if (intable.getRowCount() > 0) {
					int row = intable.getRowCount() - 1;
					intable.setRowSelectionInterval(row, row);
				}
			} else {
				table_rowrefresh(name, uid, uname);
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
private void table_rowrefresh(String name, int uid, String uname) {
	DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
	d1.setValueAt(name, myrow, 1);
	d1.setValueAt(uid, myrow, 2);	
	d1.setValueAt(uname, myrow, 3);
}
private void clearFields() {
	txname.setText("");;
	cmbunities.setSelectedIndex(0);
	txname.requestFocus();
	rowid = "";
	myrow = 0;
}
private int data_delete(){
String sql = "delete from ingredients  where iid =";
int flag = 0;
DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
int sIndex = intable.getSelectedRow();
if (sIndex < 0) {
	return flag;
}
String iid = d1.getValueAt(sIndex, 0).toString();
if (iid.equals("")) {
	return flag;
}
if (ingredient_delete(iid) == false) {
	JOptionPane.showMessageDialog(null, "You can not delete this ingredient !");
	return flag;
}

int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
if (a == JOptionPane.YES_OPTION) {
	String vsql = sql + iid;
	flag = dh.Insupdel(vsql);
	if (flag > 0) {
		d1.removeRow(sIndex);
		clearFields();
	}
}
return flag;
}

private boolean ingredient_delete(String iid) {
	boolean ret = true;
	String Sql = "Select iid from recipes_ingredients where iid='"+ iid+"'";	
	try {
		res = dh.GetData(Sql);
		while (res.next()) {
		ret = false;
		break;
		}	
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		dh.CloseConnection();			
	}			
	return ret;
}

private void table_update(String what) {
	DefaultTableModel model = (DefaultTableModel) intable.getModel();
	model.setRowCount(0);
	String Sql = "";
	if (what == "") {
		Sql = "select  i.iid, i.iname, i.uid, u.uname from ingredients i "
				+ " join unities u  on i.uid = u.uid order by upper(iname)";
	} else {
		Sql = "select  i.iid, i.iname, i.uid, u.uname from ingredients i "
				+ " join unities u  on i.uid = u.uid where " + what + " order by Upper(iname)";
	}
	try {
		res = dh.GetData(Sql);
		while (res.next()) {
			String iid = res.getString("iid");
			String iname = res.getString("iname");				
			String uid = res.getString("uid");		
			String uname = res.getString("uname");		
			model.addRow(new Object[] { iid, iname, uid, uname });
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		dh.CloseConnection();
	}

	String[] fej = { "iid", "Name", "uid", "Unity" };
	((DefaultTableModel) intable.getModel()).setColumnIdentifiers(fej);
	hh.setJTableColumnsWidth(intable, 400, 0, 80,0,20	);
	intable.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			intable.scrollRectToVisible(intable.getCellRect(intable.getRowCount() - 1, 0, true));
		}
	});
	if (intable.getRowCount() > 0) {
		int row = intable.getRowCount() - 1;
		intable.setRowSelectionInterval(row, row);
	}
}
private void sqlgyart() {
	String stext = txsearch.getText().trim().toLowerCase();
	String swhere = "";
	if (!hh.zempty(stext)) {	
	   swhere = " lower(iname) like '%" + stext.trim() + "%'";	
		table_update(swhere);
	} else {
		JOptionPane.showMessageDialog(null, "Empty condition !", "Error", 1);
		return;
	}	
}	
	public static void main(String args[]) {
		
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {	            
	                	Ingredients in  = new Ingredients();	
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}
JLabel lbheader, lbname, lbunity, lbsearch;
JTextField txname, txsearch;
JComboBox cmbunities, cmbsearch;
JPanel exitPanel, bPanel, ePanel,tPanel;
JButton btndelete, btnsave, btncancel, btnsearch, btnclear;
JScrollPane jScrollPane1;
JTable intable;
}
