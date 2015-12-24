package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.qf.entity.Dog;

/**
 * �༭���ﹷ
 * 
 * @author С��
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
	 * ע���¼�����
	 */
	private void registerListener() {
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SHOW.equals(status)) { // �鿴
					dispose();
				} else if (ADD.equals(status)) { // ���
					// �½� Dog ����
					Dog dog = new Dog(txtName.getText().trim(), Integer.parseInt(txtHealth.getText()),
							Integer.parseInt(txtLove.getText()), txtStrain.getText().trim());

					if (petService.addServerPet(dog)) {
						JOptionPane.showMessageDialog(null, "��ӳɹ�");
						dispose();
					}
				} else if (UPDATE.equals(status)) { // �޸�
					pet.setName(txtName.getText());
					pet.setHealth(Integer.parseInt(txtHealth.getText()));
					pet.setLove(Integer.parseInt(txtLove.getText()));
					((Dog) pet).setStrain(txtStrain.getText());
					if (petService.updateServerPet(pet)) {
						JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
						dispose();
					}
				}
			}
		});
	}

	/**
	 * ��ʼ���������
	 */
	private void initComponent() {
		this.setTitle("�༭���ﹷ��Ϣ");
		this.setSize(300, 260);

		JLabel lblStrain = new JLabel("Ʒ��");
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
