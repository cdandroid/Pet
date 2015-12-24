package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

import com.qf.entity.Cat;
import com.qf.entity.Dog;
import com.qf.entity.Pet;
import com.qf.service.ManageService;
import com.qf.service.PetService;
import com.qf.service.impl.ManageServiceImpl;
import com.qf.service.impl.PetServiceImpl;
import com.qf.util.Initialization;

/**
 * ������
 * 
 * @author С��
 *
 */
public class MainMenu extends JFrame {

	private static final long serialVersionUID = -5158003865654166239L;
	private PetService petService = new PetServiceImpl();
	private ManageService manageService = new ManageServiceImpl();
	private List<Pet> pets;
	private List<Pet> storePets;

	public static boolean firstOpen = true;
	public static boolean firstLoadServer = true;
	private JMenuItem jmiUpdate;
	private JMenuItem jmiLocal;
	private JMenuItem jmiExit;
	private JTable tabMyPet;
	private SelfTableModel model;
	private SelfTableModel modelStore;
	private JPopupMenu jpmContextMenu;
	private JMenuItem jmiMyDetail;
	private JMenuItem jmiFeed;
	private JMenuItem jmiPlay;
	private JMenuItem jmiDelete;
	private JTextField txtServer;
	private JTextField txtFreq;
	private JButton btnSetting;
	private JTable tabStore;
	private JPopupMenu popupMenu;
	private JMenuItem jmiRefresh;
	private JMenuItem jmiAdopt;
	private JTabbedPane jtpMain;
	private JMenuItem jmiStoreDetail;

	public MainMenu() {
		initComponent();
		loadData();
		refreshInTime();
		registerListener();
		this.setVisible(true);
	}

	/**
	 * ע���¼�����
	 */
	private void registerListener() {
		tabMyPet.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					jpmContextMenu.show(tabMyPet, e.getX(), e.getY());
			}
		});

		tabStore.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					popupMenu.show(tabStore, e.getX(), e.getY());
			}
		});

		jtpMain.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (firstLoadServer && jtpMain.getSelectedIndex() == 1) {
					firstLoadServer = false;
					loadServerData();
				}
			}
		});

		PopupmenuHandler poupHandler = new PopupmenuHandler();
		jmiMyDetail.addActionListener(poupHandler);
		jmiFeed.addActionListener(poupHandler);
		jmiPlay.addActionListener(poupHandler);
		jmiDelete.addActionListener(poupHandler);
		jmiAdopt.addActionListener(poupHandler);
		jmiRefresh.addActionListener(poupHandler);
		jmiStoreDetail.addActionListener(poupHandler);

		Handler handler = new Handler();
		btnSetting.addActionListener(handler);
		jmiUpdate.addActionListener(handler);
		jmiLocal.addActionListener(handler);
		jmiExit.addActionListener(handler);
	}

	/**
	 * �ڲ����¼��������
	 * 
	 * @author С��
	 *
	 */
	class Handler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if ("ͬ����������".equals(cmd)) {
				if (manageService.syncToServer(pets)) {
					JOptionPane.showMessageDialog(null, "ͬ���ɹ�");
				}
			} else if ("ͬ��������".equals(cmd)) {
				List<Pet> pets = manageService.loadFromServer();
				if (pets != null) {
					MainMenu.this.pets = pets;
					refresh();
					JOptionPane.showMessageDialog(null, "ͬ���ɹ�");
				}
			} else if ("����".equals(cmd)) {
				Initialization.server = txtServer.getText().trim();
				Initialization.updateFreq = Integer.parseInt(txtFreq.getText().trim());
				Initialization.saveProfile();
			} else if ("�˳�".equals(cmd)) {
				manageService.syncToServer(pets);
				dispose();
			}
		}
	}

	/**
	 * �ڲ����¼��������
	 * @author С��
	 *
	 */
	class PopupmenuHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			Pet pet = null;
			if (jtpMain.getSelectedIndex() == 0)
				pet = (Pet) model.getValueAt(tabMyPet.getSelectedRow(), tabMyPet.getColumnCount() - 1);
			else if (jtpMain.getSelectedIndex() == 1)
				pet = (Pet) modelStore.getValueAt(tabStore.getSelectedRow(), tabStore.getColumnCount() - 1);
			if ("�鿴����".equals(cmd)) {
				if (pet instanceof Dog) {
					new EditDog((Dog) pet, EditPet.SHOW);
				} else if (pet instanceof Cat) {
					new EditCat((Cat) pet, EditPet.SHOW);
				}
			} else if ("ι��".equals(cmd)) {
				if (petService.feed(pet))
					refresh();
			} else if ("��ˣ".equals(cmd)) {
				if (petService.play(pet))
					refresh();
			} else if ("����".equals(cmd)) {
				int confirm = JOptionPane.showConfirmDialog(null, "�Ƿ�����ó��", "������ʾ", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					petService.del(pet);
					pets.remove(pet);
					refresh();
					loadServerData();
				}
			} else if ("����".equals(cmd)) {
				if (petService.adopt(pet)) {
					pets.add(pet);
					refresh();
					storePets.remove(pet);
					refreshStore();
					JOptionPane.showMessageDialog(null, "�����ɹ�");
				}
			} else if ("ˢ��".equals(cmd)) {
				loadServerData();
			}
		}
	}

	/**
	 * ��ʼ������
	 * 
	 * @return
	 */
	private void initComponent() {
		this.setTitle("������");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		JMenuBar jmb = new JMenuBar();
		this.setJMenuBar(jmb);
		JMenu jmFile = new JMenu("�ļ�");
		jmb.add(jmFile);
		jmiUpdate = new JMenuItem("ͬ����������");
		jmiLocal = new JMenuItem("ͬ��������");
		jmFile.add(jmiUpdate);
		jmFile.add(jmiLocal);
		jmFile.addSeparator();
		jmiExit = new JMenuItem("�˳�");
		jmFile.add(jmiExit);

		JMenu jmHelp = new JMenu("����");
		jmb.add(jmHelp);
		JMenuItem jmiAbout = new JMenuItem("��������");
		jmHelp.add(jmiAbout);

		jtpMain = new JTabbedPane();
		String[] tabNames = { "�ҵĳ���", "�̵�", "����" };
		// �ҵĳ���
		tabMyPet = new JTable(25, 4);
		JScrollPane jspMyPet = new JScrollPane(tabMyPet);
		model = new SelfTableModel();
		tabMyPet.setModel(model);
		hideLastColumn(tabMyPet);
		jtpMain.add(tabNames[0], jspMyPet);
		// �̵�
		tabStore = new JTable(25, 4);
		JScrollPane jspStore = new JScrollPane(tabStore);
		modelStore = new SelfTableModel();
		tabStore.setModel(modelStore);
		hideLastColumn(tabStore);
		jtpMain.add(tabNames[1], jspStore);
		// ����
		JPanel pnlSetting = new JPanel(null);
		JLabel lblServer = new JLabel("Զ�̷�������");
		lblServer.setSize(80, 30);
		lblServer.setLocation(100, 50);
		pnlSetting.add(lblServer);
		txtServer = new JTextField("http://");
		txtServer.setSize(200, 30);
		txtServer.setLocation(200, 50);
		pnlSetting.add(txtServer);
		JLabel lblFreq = new JLabel("ͬ��������Ƶ�ʣ�ÿ");
		lblFreq.setSize(120, 30);
		lblFreq.setLocation(75, 100);
		pnlSetting.add(lblFreq);
		txtFreq = new JTextField("5");
		txtFreq.setHorizontalAlignment(JTextField.CENTER);
		txtFreq.setSize(50, 30);
		txtFreq.setLocation(200, 100);
		pnlSetting.add(txtFreq);
		JLabel lblTag = new JLabel("����");
		lblTag.setSize(50, 30);
		lblTag.setLocation(260, 100);
		pnlSetting.add(lblTag);
		btnSetting = new JButton("����");
		btnSetting.setSize(80, 30);
		btnSetting.setLocation(200, 150);
		pnlSetting.add(btnSetting);
		jtpMain.add(tabNames[2], pnlSetting);
		this.add(jtpMain);

		// �Ҽ��˵�
		jpmContextMenu = new JPopupMenu();
		jmiMyDetail = new JMenuItem("�鿴����");
		jmiFeed = new JMenuItem("ι��");
		jmiPlay = new JMenuItem("��ˣ");
		jmiDelete = new JMenuItem("����");
		jpmContextMenu.add(jmiMyDetail);
		jpmContextMenu.add(jmiFeed);
		jpmContextMenu.add(jmiPlay);
		jpmContextMenu.add(jmiDelete);

		popupMenu = new JPopupMenu();
		jmiRefresh = new JMenuItem("ˢ��");
		jmiStoreDetail = new JMenuItem("�鿴����");
		jmiAdopt = new JMenuItem("����");
		popupMenu.add(jmiRefresh);
		popupMenu.add(jmiStoreDetail);
		popupMenu.add(jmiAdopt);
	}

	/**
	 * ��ʱˢ������
	 */
	private void refreshInTime() {
		// ˢ�½��������ܶ�����
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(Initialization.refreshFreq * 1000 * 60);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					for (Pet pet : pets) {
						if (pet.getHealth() < 0 || pet.getLove() < 0)
							continue;
						pet.setHealth(pet.getHealth() - Initialization.healthMinus);
						pet.setLove(pet.getLove() - Initialization.loveMinus);
					}
					refresh();
				}
			}
		}).start();
		// ͬ��������
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000 * 60 * Initialization.updateFreq);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					manageService.syncToServer(pets);
				}
			}
		}).start();
	}

	/**
	 * ��ʼ�����ر�������
	 */
	private void loadData() {
		if (firstOpen) {
			Initialization.init();
			firstOpen = false;
		}
		pets = petService.findAll();
		for (Pet pet : pets) {
			model.addRow(pet);
		}
		txtServer.setText(String.valueOf(Initialization.server));
		txtFreq.setText(String.valueOf(Initialization.updateFreq));
	}

	/**
	 * ���ط�����δ��������������
	 */
	private void loadServerData() {
		storePets = manageService.loadStorePets();
		for (Pet pet : storePets) {
			modelStore.addRow(pet);
		}
		refreshStore();
	}

	/**
	 * ˢ�±�������
	 */
	private void refresh() {
		int index = tabMyPet.getSelectedRow();
		model.getContent().clear();
		for (Pet pet : pets) {
			model.addRow(pet);
		}
		model.fireTableDataChanged();
		if (index != -1)
			tabMyPet.changeSelection(index, 0, false, false);
	}

	/**
	 * ˢ�·���������
	 */
	private void refreshStore() {
		int index = tabStore.getSelectedRow();
		modelStore.getContent().clear();
		for (Pet pet : storePets) {
			modelStore.addRow(pet);
		}
		modelStore.fireTableDataChanged();
		if (index != -1)
			tabStore.changeSelection(index, 0, false, false);
	}

	/**
	 * ���ر���е���
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

	@Override
	public void dispose() {
		super.dispose();
		System.exit(0);
	}

}
