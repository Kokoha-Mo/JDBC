package tw.lab.apis;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GiftTable extends JTable{
	
	private GiftModel giftModel;
	private GiftDAO dao;
	
	public GiftTable() {
		
		try {
			dao = new GiftDAO();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		giftModel = new GiftModel();
		setModel(giftModel);
		giftModel.setColumnIdentifiers(dao.getFields());
		
		
	}
	
	public void delData() {
		if(getSelectedRow() != -1) {
			dao.delRow(getSelectedRow());
			repaint();
		}
	}
	
	
	
	private class GiftModel extends DefaultTableModel {

		@Override
		public int getRowCount() {
			return dao.getRows();
		}

		@Override
		public int getColumnCount() {
			return dao.getCols();
		}

		@Override
		public Object getValueAt(int row, int column) { //0base
			return dao.getData(row, column); 
		}
		
		@Override
		public void setValueAt(Object aValue, int row, int column) {
//			System.out.println(aValue + ":" + row + ":" + column);
			dao.updateData((String) aValue, row, column);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column != 0;
		}
		
		
		
	}

	
	
	
}
