package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.qf.entity.Pet;
import com.qf.service.PetService;
import com.qf.service.impl.PetServiceImpl;

/**
 * �༭����
 * 
 * @author С��
 *
 */
public class EditPet extends JFrame {
	private static final long serialVersionUID = -7472602631325561820L;

	public static final String UPDATE = "update";
	public static final String ADD = "add";
	public static final String SHOW = "show";

	protected Manager manager;
	protected PetService petService = new PetServiceImpl();
	protected Pet pet;
	protected JButton btnEdit;
	protected JButton btnCancel;
	protected JTextField txtId;
	protected JTextField txtName;
	protected JTextField txtHealth;
	protected JTextField txtLove;
	protected String status;

	public EditPet() {
		this(null, SHOW);
	}

	public EditPet(Pet pet, String status) {
		this.pet = pet;
		this.status = status;

		initComponent();
		registerListener();
		this.setVisible(true);
	}

	/**
	 * ע���¼�����
	 */
	private void registerListener() {
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * ��ʼ�����������Ϣ
	 */
	private void initComponent() {
		this.setTitle("�༭������Ϣ");
		this.setSize(300, 220);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.setLayout(null);

		JLabel lblId = new JLabel("���");
		lblId.setSize(80, 30);
		lblId.setLocation(45, 10);
		txtId = new JTextField();
		txtId.setSize(150, 20);
		txtId.setLocation(100, 15);
		txtId.setEditable(false);
		JLabel lblName = new JLabel("�ǳ�");
		lblName.setSize(80, 30);
		lblName.setLocation(45, 40);
		txtName = new JTextField();
		txtName.setSize(150, 20);
		txtName.setLocation(100, 45);
		JLabel lblHealth = new JLabel("����ֵ");
		lblHealth.setSize(80, 30);
		lblHealth.setLocation(30, 70);
		txtHealth = new JTextField();
		txtHealth.setSize(150, 20);
		txtHealth.setLocation(100, 75);
		JLabel lblLove = new JLabel("���ܶ�");
		lblLove.setSize(80, 30);
		lblLove.setLocation(30, 100);
		txtLove = new JTextField();
		txtLove.setSize(150, 20);
		txtLove.setLocation(100, 105);

		btnEdit = new JButton("����");
		btnEdit.setSize(80, 30);
		btnEdit.setLocation(50, 135);
		btnCancel = new JButton("ȡ��");
		btnCancel.setSize(80, 30);
		btnCancel.setLocation(150, 135);

		this.add(lblId);
		this.add(txtId);
		this.add(lblName);
		this.add(txtName);
		this.add(lblHealth);
		this.add(txtHealth);
		this.add(lblLove);
		this.add(txtLove);
		this.add(btnEdit);
		this.add(btnCancel);

		if (pet != null) {
			txtId.setText(String.valueOf(pet.getId()));
			txtName.setText(pet.getName());
			txtHealth.setText(String.valueOf(pet.getHealth()));
			txtLove.setText(String.valueOf(pet.getLove()));
		}

		if (SHOW.equals(status)) {
			txtName.setEditable(false);
			txtHealth.setEditable(false);
			txtLove.setEditable(false);
			btnEdit.setText("ȷ��");
		} else {
			txtName.setEditable(true);
			txtHealth.setEditable(true);
			txtLove.setEditable(true);
			if (UPDATE.equals(status)) {
				btnEdit.setText("�޸�");
			} else if (ADD.equals(status)) {
				btnEdit.setText("���");
			}
		}
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (!SHOW.equals(status))
			this.manager.refreshStore();
	}
}
