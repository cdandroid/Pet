package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.qf.entity.Cat;
import com.qf.entity.Dog;
import com.qf.entity.Pet;
import com.qf.service.ManageService;
import com.qf.service.PetService;
import com.qf.service.impl.ManageServiceImpl;
import com.qf.service.impl.PetServiceImpl;

public class Manager extends JFrame {
	private static final long serialVersionUID = -8791195532092072761L;
	private JTable tabMyPet;
	private SelfTableModel model;

	private ManageService manageService = new ManageServiceImpl();
	private PetService petService = new PetServiceImpl();
	private List<Pet> pets;
	private JMenuItem jmiAddDog;
	private JMenuItem jmiAddCat;
	private JTabbedPane jtpMain;
	private JMenuItem jmiUpdate;
	private JMenuItem jmiDelete;
	private JMenuItem jmiRefresh;
	private JPopupMenu jpm;

	public Manager() {
		initComponent();
		registerListener();
		loadServerData();
		this.setVisible(true);
	}

	/**
	 * ע���¼�������
	 */
	private void registerListener() {
		jmiAddDog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditDog(null, EditPet.ADD).setManager(Manager.this);
			}
		});

		jmiAddCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditCat(null, EditPet.ADD).setManager(Manager.this);
				;
			}
		});

		jmiUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tabMyPet.getSelectedRow();
				if (index == -1)
					return;
				Pet pet = (Pet) model.getValueAt(index, tabMyPet.getColumnCount() - 1);
				if (pet instanceof Dog) {
					new EditDog((Dog) pet, EditPet.UPDATE).setManager(Manager.this);
					;
				} else if (pet instanceof Cat) {
					new EditCat((Cat) pet, EditPet.UPDATE).setManager(Manager.this);
					;
				}
			}
		});

		jmiDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tabMyPet.getSelectedRow();
				if (index == -1)
					return;
				Pet pet = (Pet) model.getValueAt(index, tabMyPet.getColumnCount() - 1);
				if (pet.getUid() != 0) {
					JOptionPane.showMessageDialog(null, "�ó����ѱ�����������ɾ��");
					return;
				}
				int result = JOptionPane.showConfirmDialog(null, "�Ƿ�ɾ��ѡ�г��");
				if (result == JOptionPane.YES_OPTION) {
					if (petService.deleteServerPet(pet)) {
						pets.remove(pet);
						refreshStore();
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
					} else {
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
					}
				}
			}
		});

		jmiRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadServerData();
			}
		});

		tabMyPet.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					jpm.show(tabMyPet, e.getX(), e.getY());
				}
			}
		});
	}

	/**
	 * ��ʼ������
	 */
	private void initComponent() {
		this.setTitle("����Ա������");
		this.setSize(500, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JMenuBar jmb = new JMenuBar();
		this.setJMenuBar(jmb);

		JMenu jmManage = new JMenu("����");
		JMenu jmiAdd = new JMenu("���");
		jmiAddDog = new JMenuItem("���ﹷ");
		jmiAddCat = new JMenuItem("����è");
		jmiAdd.add(jmiAddDog);
		jmiAdd.add(jmiAddCat);
		jmManage.add(jmiAdd);
		jmb.add(jmManage);

		jtpMain = new JTabbedPane();
		String[] tabNames = { "���г���" };

		tabMyPet = new JTable(25, 4);
		JScrollPane jspMyPet = new JScrollPane(tabMyPet);
		model = new SelfTableModel();
		tabMyPet.setModel(model);
		hideLastColumn(tabMyPet);
		jtpMain.add(tabNames[0], jspMyPet);
		this.add(jtpMain);

		jpm = new JPopupMenu();
		jmiUpdate = new JMenuItem("�޸�");
		jmiDelete = new JMenuItem("ɾ��");
		jmiRefresh = new JMenuItem("ˢ��");
		jpm.add(jmiUpdate);
		jpm.add(jmiDelete);
		jpm.add(jmiRefresh);
	}

	/**
	 * ���ر����
	 * 
	 * @param tab
	 *            ���
	 */
	private void hideLastColumn(JTable tab) {
		TableColumn column = tab.getTableHeader().getColumnModel().getColumn(tab.getColumnCount() - 1);
		column.setWidth(0);
		column.setMaxWidth(0);
		column.setMinWidth(0);
		TableColumn tableColumn = tab.getColumnModel().getColumn(tab.getColumnCount() - 1);
		tableColumn.setMaxWidth(0);
		tableColumn.setMinWidth(0);
		tableColumn.setPreferredWidth(0);
		tableColumn.setWidth(0);
	}

	/**
	 * ���ط���������
	 */
	private void loadServerData() {
		pets = manageService.loadAllStorePets();
		for (Pet pet : pets) {
			model.addRow(pet);
		}
		refreshStore();
	}

	/**
	 * ˢ���̵�����
	 */
	public void refreshStore() {
		int index = tabMyPet.getSelectedRow();
		model.getContent().clear();
		for (Pet pet : pets) {
			model.addRow(pet);
		}
		model.fireTableDataChanged();
		if (index != -1)
			tabMyPet.changeSelection(index, 0, false, false);
	}
}
