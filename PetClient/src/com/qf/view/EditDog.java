package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.qf.entity.Dog;

/**
 * 编辑宠物狗
 * 
 * @author 小明
 *
 */
public class EditDog extends EditPet {

	private static final long serialVersionUID = 155247939522500972L;
	private JTextField txtStrain;

	public EditDog() {
		this(null, SHOW);
	}

	public EditDog(Dog dog, String status) {
		super(dog, status);
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
					// 新建 Dog 对象
					Dog dog = new Dog(txtName.getText().trim(), Integer.parseInt(txtHealth.getText()),
							Integer.parseInt(txtLove.getText()), txtStrain.getText().trim());

					if (petService.addServerPet(dog)) {
						JOptionPane.showMessageDialog(null, "添加成功");
						dispose();
					}
				} else if (UPDATE.equals(status)) { // 修改
					pet.setName(txtName.getText());
					pet.setHealth(Integer.parseInt(txtHealth.getText()));
					pet.setLove(Integer.parseInt(txtLove.getText()));
					((Dog) pet).setStrain(txtStrain.getText());
					if (petService.updateServerPet(pet)) {
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
		this.setTitle("编辑宠物狗信息");
		this.setSize(300, 260);

		JLabel lblStrain = new JLabel("品种");
		lblStrain.setSize(80, 30);
		lblStrain.setLocation(45, 130);
		this.add(lblStrain);
		txtStrain = new JTextField();
		txtStrain.setSize(150, 20);
		txtStrain.setLocation(100, 135);
		this.add(txtStrain);

		super.btnEdit.setLocation(50, 170);
		super.btnCancel.setLocation(150, 170);

		if (pet != null) {
			txtStrain.setText(((Dog) pet).getStrain());
		}
		if (SHOW.equals(status)) {
			txtStrain.setEditable(false);
		} else {
			txtStrain.setEditable(true);
		}
	}
}
