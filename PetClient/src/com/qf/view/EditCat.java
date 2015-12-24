package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import com.qf.entity.Cat;

/**
 * 编辑宠物猫
 * 
 * @author 小明
 *
 */
public class EditCat extends EditPet {

	private static final long serialVersionUID = 155247939522500972L;
	private JRadioButton rdoFemale;
	private JRadioButton rdoMale;

	public EditCat() {
		this(null, SHOW);
	}

	public EditCat(Cat cat, String status) {
		super(cat, status);
		initComponent();
		registerListener();
	}

	/**
	 * 注册事件监听
	 */
	private void registerListener() {
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SHOW.equals(status)) { // 查看
					dispose();
				} else if (ADD.equals(status)) { // 添加
					Cat cat = new Cat(txtName.getText().trim(), Integer.parseInt(txtHealth.getText()),
							Integer.parseInt(txtLove.getText()),
							rdoMale.isSelected() ? rdoMale.getText() : rdoFemale.getText());
					if (petService.addServerPet(cat)) {
						JOptionPane.showMessageDialog(null, "添加成功");
						dispose();
					}
				} else if (UPDATE.equals(status)) { // 修改
					pet.setName(txtName.getText());
					pet.setHealth(Integer.parseInt(txtHealth.getText()));
					pet.setLove(Integer.parseInt(txtLove.getText()));
					((Cat) pet).setSex(rdoMale.isSelected() ? rdoMale.getText() : rdoFemale.getText());
					if (petService.addServerPet(pet)) {
						JOptionPane.showMessageDialog(null, "修改成功");
						dispose();
					}
				}
			}
		});
	}

	/**
	 * 初始化界面组件
	 */
	private void initComponent() {
		this.setTitle("编辑宠物猫信息");
		this.setSize(300, 260);

		JLabel lblStrain = new JLabel("性别");
		lblStrain.setSize(80, 30);
		lblStrain.setLocation(45, 130);
		this.add(lblStrain);
		rdoMale = new JRadioButton("雄性");
		rdoMale.setSize(60, 30);
		rdoMale.setLocation(100, 130);
		rdoMale.setSelected(true);
		rdoFemale = new JRadioButton("雌性");
		rdoFemale.setSize(60, 30);
		rdoFemale.setLocation(160, 130);
		ButtonGroup bgp = new ButtonGroup();
		bgp.add(rdoMale);
		bgp.add(rdoFemale);

		this.add(rdoMale);
		this.add(rdoFemale);

		super.btnEdit.setLocation(50, 170);
		super.btnCancel.setLocation(150, 170);

		if (pet != null) {
			if ("雄性".equals(((Cat) pet).getSex()))
				rdoMale.setSelected(true);
			else
				rdoFemale.setSelected(true);
		}

		if (SHOW.equals(status)) {
			rdoMale.setEnabled(false);
			rdoFemale.setEnabled(false);
		} else {
			rdoMale.setEnabled(true);
			rdoFemale.setEnabled(true);
		}
	}
}
