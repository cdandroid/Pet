package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.qf.service.ManageService;
import com.qf.service.impl.ManageServiceImpl;

/**
 * ��¼����
 * 
 * @author С��
 *
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 5788126231517623500L;
	ManageService manageService = new ManageServiceImpl();
	private JTextField txtUname;
	private JPasswordField txtUpwd;
	private JRadioButton rdoCommon;
	private JRadioButton rdoManager;
	private JButton btnLogin;
	private JButton btnCancel;

	public Login() {
		initComponent();
		registerListener();
		this.setVisible(true);
	}

	/**
	 * ��ʼ������
	 */
	private void initComponent() {
		this.setTitle("��¼");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);

		/* �û��� */
		JLabel lblUname = new JLabel("�û�����");
		lblUname.setSize(80, 30);
		lblUname.setLocation(30, 10);
		this.add(lblUname);

		txtUname = new JTextField();
		txtUname.setSize(150, 30);
		txtUname.setLocation(100, 10);
		this.add(txtUname);

		/* ���� */
		JLabel lblUpwd = new JLabel("���룺");
		lblUpwd.setSize(80, 30);
		lblUpwd.setLocation(43, 50);
		this.add(lblUpwd);

		txtUpwd = new JPasswordField();
		txtUpwd.setSize(150, 30);
		txtUpwd.setLocation(100, 50);
		this.add(txtUpwd);

		/* ���� */
		ButtonGroup bgp = new ButtonGroup();
		rdoCommon = new JRadioButton("��ͨ�û�");
		rdoCommon.setSelected(true);
		rdoCommon.setSize(80, 30);
		rdoCommon.setLocation(60, 90);
		this.add(rdoCommon);

		rdoManager = new JRadioButton("����Ա");
		rdoManager.setSize(80, 30);
		rdoManager.setLocation(170, 90);
		this.add(rdoManager);

		bgp.add(rdoCommon);
		bgp.add(rdoManager);

		/* ��¼��ȡ����ť */
		btnLogin = new JButton("��¼");
		btnLogin.setSize(80, 30);
		btnLogin.setLocation(60, 130);
		this.add(btnLogin);

		btnCancel = new JButton("ȡ��");
		btnCancel.setSize(80, 30);
		btnCancel.setLocation(160, 130);
		this.add(btnCancel);
	}

	/**
	 * ע���¼�����
	 */
	private void registerListener() {
		// ��¼
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String uname = txtUname.getText().trim();
				String upwd = new String(txtUpwd.getPassword());
				boolean isLogin = manageService.login(uname, upwd, rdoCommon.isSelected());
				if (isLogin) {
					if (rdoCommon.isSelected())
						new MainMenu();
					else
						new Manager();
					Login.this.setVisible(false);
				} else
					JOptionPane.showMessageDialog(null, "�û������������");
			}
		});

		// ȡ��
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new Login();
	}
}
