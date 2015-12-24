package com.qf.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qf.dao.PetDao;
import com.qf.entity.Cat;
import com.qf.entity.Dog;
import com.qf.entity.Pet;
import com.qf.util.JdbcUtil;

public class PetDaoImpl implements PetDao {

	@Override
	public List<Pet> getAll() {
		String sql = "SELECT id, name, health, love, strain, sex, uid FROM pet";
		return getPets(sql);
	}

	@Override
	public boolean update(Pet pet) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE pet ");
		sb.append(" SET name='").append(pet.getName()).append("', health=").append(pet.getHealth()).append(", love=")
				.append(pet.getLove());
		if (pet instanceof Dog)
			sb.append(", strain='").append(((Dog) pet).getStrain()).append("'");
		else if (pet instanceof Cat)
			sb.append(", sex='").append(((Cat) pet).getSex()).append("'");
		sb.append(", uid=").append(pet.getUid() == 0 ? "NULL" : pet.getUid());
		sb.append(" WHERE id=").append(pet.getId());

		return JdbcUtil.executeUpdate(sb.toString()) == 1;
	}

	@Override
	public boolean delete(Pet pet) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM pet WHERE id=").append(pet.getId());

		return JdbcUtil.executeUpdate(sb.toString()) == 1;
	}

	@Override
	public boolean save(Pet pet) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO pet (name, health, love");
		if (pet instanceof Dog)
			sb.append(", strain");
		else if (pet instanceof Cat)
			sb.append(", sex");
		sb.append(") VALUES ('").append(pet.getName()).append("', ").append(pet.getHealth()).append(",")
				.append(pet.getLove());
		if (pet instanceof Dog)
			sb.append(", '").append(((Dog) pet).getStrain()).append("'");
		else if (pet instanceof Cat)
			sb.append(", '").append(((Cat) pet).getSex()).append("'");
		sb.append(")");

		return JdbcUtil.executeUpdate(sb.toString()) == 1;
	}

	@Override
	public boolean save(List<Pet> pets) {
		Connection conn = JdbcUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			StringBuilder sb = new StringBuilder();
			for (Pet pet : pets) {
				sb.setLength(0);
				sb.append("INSERT INTO pet (id, name, health, love");
				if (pet instanceof Dog)
					sb.append(", strain");
				else if (pet instanceof Cat)
					sb.append(", sex");
				sb.append(") VALUES (").append(pet.getId()).append(", '").append(pet.getName()).append("', ")
						.append(pet.getHealth()).append(",").append(pet.getLove());
				if (pet instanceof Dog)
					sb.append(", '").append(((Dog) pet).getStrain()).append("'");
				else if (pet instanceof Cat)
					sb.append(", '").append(((Cat) pet).getSex()).append("'");
				sb.append(")");

				stmt.addBatch(sb.toString());
			}

			stmt.executeBatch();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public boolean update(List<Pet> pets) {
		Connection conn = JdbcUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			StringBuilder sb = new StringBuilder();
			for (Pet pet : pets) {
				sb.setLength(0);
				sb.append("UPDATE pet SET name='").append(pet.getName()).append("', health=").append(pet.getHealth())
						.append(", love=").append(pet.getLove());
				if (pet instanceof Dog)
					sb.append(", strain='").append(((Dog) pet).getStrain()).append("'");
				else if (pet instanceof Cat)
					sb.append(", sex='").append(((Cat) pet).getSex()).append("'");
				sb.append(", uid=").append(pet.getUid() == 0 ? "NULL" : pet.getUid()).append(" WHERE id=")
						.append(pet.getId());
				stmt.addBatch(sb.toString());
			}

			stmt.executeBatch();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public List<Pet> getNotAdopted() {
		String sql = "SELECT id, name, health, love, strain, sex, uid FROM pet WHERE uid IS NULL";
		return getPets(sql);
	}

	private List<Pet> getPets(String sql) {
		List<Pet> pets = new ArrayList<Pet>();
		ResultSet rs = JdbcUtil.executeQuery(sql);
		try {
			Pet pet = null;
			while (rs.next()) {
				String strain = rs.getString("strain");
				if (!rs.wasNull()) {
					pet = new Dog();
					((Dog) pet).setStrain(strain);
				}
				String sex = rs.getString("sex");
				if (!rs.wasNull()) {
					pet = new Cat();
					((Cat) pet).setSex(sex);
				}
				pet.setId(rs.getInt("id"));
				pet.setName(rs.getString("name"));
				pet.setHealth(rs.getInt("health"));
				pet.setLove(rs.getInt("love"));
				pet.setUid(rs.getInt("uid"));
				pets.add(pet);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close();
		}
		return pets;
	}

	@Override
	public List<Pet> getByUid(int uid) {
		String sql = "SELECT id, name, health, love, strain, sex, uid FROM pet WHERE uid=" + uid;
		return getPets(sql);
	}
}
