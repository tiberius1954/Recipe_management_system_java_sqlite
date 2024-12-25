import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import Classes.Hhelper;
import Classes.grlib;

public class Mainframe extends JFrame {
	grlib gr = new grlib();
	Border borderw = BorderFactory.createLineBorder(Color.WHITE, 1);
	Hhelper hh = new Hhelper();

	Mainframe() {
		initComponents();
		 ifdirectory();
		try {
			for (int i = 0; i < 10; i++) {
				Thread.sleep(300);
				slowly(i);
			}
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}

	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setUndecorated(true);

		kGradientPanel1 = new keeptoo.KGradientPanel();
		kGradientPanel1.setkEndColor(new Color(51, 255, 51));
		kGradientPanel1.setkStartColor(new Color(0, 179, 0));

		kGradientPanel1.setLayout(null);
		kGradientPanel1.setBounds(10, 10, 1300, 670);

		lbheader = new JLabel();
		lbheader.setFont(new java.awt.Font("Century Gothic", 0, 64));
		lbheader.setForeground(new java.awt.Color(255, 255, 255));
		lbheader.setText("Cookbook Recipes");
		lbheader.setBounds(200, 70, 930, 120);
		kGradientPanel1.add(lbheader);

		exitPanel = gr.makeexitpanel(this);
		exitPanel.setBounds(900, 10, 100, 35);
		exitPanel.setOpaque(true);
		exitPanel.setBackground(new Color(51, 255, 51));
		add(exitPanel);

		kGradientPanel1.add(exitPanel);
		add(kGradientPanel1);

		btnmenu1 = gr.sbcs("Recipes");
		btnmenu1.setForeground(Color.black);
		btnmenu1.setBackground(hh.vpiros);
		btnmenu1.setBounds(350, 200, 200, 40);
		kGradientPanel1.add(btnmenu1);
		btnmenu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnmenufv(1);
			}
		});

		btnmenu2 = gr.sbcs("Informations");
		btnmenu2.setForeground(Color.black);
		btnmenu2.setBackground(hh.vpiros);
		btnmenu2.setBounds(450, 250, 200, 40);
		kGradientPanel1.add(btnmenu2);
		btnmenu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnmenufv(2);
			}
		});
		btnmenu3 = gr.sbcs("Archives");
		btnmenu3.setForeground(Color.black);
		btnmenu3.setBackground(hh.vpiros);
		btnmenu3.setBounds(350, 300, 200, 40);
		kGradientPanel1.add(btnmenu3);
		btnmenu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnmenufv(3);
			}
		});		
		
		btnmenu4 = gr.sbcs("Ingredients");
		btnmenu4.setForeground(Color.black);
		btnmenu4.setBackground(hh.vpiros);
		btnmenu4.setBounds(350, 350, 200, 40);
		kGradientPanel1.add(btnmenu4);
		btnmenu4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnmenufv(4);
			}
		});

		btnmenu5 = gr.sbcs("Food categories");
		btnmenu5.setForeground(Color.black);
		btnmenu5.setBackground(hh.vpiros);
		btnmenu5.setBounds(450, 400, 200, 40);
		kGradientPanel1.add(btnmenu5);
		btnmenu5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnmenufv(5);
			}
		});
		btnmenu6 = gr.sbcs("Unities");
		btnmenu6.setForeground(Color.black);
		btnmenu6.setBackground(hh.vpiros);
		btnmenu6.setBounds(350, 450, 200, 40);
		kGradientPanel1.add(btnmenu6);
		btnmenu6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				btnmenufv(6);
			}
		});
		
		setMinimumSize(new Dimension(100, 100));
		setSize(new java.awt.Dimension(1000, 600));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void btnmenufv(int ii) {
		if (ii == 1) {
            Recipest fv = new Recipest();
            fv.setVisible(true);
		} else if (ii == 2) {
			 Recipeview fv = new Recipeview();
	            fv.setVisible(true);
		} else if (ii == 3) {
			Records fv = new Records();
			fv.setVisible(true);	            
		} else if (ii == 4) {
			Ingredients fv = new Ingredients();
			fv.setVisible(true);
		} else if (ii == 5) {
			Categories fv = new Categories();
			fv.setVisible(true);
		} else if (ii == 6) {
			Unities fv = new Unities();
			fv.setVisible(true);
		}
	}

	private void slowly(int i) {
		switch (i) {
		case 1:
			btnmenu1.setBounds(400, 200, 200, 40);
			break;
		case 2:
			btnmenu2.setBounds(400, 250, 200, 40);
			break;
		case 3:
			btnmenu3.setBounds(400, 300, 200, 40);
			break;
		case 4:
			btnmenu4.setBounds(400, 350, 200, 40);
			break;
		case 5:
			btnmenu5.setBounds(400, 400, 200, 40);
			break;
		case 6:
			btnmenu6.setBounds(400, 450, 200, 40);
			break;
		default:
		}
	}
	private void ifdirectory() {
		String basePath = new File("").getAbsolutePath();
		String dirName = basePath + "\\documents\\";
		File theDir = new File(dirName);
		if (!theDir.exists())
			theDir.mkdirs();
	}


	public static void main(String args[]) {
	
	// EventQueue.invokeLater(new Runnable() {
//	            public void run() {
//	                try {	            
	                	Mainframe ts = new Mainframe();
	            		ts.setVisible(true);
//	                } catch (Exception e) {
//	                    e.printStackTrace();
//	                }
//	            }
//	        });
	}

	private keeptoo.KGradientPanel kGradientPanel1;
	private JButton exitbutton, minbutton, maxbutton;
	private JButton btnmenu1, btnmenu2, btnmenu3, btnmenu4, btnmenu5, btnmenu6;
	private JPanel exitPanel;
	JLabel lbheader;
	JSeparator jSeparator;
}
