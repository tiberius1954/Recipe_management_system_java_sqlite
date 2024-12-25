import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import net.proteanit.sql.DbUtils;

import javax.swing.*;

public class Records extends JFrame {
	Connection con;
	Statement stmt = null;
	grlib gr = new grlib();
	Hhelper hh = new Hhelper();
	Hhelper.StringUtils hss = new Hhelper.StringUtils();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String myrid = "";

	Records() {
		initcomponents();
		dd.rectable_update(rectable, "");

	}

	private void initcomponents() {
		setSize(1220, 670);
		setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getContentPane().setBackground(new Color(128, 255, 128));
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		exitPanel = gr.makeexitpanel(this);
		exitPanel.setBounds(1115, 10, 100, 35);
		exitPanel.setOpaque(true);
		exitPanel.setBackground(new Color(128, 255, 128));
		add(exitPanel);
		lbheader = hh.fflabel("A R C H I V E S");
		lbheader.setBounds(40, 20, 240, 25);
		add(lbheader);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		tPanel = maketpanel();
		tPanel.setBounds(10, 60, 1200, 600);
		add(tPanel);
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
				DefaultTableModel d1 = (DefaultTableModel) link_table.getModel();
				try {
					int row = rectable.getSelectedRow();
					if (row > -1) {
						String rid = model.getValueAt(row, 0).toString();
						d1.setRowCount(0);
						ResultSet rs = dd.getlinkrows(rid);
						link_table.setModel(DbUtils.resultSetToTableModel(rs));
						String[] fej = { "", "Attachment" };
						((DefaultTableModel) link_table.getModel()).setColumnIdentifiers(fej);
						hh.setJTableColumnsWidth(link_table, 520, 0, 100);
						dh.CloseConnection();
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
		recPane.setBounds(10, 70, 650, 500);
		recPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(recPane);

		link_table = hh.ltable();
		JTableHeader header = link_table.getTableHeader();
		 header.setBorder(hh.borderz1);
		 header.setForeground(Color.green);
		 Font font = new Font("tahoma", Font.BOLD, 12);
		 header.setFont(font);
		link_table.setModel(
				new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "", "Attachments" }));
		hh.setJTableColumnsWidth(link_table, 520, 0, 100);
		linkPane = new JScrollPane();
		linkPane = new JScrollPane(link_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		linkPane.setViewportView(link_table);
		linkPane.setBounds(670, 250, 520, 170);

		linkPane.setBorder(hh.borderz);
		ttpanel.add(linkPane);
		link_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				linktableMouseClicked(evt);
			}
		});

		btnclip = new JButton();
		btnclip.setBounds(900, 450, 40, 40);
		btnclip.setToolTipText("Attach documents");
		btnclip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnclip.setBorder(hh.myRaisedBorder);
		btnclip.setMargin(new Insets(10, 10, 10, 10));
		try {
			ImageIcon ImageIcon = new ImageIcon(ClassLoader.getSystemResource("Images/clip.png"));
			Image image = ImageIcon.getImage();
			btnclip.setIcon(new ImageIcon(image));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		ttpanel.add(btnclip);
		btnclip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DefaultTableModel d2 = (DefaultTableModel) rectable.getModel();
				int row = rectable.getSelectedRow();
				if (row < 0) {
					return;
				}
				String Rid = d2.getValueAt(row, 0).toString();
				File sourceFile = hh.myfilechooser();
				if (sourceFile.length() == 0 || !sourceFile.exists()) {
					return;
				}
				String ques = sourceFile.getAbsolutePath().toString();
				if (!ques.equals("")) {
					String filename = sourceFile.getName();
					String basePath = new File("").getAbsolutePath();
					File destFile = new File(basePath + "\\documents\\" + filename);
					try {
						hh.copyFile(sourceFile, destFile);
						String sql = "insert into documents (rid, dname, dpath) " + "values ('" + Rid + "','" + filename
								+ "','" + basePath + "\\documents\\" + "')";
						int flag = dh.Insupdel(sql);
						if (flag > 0) {
							int myid = dd.table_maxid("SELECT MAX(did) AS max_id from documents");
							DefaultTableModel d1 = (DefaultTableModel) link_table.getModel();
							d1.insertRow(d1.getRowCount(), new Object[] { myid, filename });
							hh.gotolastrow(link_table);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		return ttpanel;
	}

	private void linktableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = link_table.getSelectedRow();
		if (row >= 0) {
			String filename = link_table.getValueAt(row, 1).toString();
			if (!hh.zempty(filename)) {
				String did = link_table.getValueAt(row, 0).toString();
				hh.new linkPopupMenu(filename, link_table, did, row);
			}
		}
	}
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
					Records or = new Records();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JPanel exitPanel, tPanel;
	JLabel lbheader, lbsearch;
	JTable rectable, link_table;
	JButton btnsearch, btnclear, btnclip;
	JComboBox cmbsearch;
	JTextField txsearch;
	JScrollPane recPane, linkPane;
}
