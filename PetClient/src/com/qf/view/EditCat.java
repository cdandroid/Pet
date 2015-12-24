package com.qf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import com.qf.entity.Cat;

/**
 * �༭����è
 * 
 * @author С��
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
	 * ע���¼�����
	 */
	private void registerListener() {
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SHOW.equals(status)) { // �鿴
					dispose();
				} else if (ADD.equals(status)) { // ���
					Cat cat = new Cat(txtName.getText().trim(), Integer.parseInt(txtHealth.getText()),
							Integer.parseInt(txtLove.getText()),
							rdoMale.isSelected() ? rdoMale.getText() : rdoFemale.getText());
					if (petService.addServerPet(cat)) {
						JOptionPane.showMessageDialog(null, "��ӳɹ�");
						dispose();
					}
				} else if (UPDATE.equals(status)) { // �޸�
					pet.setName(txtName.getText());
					pet.setHealth(Integer.parseInt(txtHealth.getText()));
					pet.setLove(Integer.parseInt(txtLove.getText()));
					((Cat) pet).setSex(rdoMale.isSelected() ? rdoMale.getText() : rdoFemale.getText());
					if (petService.addServerPet(pet)) {
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
		this.setTitle("�༭����è��Ϣ");
		this.setSize(300, 260);

		JLabel lblStrain = new JLabel("�Ա�");
		lblStrain.setSize(80, 30);
		lblStrain.setLocation(45, 130);
		this.add(lblStrain);
		rdoMale = new JRadioButton("����");
		rdoMale.setSize(60, 30);
		rdoMale.setLocation(100, 130);
		rdoMale.setSelected(true);
		rdoFemale = new JRadioButton("����");
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
			if ("����".equals(((Cat) pet).getSex()))
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
