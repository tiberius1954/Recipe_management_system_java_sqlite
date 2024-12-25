package Databaseop;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import Classes.Category;
import Classes.Hhelper;
import Classes.Ingredient;
import Classes.Unity;
import net.proteanit.sql.DbUtils;

public class Databaseop {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;	
	DatabaseHelper dh = new DatabaseHelper();
	Hhelper hh = new Hhelper();

	public int data_delete(JTable dtable, String sql) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}	
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
			}
		}
		return flag;
	}	

	public void rtable_delete(JTable dtable, String sql) {
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int flag = dh.Insupdel(sql);
		if (flag > 0) {
			d1.setRowCount(0);
		}
	}	
	
	public int tdata_delete(JTable dtable, String sql, int row) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();	
		flag = dh.Insupdel(sql);
		if (flag == 1) {
			d1.removeRow(row);
		}
		return flag;
	}
	public ResultSet getlinkrows(String rid) throws SQLException {
		String sql = "select did, dname from documents  where rid ='" + rid + "'";
		ResultSet rs = dh.GetData(sql);
		return rs;
	}


	public void rectable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  r.rid, r.rname,  r.cid, c.cname, r.instructions, r.cook_time  from recipes r "
					+ " join categories c  on r.cid = c.cid ";
		} else {
			Sql = "select  r.rid, r.rname, r.cid, c.cname, r.instructions, r.cook_time from recipes r"
					+ " join categories c  on r.cid = c.cid where " + what;
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String rid= rs.getString("rid");
				String rname = rs.getString("rname");
				String cid= rs.getString("cid");
				String cname = rs.getString("cname");			
				String instructions = rs.getString("instructions");
				String cooktime = rs.getString("cook_time");			
				m1.addRow(new Object[] { rid, rname, cid,  cname, instructions, cooktime });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "rid", "Name", "cid", "Category", "Instructions", "Cook time"};		
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 650, 0, 60, 0, 20, 0, 20);		
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);	
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		dtable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);	
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}
	public void ingtable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  r.reinid, r.rid, r.iid,  i.iname, r.uid, u.uname, r.quantity  from recipes_ingredients r "
					+ " join ingredients  i on r.iid = i.iid   join unities u on r.uid = u.uid";
		} else { 
			Sql = "select  r.reinid, r.rid, r.iid,  i.iname,  r.uid, u.uname, r.quantity  from recipes_ingredients r"
					+ "  join ingredients  i on r.iid = i.iid   join unities u on r.uid = u.uid  where rid= " + what;
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String reinid= rs.getString("reinid");
				String rid= rs.getString("rid");
				String iid= rs.getString("iid");
				String iname= rs.getString("iname");
				String uid = rs.getString("uid");			
				String uname = rs.getString("uname");
				String quantity= rs.getString("quantity");			
				m1.addRow(new Object[] { reinid, rid, iid, iname, quantity,uid, uname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "reinid", "rid", "iid", "Name","Quantity","uid","Unity"};		
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 650, 0, 0, 0, 70, 15, 0,15);		
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);	
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		dtable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);	
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}
	public void ingetable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  r.reinid, r.rid, r.iid,  i.iname, r.uid, u.uname, r.quantity  from recipes_ingredients r "
					+ " join ingredients  i on r.iid = i.iid   join unities u on r.uid = u.uid";
		} else { 
			Sql = "select  r.reinid, r.rid, r.iid,  i.iname,  r.uid, u.uname, r.quantity  from recipes_ingredients r"
					+ "  join ingredients  i on r.iid = i.iid   join unities u on r.uid = u.uid  where rid= " + what;
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String reinid= rs.getString("reinid");
				String rid= rs.getString("rid");
				String iid= rs.getString("iid");
				String iname= rs.getString("iname");
				String uid = rs.getString("uid");			
				String uname = rs.getString("uname");
				String quantity= rs.getString("quantity");			
				m1.addRow(new Object[] { reinid, rid, iid, iname, quantity,uid, uname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	
		String[] fej = { "reinid", "rid", "iid", "Name","Quantity","uid","Unity"};		
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 480, 0, 0, 0, 60, 15, 0, 25);		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);	
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		dtable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);	
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}

	public int table_maxid(String sql) {
		int myid = 0;
		try {
			con = dh.getConnection();
			rs = dh.GetData(sql);
			if (!rs.next()) {
				System.out.println("Error.");
			} else {
				myid = rs.getInt("max_id");
			}
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return myid;
	}

	public void unitiescombofill(JComboBox ccombo) {
		ccombo.removeAllItems();
		Unity A = new Unity(0, " ");
		ccombo.addItem(A);
		String sql = "select uid, uname  from unities order by upper(uname)";
		try {
			ResultSet rs = dh.GetData(sql);
			while (rs.next()) {
				A = new Unity(rs.getInt("uid"), rs.getString("uname"));
				ccombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void categoriescombofill(JComboBox mycombo)  {
		mycombo.removeAllItems();
		Category A = new Category(0, "");
		mycombo.addItem(A);
		String sql = "select cid, cname from categories order by cname";
		try {
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Category(rs.getInt("cid"), rs.getString("cname"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ingredientscombofill(JComboBox mycombo) {
		String ss ="";
		mycombo.removeAllItems();
		Ingredient A = new Ingredient(0," ",0);
		mycombo.addItem(A);
		try {
		String sql = "select iid, iname, uid from ingredients order by upper( iname)";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Ingredient(rs.getInt("iid"), rs.getString("iname"),rs.getInt("uid"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Boolean cannotdelete(String sql) {
		Boolean found = false;
		rs = dh.GetData(sql);
		try {
			if (rs.next()) {
				found = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		return found;
	}
}
