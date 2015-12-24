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
import com.qf.util.Initialization;
import com.qf.util.JdbcUtil;

/**
 * 宠物 DAO 实现类
 * 
 * @author 小明
 *
 */
public class PetDaoImpl implements PetDao {

	@Override
	public List<Pet> loadAll() {
		List<Pet> pets = new ArrayList<Pet>();
		String sql = "SELECT id, name, health, love, strain, NULL as sex FROM dog "
				+ "UNION SELECT id, name, health, love, NULL as strain, sex FROM cat";
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
				pet.setUid(Initialization.uid);

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
	public boolean update(Pet pet) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		if (pet instanceof Dog)
			sb.append(" dog ");
		else if (pet instanceof Cat)
			sb.append(" cat ");
		sb.append(" SET health=").append(pet.getHealth()).append(", love=").append(pet.getLove());
		if (pet instanceof Dog)
			sb.append(", strain='").append(((Dog) pet).getStrain()).append("'");
		else if (pet instanceof Cat)
			sb.append(", sex='").append(((Cat) pet).getSex()).append("'");
		sb.append(" WHERE id=").append(pet.getId());

		return JdbcUtil.executeUpdate(sb.toString()) == 1;
	}

	@Override
	public boolean delete(Pet pet) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		if (pet instanceof Dog)
			sb.append(" dog ");
		else if (pet instanceof Cat)
			sb.append(" cat ");
		sb.append(" WHERE id=").append(pet.getId());

		return false;
	}

	@Override
	public boolean deleteAll() {
		String sql = "DELETE FROM dog";
		JdbcUtil.executeUpdate(sql);
		sql = "DELETE FROM cat";
		JdbcUtil.executeUpdate(sql);

		return true;
	}

	@Override
	public boolean save(List<Pet> pets) {
		Connection conn = JdbcUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			for (Pet pet : pets) {
				stmt.addBatch(insertSql(pet));
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
	public boolean save(Pet pet) {
		return JdbcUtil.executeUpdate(insertSql(pet)) == 1;
	}

	/**
	 * 根据宠物信息生成插入SQL语句
	 * 
	 * @param pet
	 *            宠物
	 * @return SQL语句字符串
	 */
	private String insertSql(Pet pet) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		if (pet instanceof Dog)
			sb.append(" dog ");
		else if (pet instanceof Cat)
			sb.append(" cat ");
		sb.append(" (id, name, health, love");
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
		return sb.toString();
	}
}
