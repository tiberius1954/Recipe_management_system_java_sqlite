import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Hhelper;
import Classes.Unity;
import Classes.Category;
import Classes.Ingredient;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Recipest extends JFrame {
	Connection con;
	Statement stmt = null;
	grlib gr = new grlib();
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String myrid = "";
	private String rowid = "";
	private int myrow = 0;
	private String rowuid ="";
	private int myurow=0;

	Recipest() {
		initcomponents();
		dd.unitiescombofill(cmbunities);
		dd.ingredientscombofill(cmbingredients);
		dd.categoriescombofill(cmbcategories);
		dd.rectable_update(rectable, "");			
	}

	private void initcomponents() {
		setSize(1220, 670);
		setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getContentPane().setBackground(new Color(128, 255, 128));
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
		exitPanel = gr.makeexitpanel(this);
		exitPanel.setBounds(1115, 10, 100, 35);
		exitPanel.setOpaque(true);
		exitPanel.setBackground(new Color(128, 255, 128));
		add(exitPanel);
		lbheader = hh.fflabel("R E C I P E S");
		lbheader.setBounds(40, 20, 180, 25);
		add(lbheader);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if (hh.whichpanel(cardPanel) == "tabla") {
					dispose();
				} else if (hh.whichpanel(cardPanel) == "edit") {
					cards.show(cardPanel, "tabla");
				} else {
					cards.show(cardPanel, "tabla");
				}
			}
		});

		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 60, 1200, 600);
		tPanel = maketpanel();
		tPanel.setName("tabla");

		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(tPanel, "tabla");
		cardPanel.add(ePanel, "edit");
		add(cardPanel);
	   cards.show(cardPanel, "tabla");
	//	cards.show(cardPanel, "edit");
		setVisible(true);
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);	
		ttpanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));	
		ttpanel.setBackground(new Color(128, 255, 128));
		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(20, 25, 70, 25);
		ttpanel.add(lbsearch);

		txsearch = gr.gTextField(25);
		txsearch.setBounds(95, 25, 200, 30);
		ttpanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(295, 25, 25, 30);	
		btnclear.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnclear.setText("x");
		ttpanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				dd.rectable_update(rectable, "");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(325, 25, 180, 30);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.setBackground(Color.ORANGE);
		cmbsearch.addItem("Name");
		cmbsearch.addItem("Category");
		ttpanel.add(cmbsearch);

		btnsearch = gr.sbcs("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(510, 25, 130, 30);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		ttpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});

		rectable = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rectable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		rectable.setTableHeader(new JTableHeader(rectable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		rectable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) rectable.getModel();
				try {
					int row = rectable.getSelectedRow();			
					if (row > -1) {					
						myrid = model.getValueAt(row, 0).toString();
						dd.ingtable_update(ingtable, myrid);
				         String ss = model.getValueAt(row, 4).toString();
				         txainstruction.setText(ss);
				        txainstruction.setCaretPosition(0);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});		

		hh.madeheader(rectable);
		rectable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		recPane = new JScrollPane(rectable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		rectable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "rid", "Name", "cid", "Category", "Instructions", "Cook time" }));
		hh.setJTableColumnsWidth(rectable, 650, 0, 60, 0, 20, 0, 20);
		recPane.setViewportView(rectable);
		recPane.setBounds(30, 70, 650, 200);
		recPane.setBorder(hh.myRaisedBorder);		
		ttpanel.add(recPane);

		btnnew = gr.sbcs("New");
		btnnew.setBounds(180, 300, 100, 30);
		btnnew.setForeground(Color.black);
		btnnew.setBackground(Color.red);
		ttpanel.add(btnnew);
		btnnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_new();
			}
		});

		btnupdate = gr.sbcs("Update");
		btnupdate.setBounds(290, 300, 100, 30);
		btnupdate.setForeground(Color.black);
		btnupdate.setBackground(Color.green);

		ttpanel.add(btnupdate);
		btnupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DefaultTableModel model = (DefaultTableModel) ingetable.getModel();
				int row = rectable.getSelectedRow();
				if (row >= 0) {
					txrname.setText(rectable.getValueAt(row, 1).toString());					
					txaeinstruction.setText(rectable.getValueAt(row, 4).toString());
					txcooktime.setText(rectable.getValueAt(row, 5).toString());
					rowid = rectable.getValueAt(row, 0).toString();
					int number = 0;
					String cnum = rectable.getValueAt(row, 2).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
					}
					hh.setSelectedValue(cmbcategories, number);
					cmbcategories.updateUI();					
					myrow = row;
					dd.ingetable_update(ingetable, rowid);			      
					cards.show(cardPanel, "edit");
					txrname.requestFocus();	
				}			
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(400, 300, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);

		ttpanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});

		lbinstrlabel = hh.fflabel("Instructions");
		lbinstrlabel.setBounds(870, 30, 150, 25);
		ttpanel.add(lbinstrlabel);

		txtpanel = new JPanel(null);
		txtpanel.setBounds(700,70,480,500);
		ttpanel.add(txtpanel);

		txainstruction = new JTextArea();
		txainstruction.setBackground(hh.feher);
		txainstruction.setFont(hh.textf3);
		txainstruction.setCaretColor(Color.RED);
		txainstruction.putClientProperty("caretAspectRatio", 0.1);
		txainstruction.setBackground(hh.feher);
		txainstruction.setLineWrap(true);
		txainstruction.setWrapStyleWord(true);
		txainstruction.setEditable(false);
		jsp = new JScrollPane(txainstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
		jsp.setBounds(0, 0, 480, 500);
		jsp.setViewportView(txainstruction);
		Border border = BorderFactory.createLineBorder(Color.BLACK,2);	
		txainstruction
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		txtpanel.add(jsp);
		
		lbrecingredient = hh.fflabel("Ingredients");
		lbrecingredient.setBounds(30, 330, 130, 30);
		ttpanel.add(lbrecingredient);

		ingtable = hh.ztable();
		DefaultTableCellRenderer rrenderer = (DefaultTableCellRenderer) ingtable.getDefaultRenderer(Object.class);
		rrenderer.setHorizontalAlignment(SwingConstants.LEFT);
		ingtable.setTableHeader(new JTableHeader(ingtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ingtable);
		ingPane = new JScrollPane(ingtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ingtable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null } },
				new String[] { "reinid", "rid", "iid", "Name", "uid", "Unity", "Quantity" }));
		hh.setJTableColumnsWidth(ingtable, 650, 0, 0, 0, 70, 0, 15, 15);
		ingPane.setBounds(30, 360, 650, 210);
		ingPane.setViewportView(ingtable);	
		ingPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(ingPane);
	
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBackground(new Color(128, 255, 128));	
		eepanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));	
				
		lbeheader1 = hh.fflabel("Recipe header");
		lbeheader1.setBounds(270, 20, 200, 25);
		eepanel.add(lbeheader1);

		lbename = hh.clabel("Recipe name");
		lbename.setBounds(60, 60, 120, 25);
		eepanel.add(lbename);

		txrname = gr.gTextField(30);
		txrname.setBounds(210, 60, 250, 30);
		eepanel.add(txrname);
		txrname.addKeyListener( hh.MUpper());

		lbecategory = hh.clabel("Food category");
		lbecategory.setBounds(60, 100, 120, 25);
		eepanel.add(lbecategory);
	
		cmbcategories = gr.grcombo();
		cmbcategories.setName("categories");
		cmbcategories.setBounds(210, 100, 250, 30);
		eepanel.add(cmbcategories);

		lbecooktime = hh.clabel("Cook time");
		lbecooktime.setBounds(60, 140, 120, 25);
		eepanel.add(lbecooktime);

		txcooktime = gr.gTextField(30);
		txcooktime.setBounds(210, 140, 190, 30);
		txcooktime.setHorizontalAlignment(JTextField.RIGHT);
		eepanel.add(txcooktime);
		txcooktime.addKeyListener(hh.Onlynum());
		
		lbminute =hh.clabel("minute");
		lbminute.setBounds(400, 140, 60, 25);
	     eepanel.add(lbminute);
	     
		lbeheader3 = hh.fflabel("Instructions");
		lbeheader3.setBounds(270, 180, 200, 25);
		eepanel.add(lbeheader3);

		txtepanel = new JPanel(null);
		txtepanel.setBounds(100, 215, 481, 301);	
		eepanel.add(txtepanel);

		txaeinstruction = new JTextArea();
		txaeinstruction.setBackground(hh.feher);
		txaeinstruction.setFont(hh.textf3);
		txaeinstruction.setCaretColor(Color.RED);
		txaeinstruction.putClientProperty("caretAspectRatio", 0.1);
		txaeinstruction.setBackground(hh.feher);
		txaeinstruction.setLineWrap(true);
		txaeinstruction.setWrapStyleWord(true);

		jspe = new JScrollPane(txaeinstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspe.setBounds(0, 0, 480, 300);
		jspe.setViewportView(txaeinstruction);
	Border border = BorderFactory.createLineBorder(Color.BLACK,2);
		txaeinstruction
			.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		txtepanel.add(jspe);
		
		btnesave = gr.sbcs("Save");
		btnesave.setBounds(230, 540, 100, 30);
		btnesave.setForeground(Color.black);
		btnesave.setBackground(Color.red);
		eepanel.add(btnesave);
		btnesave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dataesave();
			}
		});

		btnecancel = gr.sbcs("Cancel");
		btnecancel.setBounds(340, 540, 100, 30);
		btnecancel.setForeground(Color.black);
		btnecancel.setBackground(Color.green);

		eepanel.add(btnecancel);
		btnecancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearuFields();
				cards.show(cardPanel, "tabla");				
			}
		});  

		lbeheader2 = hh.fflabel("Recipe ingredients");
		lbeheader2.setBounds(780, 20, 200, 25);
		eepanel.add(lbeheader2);

		ingetable = hh.ztable();
		DefaultTableCellRenderer rrenderer = (DefaultTableCellRenderer) ingetable.getDefaultRenderer(Object.class);
		rrenderer.setHorizontalAlignment(SwingConstants.LEFT);
		ingetable.setTableHeader(new JTableHeader(ingetable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ingetable);
		ingePane = new JScrollPane(ingetable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ingetable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null } },
				new String[] { "reinid", "rid", "iid", "Name", "Quantity", "uid", "Unity" }));

		hh.setJTableColumnsWidth(ingetable, 480, 0, 0, 0, 60, 15, 0, 25);
		
		ingePane.setViewportView(ingetable);
		ingePane.setBounds(640, 60, 480, 210);
		ingePane.setBorder(hh.myRaisedBorder);	
		eepanel.add(ingePane);
		ingetable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				ingetableMouseClicked(evt);
			}
		});

		ieditpanel = new JPanel(null);
		ieditpanel.setBounds(640, 300, 480, 270);
		ieditpanel.setBackground(new Color(128, 255, 128));
		ieditpanel.setBorder(hh.myRaisedBorder);	
		eepanel.add(ieditpanel);

		lbeingredient = hh.clabel("Ingredient name");
		lbeingredient.setBounds(10, 30, 130, 25);
		ieditpanel.add(lbeingredient);

		cmbingredients = gr.grcombo();
		cmbingredients.setName("ingredients");
		cmbingredients.setBounds(150, 30, 200, 30);
		ieditpanel.add(cmbingredients);
		cmbingredients.addFocusListener(cFocusListener);

		lbquantity = hh.clabel("Quantity");
		lbquantity.setBounds(10, 70, 130, 25);
		ieditpanel.add(lbquantity);
	
		txquantity = gr.gTextField(25);
		txquantity.setBounds(150, 70, 200, 30);
		txquantity.setHorizontalAlignment(JTextField.RIGHT);
		ieditpanel.add(txquantity);
		txquantity.addKeyListener(hh.Onlynum());

		lbunity = hh.clabel("Unity");
		lbunity.setBounds(10, 110, 130, 25);
		ieditpanel.add(lbunity);

		cmbunities = gr.grcombo();
		cmbunities.setName("unities");
		cmbunities.setBounds(150, 110, 200, 30);
		ieditpanel.add(cmbunities);
		eepanel.add(ieditpanel);

		btnusave = gr.sbcs("Save");
		btnusave.setBounds(90, 190, 100, 30);
		btnusave.setForeground(Color.black);
		btnusave.setBackground(Color.red);
		ieditpanel.add(btnusave);
		btnusave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				ingdata_save();
			}
		});
		

		btnucancel = gr.sbcs("Cancel");
		btnucancel.setBounds(200, 190, 100, 30);
		btnucancel.setForeground(Color.black);
		btnucancel.setBackground(Color.green);
		ieditpanel.add(btnucancel);
		btnucancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				ingdata_cancel();
			}
		});

		btnudelete = gr.sbcs("Delete");
		btnudelete.setBounds(310, 190, 100, 30);
		btnudelete.setForeground(Color.black);
		btnudelete.setBackground(Color.yellow);
		ieditpanel.add(btnudelete);
		btnudelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				ingdata_delete();
			}
		});
		return eepanel;
	}
	private void data_new() {
		clearFields();		
		cards.show(cardPanel, "edit");
		 txrname.requestFocus();		
	}
	private void dataesave() {
		DefaultTableModel d1 = (DefaultTableModel) rectable.getModel();
		String sql = "";
		String jel = "";
		String rname = txrname.getText();
		String cooktime = txcooktime.getText();
		int cid =0;
		String cname ="";
		String instruct = txaeinstruction.getText();
		if (cmbcategories.getSelectedItem() != null) {	
			Category  cat = (Category) cmbcategories.getSelectedItem();
			cid = cat.getCid();
			cname = cat.getCname();
		}
		
		if (hh.zempty(cname)|| hh.zempty(cname)) {
			JOptionPane.showMessageDialog(null, "Please fill name, category fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update recipes set rname= '" + rname + "',  cid ="+cid +", instructions='"+
			instruct +"', cook_time ='" + cooktime+"' where rid = " + rowid;
		} else {
			sql = "insert into recipes (rname, cid, instructions, cook_time) " + "values ('" + rname +
					"'," + cid +",'" +instruct +"','" + cooktime +"')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag >0) {
				hh.ztmessage("Success", "Message");
				txainstruction.setText(instruct);
				if (jel == "UP") {
					table_rowrefresh(rname, cid, cname, instruct, cooktime);
				} else {			
					int myid = dd.table_maxid("SELECT MAX(rid) AS max_id from recipes");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, rname, cid, cname, instruct, cooktime});						
						int irow = d1.getRowCount();
						hh.gotolastrow(rectable);
						rectable.setRowSelectionInterval(irow - 1, irow - 1);						
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
		cards.show(cardPanel, "tabla");		
	}
	private void table_rowrefresh(String rname, int cid, String cname, String instruct, String cooktime){
		DefaultTableModel d1 = (DefaultTableModel) rectable.getModel();
		d1.setValueAt(rname, myrow, 1);
		d1.setValueAt(cid, myrow, 2);	
		d1.setValueAt(cname, myrow, 3);
		d1.setValueAt(instruct, myrow, 4);
		d1.setValueAt(cooktime, myrow, 5);
	}
	
	private void data_delete() {	
		DefaultTableModel d1 = (DefaultTableModel) rectable.getModel();
		DefaultTableModel d2= (DefaultTableModel) ingtable.getModel();
		int sIndex = rectable.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		String rid = d1.getValueAt(sIndex, 0).toString();
		if (hh.zempty(rid)) {
			return;
		}	
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			    complexdelete(rid);		
				d1.removeRow(sIndex);
				d2.setRowCount(0);
				clearFields();
				txainstruction.setText("");
			}
		return;
		}	
	
	private void complexdelete(String rid) {
	try {
		con = dh.getConnection();
		con.setAutoCommit(false);
		stmt = con.createStatement();		
		String sql = "delete from recipes  where rid ='"+ rid +"'";
		stmt.executeUpdate(sql);		
		sql = "delete from recipes_ingredients  where rid ='"+ rid +"'";
		stmt.executeUpdate(sql);
		con.commit();
		con.setAutoCommit(true);
		stmt.close();
		con.close();
} catch (SQLException e) {
if (con != null) {
	try {
		con.rollback();
	} catch (SQLException e1) {
		 e1.printStackTrace();
	}
	e.printStackTrace();
}}
}
	
	private void clearFields() {	
		rowid = "";
		myrow = 0;
		txaeinstruction.setText("");	
		 txrname.setText("");
		 txcooktime.setText("");
		 cmbcategories.setSelectedIndex(-1);
		 DefaultTableModel model = (DefaultTableModel) ingetable.getModel();
		 model.setRowCount(0);
	  clearuFields();
	}
	private void clearuFields() {	
	 txquantity.setText("");
	 cmbunities.setSelectedIndex(-1);
	 cmbingredients.setSelectedIndex(-1);  
    rowuid = "";
     myurow=0;
	}
	private void ingdata_cancel() {
		clearuFields();			
		 cmbingredients.requestFocus();		
	}	
	private void ingdata_save() {
		if (rowid == "") {
			JOptionPane.showMessageDialog(null, "You did not save recipes data !");
			return;
		}
		DefaultTableModel d1 = (DefaultTableModel) ingetable.getModel();
		String sql = "";			
		String quantity = txquantity.getText();	
		String uname ="";
		String iname ="";
		int uid=0;
		int iid =0;
		String rid = rowid;
		if (cmbunities.getSelectedItem() != null) {
			Unity  unit = (Unity) cmbunities.getSelectedItem();
			uid = unit.getUid();
			uname = unit.getUname();
		}
		if (cmbingredients.getSelectedItem() != null) {		
					Ingredient  ing = (Ingredient) cmbingredients.getSelectedItem();
					iid = ing.getIid();
					iname = ing.getIname();
				}

		if (hh.zempty(iname) || hh.zempty(uname) ||  hh.zempty(quantity)) {
			JOptionPane.showMessageDialog(null, "Please fill ingredient, quantity, unity  fields");
			return;
		}
		if (rowuid != "") {	
				sql = "update  recipes_ingredients set rid= '" + rid + "', iid= " + iid + ",  uid=" +uid +
					", quantity= '" + quantity + "' where reinid = " + rowuid;
			} else {
				sql = "insert into recipes_ingredients (rid, iid, uid, quantity) values ('" + rid + "',"+ iid +","
						+ uid +",'" +quantity +"')";
			}	
		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowuid == "") {
					int myid = dd.table_maxid("SELECT MAX(reinid) AS max_id from recipes_ingredients");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, rid, iid, iname, quantity, uid, uname});
					hh.gotolastrow(ingetable);
					if (ingetable.getRowCount() > 0) {
						int row = ingetable.getRowCount() - 1;
						ingetable.setRowSelectionInterval(row, row);
					}
				} else {
					table_urowrefresh(rid, iid, iname, quantity, uid, uname);			
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearuFields();	
		dd.ingtable_update(ingtable, rowid);
	}
	private void table_urowrefresh(String rid, int iid, String iname, String quantity, int uid,  String uname){
		DefaultTableModel d1 = (DefaultTableModel) ingetable.getModel();
		d1.setValueAt(rid, myurow, 1);
		d1.setValueAt(iid, myurow, 2);	
		d1.setValueAt(iname, myurow, 3);		
		d1.setValueAt(quantity, myurow, 4);
		d1.setValueAt(uid, myurow, 5);
		d1.setValueAt(uname, myurow, 6);
	}
	
	private int  ingdata_delete() {
		String sql = "delete from recipes_ingredients  where reinid =";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) ingetable.getModel();
		int sIndex = ingetable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String reinid = d1.getValueAt(sIndex, 0).toString();
		if (reinid.equals("")) {
			return flag;
			}
	
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + reinid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
				clearuFields();
				dd.ingtable_update(ingtable, rowid);
			}
		}
		return flag;		
	}	
	
	private void ingetableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = ingetable.getSelectedRow();	
		String  rid = rowid;
			if (row >= 0) {
			String cnum = ingetable.getValueAt(row, 2).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbingredients, number);
			cmbingredients.updateUI();		
			
			cnum = ingetable.getValueAt(row, 5).toString();
			number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbunities, number);
			cmbunities.updateUI();		
			
			txquantity.setText(ingetable.getValueAt(row, 4).toString());		
			rowuid = ingetable.getValueAt(row, 0).toString();
			myurow = row;
		}
	}
	private final FocusListener cFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {		
			JComponent c = (JComponent) e.getSource();	
		}
		@Override
		public void focusLost(FocusEvent e) {			
			JComboBox bb = (JComboBox) e.getSource();			
			if (bb.getName()=="ingredients") {	
				Ingredient  ing = (Ingredient) cmbingredients.getSelectedItem();
				int uid = ing.getUid();	
				hh.setSelectedValue(cmbunities, uid);
				cmbunities.updateUI();
			}    		
		}
	};
	private void sqlgyart() {
		String stext = txsearch.getText().trim().toLowerCase();
		String scmbtxt = String.valueOf(cmbsearch.getSelectedItem());
		String swhere = "";
		if (!hh.zempty(stext)) {
			if (scmbtxt == "Name") {
				swhere = " lower(rname) like '%" + stext.trim() + "%'";
			} else if (scmbtxt == "Category") {
				swhere = " lower(cname) like '%" + stext.trim() + "%' ";
			}			
			dd.rectable_update(rectable, swhere);	
		} else {
			JOptionPane.showMessageDialog(null, "Empty condition !", "Error", 1);
			return;
		}
	}

	public static void main(String args[]) {
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {	            
	            		Recipest or = new Recipest();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}
	
	JPanel exitPanel, cardPanel, tPanel, ePanel, gePanel, lPanel, rPanel, tepanel, txtpanel;
	JPanel txtepanel, ieditpanel;
	CardLayout cards;
	JLabel lbheader, lbsearch, lbinstrlabel, lbeheader1, lbeheader2, lbeheader3, lbrecingredient;
	JLabel lbename, lbecategory, lbecooktime, lbeingredient, lbquantity, lbunity, lbminute;
	JTextField txsearch, txrname, txcooktime, txquantity;
	JButton btnsearch, btnclear, btnnew, btndelete, btnupdate;
	JButton btnesave, btnecancel;
	JButton btnusave, btnucancel, btnudelete;
	JComboBox cmbsearch, cmbcategories, cmbunities, cmbingredients;
	JTable rectable, ingtable, ingetable;
	JScrollPane recPane, ingPane, jsp, ingePane, jspe;
	JTextArea txainstruction, txaeinstruction;
}
