package com.qf.service.impl;

import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.qf.dao.PetDao;
import com.qf.dao.impl.PetDaoImpl;
import com.qf.entity.Cat;
import com.qf.entity.Dog;
import com.qf.entity.Pet;
import com.qf.service.PetService;
import com.qf.util.HttpUrlConnectionUtil;
import com.qf.util.Initialization;
import com.qf.util.JdbcUtil;

public class PetServiceImpl implements PetService {

	private PetDao petDao = new PetDaoImpl();

	@Override
	public List<Pet> findAll() {
		return petDao.loadAll();
	}

	@Override
	public boolean feed(Pet pet) {
		int health = pet.getHealth();
		if (health >= 100)
			return false;
		Random random = new Random();
		int rand = random.nextInt(10) + 1;
		if (health + rand >= 100)
			pet.setHealth(100);
		else
			pet.setHealth(health + rand);
		return petDao.update(pet);
	}

	@Override
	public boolean play(Pet pet) {
		int love = pet.getLove();
		if (love >= 100)
			return false;
		Random random = new Random();
		int rand = random.nextInt(10) + 1;
		if (love + rand >= 100)
			pet.setLove(100);
		else
			pet.setLove(love + rand);
		return petDao.update(pet);
	}

	@Override
	public boolean del(Pet pet) {
		Gson gson = new Gson();
		String json = "";
		if (pet instanceof Dog)
			json = "dog-";
		else if (pet instanceof Cat)
			json = "cat-";
		json += gson.toJson(pet) + "-" + Initialization.uid;
		String param = String.format("action=notAdopt&param=%s", json);
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		if ("success".equals(new String(bs))) {
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM ");
			if (pet instanceof Dog)
				sb.append(" dog ");
			else if (pet instanceof Cat)
				sb.append(" cat ");
			sb.append(" WHERE id=").append(pet.getId());
			JdbcUtil.executeUpdate(sb.toString());
			return true;
		}

		return false;
	}

	@Override
	public boolean removeAll() {
		return petDao.deleteAll();
	}

	@Override
	public boolean savePets(List<Pet> pets) {
		return petDao.save(pets);
	}

	@Override
	public boolean adopt(Pet pet) {
		Gson gson = new Gson();
		String json = "";
		if (pet instanceof Dog)
			json = "dog-";
		else if (pet instanceof Cat)
			json = "cat-";
		json += gson.toJson(pet) + "-" + Initialization.uid;
		String param = String.format("action=adopt&param=%s", json);
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		return "success".equals(new String(bs)) && petDao.save(pet);
	}

	@Override
	public boolean updateServerPet(Pet pet) {
		Gson gson = new Gson();
		String json = gson.toJson(pet);
		String param = null;
		if (pet instanceof Dog) {
			param = String.format("action=updateServerPet&param=dogs-%s", json);
		} else if (pet instanceof Cat) {
			param = String.format("action=updateServerPet&param=cats-%s", json);
		}
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		return "success".equals(new String(bs));
	}

	@Override
	public boolean deleteServerPet(Pet pet) {
		Gson gson = new Gson();
		String json = "";
		if (pet instanceof Dog)
			json = "dog-";
		else if (pet instanceof Cat)
			json = "cat-";
		json += gson.toJson(pet);
		String param = String.format("action=deleteServerPet&param=%s", json);
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		return "success".equals(new String(bs));
	}

	@Override
	public boolean addServerPet(Pet pet) {
		Gson gson = new Gson();
		String json = gson.toJson(pet);
		String param = null;
		if (pet instanceof Dog) {
			param = String.format("action=addPet&param=dogs-%s", json);
		} else if (pet instanceof Cat) {
			param = String.format("action=addPet&param=cats-%s", json);
		}
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		return "success".equals(new String(bs));
	}
}
