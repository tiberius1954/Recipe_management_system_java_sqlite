import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Hhelper;
import Classes.grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

public class Recipeview extends JFrame {
	Connection con;
	Statement stmt = null;
	grlib gr = new grlib();
	Hhelper hh = new Hhelper();
	 Hhelper.StringUtils hss =   new Hhelper.StringUtils();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();	
	private String myrid = "";
	private Vector vector_line;
	int num_lines;
	int line_Index;
	ArrayList<Integer> pageBreaks = new ArrayList<Integer>();
	private static final int TAB_SIZE = 5;
	private String recipename;
	

	Recipeview() {
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
		lbheader = hh.fflabel("R E C I P E S  V I E W");
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
				try {
					int row = rectable.getSelectedRow();
					if (row > -1) {
						myrid = model.getValueAt(row, 0).toString();
						dd.ingtable_update(ingtable, myrid);
						recipename = model.getValueAt(row, 1).toString().trim();
						String ss = model.getValueAt(row, 4).toString();
						txainstruction.setText(ss);
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

		lbinstrlabel = hh.fflabel("Instructions");
		lbinstrlabel.setBounds(870, 30, 150, 25);
		ttpanel.add(lbinstrlabel);

		txtpanel = new JPanel(null);
		txtpanel.setBounds(700, 70, 480, 460);
		ttpanel.add(txtpanel);

		txainstruction = new JTextArea();
		txainstruction.setBackground(hh.feher);
		txainstruction.setFont(hh.textf3);
		txainstruction.setBackground(hh.feher);
		txainstruction.setEditable(false);	  
		txainstruction.setLineWrap(true);
		txainstruction.setWrapStyleWord(true);
		   
		jsp = new JScrollPane(txainstruction, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(0, 0, 480, 458);
		jsp.setViewportView(txainstruction);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		txainstruction
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		txtpanel.add(jsp);

		lbrecingredient = hh.fflabel("Ingredients");
		lbrecingredient.setBounds(30, 290, 130, 30);
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
				new String[] { "reinid", "rid", "iid", "Name", "Quantity", "uid", "Unity" }));
		hh.setJTableColumnsWidth(ingtable, 650, 0, 0, 0, 70, 15, 0, 15);
		ingPane.setBounds(30, 320, 650, 210);
		ingPane.setViewportView(ingtable);
		ingPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(ingPane);

		btnprintingredient = gr.sbcs("Print ingredients");
		btnprintingredient.setBounds(270, 550, 170, 30);
		btnprintingredient.setForeground(Color.black);
		btnprintingredient.setBackground(Color.green);
		ttpanel.add(btnprintingredient);
		
		btnprintingredient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {				
			     DefaultTableModel m1 = (DefaultTableModel) ingtable.getModel();	
				if (m1.getRowCount() <= 0) {
					return;
				}				
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setJobName("Ingredientlist");
				HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();		
				attr.add(new MediaPrintableArea(10, 10, 190, 275, MediaPrintableArea.MM));
				job.print(attr);
		
				MessageFormat[] header = new MessageFormat[2];
				header[0] = new MessageFormat(hss.center("INGREDIENTS", 170));
				header[1] = new MessageFormat("");				
				
				MessageFormat[] footer = new MessageFormat[1];		
				footer[0] = new MessageFormat(hss.center("Page {0,number,integer}", 170));
				job.setPrintable(hh.new MyTablePrintable(ingtable, JTable.PrintMode.FIT_WIDTH, header, footer));
				job.printDialog();
				job.print();		
				
				} catch (java.awt.print.PrinterAbortException e) {
				} catch (PrinterException ex) {
					System.err.println("Error printing: " + ex.getMessage());
					ex.printStackTrace();
				}		
			}
		});

		btnprintinstruction = gr.sbcs("Print instructions");
		btnprintinstruction.setBounds(855, 550, 170, 30);
		btnprintinstruction.setForeground(Color.black);
		btnprintinstruction.setBackground(Color.green);
		ttpanel.add(btnprintinstruction);
		btnprintinstruction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				ztprintel();
				dispose();
			}
		});
		return ttpanel;
	}
	void ztprintel() {
		try {
			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
//             aset.add(MediaSizeName.ISO_A4);
			aset.add(OrientationRequested.PORTRAIT);
			aset.add(new JobName("instructionlist", null));
			aset.add(new MediaPrintableArea(10, 10, 190, 275, MediaPrintableArea.MM));
			// 210 , 297 A4 mm

			PrinterJob prnJob = PrinterJob.getPrinterJob();
			PageFormat pf = prnJob.defaultPage();
			prnJob.print(aset);

			prnJob.setPrintable(new MyPrintable(txainstruction));
			if (!prnJob.printDialog())
				return;
			prnJob.print();

		} catch (PrinterException ex) {
			System.out.println("Printing error: " + ex.toString());
		}
	}

	class MyPrintable implements Printable {
		JTextArea str;

		public MyPrintable(JTextArea getStr) {
			str = getStr;
		}

		public int print(Graphics g, PageFormat page_format, int pageIndex) throws PrinterException {
			g.translate((int) page_format.getImageableX(), (int) page_format.getImageableY());
			int wpage = (int) page_format.getImageableWidth();
			int hpage = (int) page_format.getImageableHeight();
			g.setClip(0, 0, wpage, hpage);
			g.setFont(new Font("Monospaced", Font.BOLD, 12));
			FontMetrics fm = g.getFontMetrics();
			int fontDescent = g.getFontMetrics().getDescent();
			int lineHeight = fm.getHeight() + fontDescent;
			if (vector_line == null) {
				vector_line = getLines(fm, wpage);
				line_Index = 0;
				num_lines = vector_line.size();
				while (line_Index < num_lines) {				
					if (line_Index % 35== 0 && line_Index!= 0) {
						pageBreaks.add(line_Index);
					}
					line_Index++;
				}
			}

			if (pageIndex > pageBreaks.size()) {
				return NO_SUCH_PAGE;
			}

			int x = 10;
			int y = fm.getAscent();
			int start = (pageIndex == 0) ? 0 : pageBreaks.get(pageIndex - 1);
			int end = (pageIndex == pageBreaks.size()) ? vector_line.size() : pageBreaks.get(pageIndex);
			for (int line = start; line < end; line++) {
				String str = (String) vector_line.get(line);			
				g.drawString(str, x, y);				
				y += lineHeight;
			}
			y += lineHeight;
			String pattern = hss.center("Page {0,number,integer}", 90);
			String footerText = MessageFormat.format(pattern, pageIndex + 1);
			g.drawString(footerText, x, y);

			return PAGE_EXISTS;
		}

		protected Vector getLines(FontMetrics fm, int wpage) {
			Vector vctr = new Vector();
			vctr.add("");
			vctr.add(hss.center(recipename,70));
			vctr.add("");
			String txt = txainstruction.getText();
			String prevToken = "";
			StringTokenizer stokn = new StringTokenizer(txt, "\n\r", true);
			while (stokn.hasMoreTokens()) {
				String line = stokn.nextToken();
				if (line.equals("\r"))
					continue;
				// Stringtokenizer will be ignore empty lines
				if (line.equals("\n") && prevToken.equals("\n"))
					vctr.add("");
				prevToken = line;
				if (line.equals("\n"))
					continue;
				StringTokenizer stokn2 = new StringTokenizer(line, " \t", true);
				String line2 = "";
				while (stokn2.hasMoreTokens()) {
					String token = stokn2.nextToken();
					if (token.equals("\t")) {
						int num_space = TAB_SIZE - line2.length() % TAB_SIZE;
						token = "";
						for (int i = 0; i < num_space; i++)
							token += " ";
					}
					int line_len = fm.stringWidth(line2 + token);
					if (line_len > wpage && line2.length() > 0) {
						vctr.add(line2);
						line2 = token.trim();
						continue;
					}
					line2 += token;
				}
				vctr.add(line2);
			}
			return vctr;
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
	                	Recipeview or = new Recipeview();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}

	JComboBox cmbsearch, cmbcategories, cmbunities, cmbingredients;
	JPanel exitPanel, tPanel, txtpanel;
	JLabel lbheader, lbsearch, lbinstrlabel, lbrecingredient;
	JTextField txsearch;
	JTable rectable, ingtable;
	JButton btnclear, btnsearch, btnprintingredient, btnprintinstruction;
	JScrollPane recPane, ingPane, jsp;
	JTextArea txainstruction;
}
