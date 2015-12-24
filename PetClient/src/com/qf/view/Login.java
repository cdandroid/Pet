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
 * 登录界面
 * 
 * @author 小明
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
	 * 初始化界面
	 */
	private void initComponent() {
		this.setTitle("登录");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);

		/* 用户名 */
		JLabel lblUname = new JLabel("用户名：");
		lblUname.setSize(80, 30);
		lblUname.setLocation(30, 10);
		this.add(lblUname);

		txtUname = new JTextField();
		txtUname.setSize(150, 30);
		txtUname.setLocation(100, 10);
		this.add(txtUname);

		/* 密码 */
		JLabel lblUpwd = new JLabel("密码：");
		lblUpwd.setSize(80, 30);
		lblUpwd.setLocation(43, 50);
		this.add(lblUpwd);

		txtUpwd = new JPasswordField();
		txtUpwd.setSize(150, 30);
		txtUpwd.setLocation(100, 50);
		this.add(txtUpwd);

		/* 类型 */
		ButtonGroup bgp = new ButtonGroup();
		rdoCommon = new JRadioButton("普通用户");
		rdoCommon.setSelected(true);
		rdoCommon.setSize(80, 30);
		rdoCommon.setLocation(60, 90);
		this.add(rdoCommon);

		rdoManager = new JRadioButton("管理员");
		rdoManager.setSize(80, 30);
		rdoManager.setLocation(170, 90);
		this.add(rdoManager);

		bgp.add(rdoCommon);
		bgp.add(rdoManager);

		/* 登录，取消按钮 */
		btnLogin = new JButton("登录");
		btnLogin.setSize(80, 30);
		btnLogin.setLocation(60, 130);
		this.add(btnLogin);

		btnCancel = new JButton("取消");
		btnCancel.setSize(80, 30);
		btnCancel.setLocation(160, 130);
		this.add(btnCancel);
	}

	/**
	 * 注册事件监听
	 */
	private void registerListener() {
		// 登录
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
					JOptionPane.showMessageDialog(null, "用户名或密码错误");
			}
		});

		// 取消
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
