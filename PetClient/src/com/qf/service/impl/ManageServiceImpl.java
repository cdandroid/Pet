package com.qf.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qf.entity.Cat;
import com.qf.entity.Dog;
import com.qf.entity.Pet;
import com.qf.service.ManageService;
import com.qf.service.PetService;
import com.qf.util.HttpUrlConnectionUtil;
import com.qf.util.Initialization;

/**
 * 服务接口实现类
 * 
 * @author 小明
 *
 */
public class ManageServiceImpl implements ManageService {
	private PetService petService = new PetServiceImpl();

	@Override
	public List<Pet> loadFromServer() {
		String param = String.format("action=loadFromServer&param=%d", Initialization.uid);
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		String[] msgs = new String(bs).split("-");
		if (!"success".equals(msgs[0]))
			return null;
		Gson gson = new Gson();
		List<Pet> pets = new ArrayList<>();
		for (int i = 1, len = msgs.length; i < len; i++) {
			String[] split = msgs[i].split("#");
			if ("dogs".equals(split[0])) {
				pets.addAll(gson.fromJson(split[1], new TypeToken<List<Dog>>() {
				}.getType()));
			} else if ("cats".equals(split[0])) {
				pets.addAll(gson.fromJson(split[1], new TypeToken<List<Cat>>() {
				}.getType()));
			}
		}

		petService.removeAll(); // 同步先删除本地数据
		petService.savePets(pets); // 再向本地保存同步后的数据

		return pets;
	}

	@Override
	public List<Pet> loadStorePets() {
		String param = "action=loadStorePets";
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		String[] msgs = new String(bs).split("-");
		if (!"success".equals(msgs[0]))
			return null;
		Gson gson = new Gson();
		// 解析获取到的宠物JSON格式数据
		List<Pet> pets = new ArrayList<>();
		for (int i = 1, len = msgs.length; i < len; i++) {
			String[] split = msgs[i].split("#");
			if ("dogs".equals(split[0])) {
				pets.addAll(gson.fromJson(split[1], new TypeToken<List<Dog>>() {
				}.getType()));
			} else if ("cats".equals(split[0])) {
				pets.addAll(gson.fromJson(split[1], new TypeToken<List<Cat>>() {
				}.getType()));
			}
		}
		return pets;
	}

	@Override
	public List<Pet> loadAllStorePets() {
		String param = "action=loadAllStorePets";
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		String[] msgs = new String(bs).split("-");
		if (!"success".equals(msgs[0]))
			return null;
		Gson gson = new Gson();
		List<Pet> pets = new ArrayList<>();
		for (int i = 1, len = msgs.length; i < len; i++) {
			String[] split = msgs[i].split("#");
			if ("dogs".equals(split[0])) {
				pets.addAll(gson.fromJson(split[1], new TypeToken<List<Dog>>() {
				}.getType()));
			} else if ("cats".equals(split[0])) {
				pets.addAll(gson.fromJson(split[1], new TypeToken<List<Cat>>() {
				}.getType()));
			}
		}
		return pets;
	}

	@Override
	public boolean login(String username, String password, boolean rdoCommon) {
		String param = String.format("param={'username':%s,'password':%s}", username, password);
		param += rdoCommon ? "&action=loginUser" : "&action=loginAdmin";
		
		byte[] b = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		String[] msgs = new String(b).split("-");
		if ("success".equals(msgs[0])) {
			if (rdoCommon)
				Initialization.uid = Integer.parseInt(msgs[1]);
			return true;
		}
		return false;
	}

	@Override
	public boolean syncToServer(List<Pet> pets) {
		List<Dog> dogs = new ArrayList<Dog>();
		List<Cat> cats = new ArrayList<Cat>();
		for (Pet pet : pets) {
			if (pet instanceof Dog)
				dogs.add((Dog) pet);
			else if (pet instanceof Cat)
				cats.add((Cat) pet);
		}
		Gson gson = new Gson();
		String dogJson = "dogs-" + gson.toJson(dogs);
		String catJson = "cats-" + gson.toJson(cats);
		String param = String.format("action=syncToServer&param=%s&param=%s", dogJson, catJson);
		byte[] bs = HttpUrlConnectionUtil.connect(Initialization.server + "/manage", param);
		return "success".equals(new String(bs));
	}

}
