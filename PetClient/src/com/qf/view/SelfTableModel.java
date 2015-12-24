package com.qf.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.qf.entity.Pet;

public class SelfTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 3015079764752663840L;
	private List<List<?>> content;
	private String[] title = { "ÐòºÅ", "êÇ³Æ", "½¡¿µÖµ", "Ç×ÃÜ¶È", "type" };

	public SelfTableModel() {
		super();
		this.content = new ArrayList<>();
	}

	public SelfTableModel(List<List<?>> content) {
		super();
		this.content = content;
	}

	public List<List<?>> getContent() {
		return content;
	}

	@Override
	public int getRowCount() {
		return content.size();
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public String getColumnName(int column) {
		return this.title[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return content.get(rowIndex).get(columnIndex);
	}

	public void addRow(Pet pet) {
		List<Object> list = new ArrayList<>();
		list.add(getRowCount() + 1);
		list.add(pet.getName());
		list.add(pet.getHealth());
		list.add(pet.getLove());
		list.add(pet);

		content.add(list);
	}

	public void removeRow(int rowIndex) {
		content.remove(rowIndex);
	}
}