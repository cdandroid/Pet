package com.qf.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qf.dao.AdminDao;
import com.qf.dao.PetDao;
import com.qf.dao.UserDao;
import com.qf.dao.impl.AdminDaoImpl;
import com.qf.dao.impl.PetDaoImpl;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.entity.Administrator;
import com.qf.entity.Cat;
import com.qf.entity.Dog;
import com.qf.entity.Pet;
import com.qf.entity.User;
import com.qf.util.Initialization;

/**
 * 管理的 Servlet 类，用于处理客户端请求
 * 
 * @author 小明
 *
 */
@WebServlet("/manage")
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminDao adminDao = new AdminDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private PetDao petDao = new PetDaoImpl();

	public ManagerServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		Initialization.initDB();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=gbk");
		// 获取操作的动作
		String action = request.getParameter("action");
		String msg = null;
		Gson gson = new Gson();
		if ("loginAdmin".equals(action)) { // 管理员登录
			String param = request.getParameter("param");
			Administrator admin = gson.fromJson(param, Administrator.class);
			admin = adminDao.get(admin);
			msg = admin != null ? "success" : "failed";
		} else if ("loginUser".equals(action)) { // 普通用户登录
			String param = request.getParameter("param");
			User user = gson.fromJson(param, User.class);
			user = userDao.get(user);
			msg = user != null ? "success" + "-" + user.getId() : "failed";
		} else if ("syncToServer".equals(action)) { // 用户同步数据到服务器
			msg = syncToServer(request);
		} else if ("loadFromServer".equals(action)) { // 用户从服务器同步数据到本地
			msg = loadFromServer(request);
		} else if ("loadStorePets".equals(action)) { // 加载服务器端未被领养宠物
			msg = loadStorePets(request);
		} else if ("addPet".equals(action)) { // 向服务器添加宠物信息
			msg = addPet(request);
		} else if ("adopt".equals(action)) { // 领养宠物
			msg = adopt(request);
		} else if ("notAdopt".equals(action)) { // 弃养宠物
			msg = notAdopt(request);
		} else if ("loadAllStorePets".equals(action)) { // 加载所有宠物
			msg = loadAllStorePets(request);
		} else if ("updateServerPet".equals(action)) { // 更新服务器宠物
			msg = updateServerPet(request);
		} else if ("deleteServerPet".equals(action)) { // 删除服务器宠物
			msg = deleteServerPet(request);
		}

		response.getWriter().append(msg);
	}

	private String deleteServerPet(HttpServletRequest request) {
		Gson gson = new Gson();
		String[] values = request.getParameter("param").split("-");
		Pet pet = null;
		if ("dog".equals(values[0]))
			pet = gson.fromJson(values[1], Dog.class);
		else if ("cat".equals(values[0]))
			pet = gson.fromJson(values[1], Cat.class);

		if (petDao.delete(pet))
			return "success";
		return "failed";
	}

	private String updateServerPet(HttpServletRequest request) {
		Gson gson = new Gson();
		String[] values = request.getParameter("param").split("-");
		Pet pet = null;
		if ("dogs".equals(values[0]))
			pet = gson.fromJson(values[1], Dog.class);
		else if ("cats".equals(values[0]))
			pet = gson.fromJson(values[1], Cat.class);
		if (petDao.update(pet))
			return "success";
		return "failed";
	}

	private String loadAllStorePets(HttpServletRequest request) {
		List<Pet> notAdopted = petDao.getAll();
		Gson gson = new Gson();
		List<Dog> dogs = new ArrayList<>();
		List<Cat> cats = new ArrayList<>();
		for (Pet pet : notAdopted) {
			if (pet instanceof Dog)
				dogs.add((Dog) pet);
			else if (pet instanceof Cat)
				cats.add((Cat) pet);
		}
		String dogJson = "dogs#" + gson.toJson(dogs);
		String catJson = "cats#" + gson.toJson(cats);

		return "success-" + dogJson + "-" + catJson;
	}

	private String notAdopt(HttpServletRequest request) {
		Gson gson = new Gson();
		String[] param = request.getParameter("param").split("-");
		Pet pet = null;
		if ("dog".equals(param[0]))
			pet = gson.fromJson(param[1], Dog.class);
		else if ("cat".equals(param[0]))
			pet = gson.fromJson(param[1], Cat.class);
		pet.setUid(0);

		if (petDao.update(pet))
			return "success";
		return "failed";
	}

	private String adopt(HttpServletRequest request) {
		Gson gson = new Gson();
		String[] param = request.getParameter("param").split("-");
		Pet pet = null;
		if ("dog".equals(param[0]))
			pet = gson.fromJson(param[1], Dog.class);
		else if ("cat".equals(param[0]))
			pet = gson.fromJson(param[1], Cat.class);
		pet.setUid(Integer.parseInt(param[2]));

		if (petDao.update(pet)) {
			return "success";
		}
		return "failed";
	}

	private String loadStorePets(HttpServletRequest request) {
		List<Pet> notAdopted = petDao.getNotAdopted();
		Gson gson = new Gson();
		List<Dog> dogs = new ArrayList<>();
		List<Cat> cats = new ArrayList<>();
		for (Pet pet : notAdopted) {
			if (pet instanceof Dog)
				dogs.add((Dog) pet);
			else if (pet instanceof Cat)
				cats.add((Cat) pet);
		}
		String dogJson = "dogs#" + gson.toJson(dogs);
		String catJson = "cats#" + gson.toJson(cats);

		return "success-" + dogJson + "-" + catJson;
	}

	private String loadFromServer(HttpServletRequest request) {
		String param = request.getParameter("param");
		Gson gson = new Gson();
		List<Pet> pets = petDao.getByUid(Integer.parseInt(param));
		List<Dog> dogs = new ArrayList<>();
		List<Cat> cats = new ArrayList<>();
		for (Pet pet : pets) {
			if (pet instanceof Dog)
				dogs.add((Dog) pet);
			else if (pet instanceof Cat)
				cats.add((Cat) pet);
		}
		String dogJson = "dogs#" + gson.toJson(dogs);
		String catJson = "cats#" + gson.toJson(cats);
		return "success-" + dogJson + "-" + catJson;
	}

	private String addPet(HttpServletRequest request) {
		Gson gson = new Gson();
		String[] values = request.getParameter("param").split("-");
		Pet pet = null;
		if ("dogs".equals(values[0]))
			pet = gson.fromJson(values[1], Dog.class);
		else if ("cats".equals(values[0]))
			pet = gson.fromJson(values[1], Cat.class);
		if (petDao.save(pet))
			return "success";
		return "failed";
	}

	private String syncToServer(HttpServletRequest request) {
		Gson gson = new Gson();
		String[] values = request.getParameterValues("param");
		List<Pet> pets = new ArrayList<>();
		for (String value : values) {
			String type = value.substring(0, value.indexOf("-"));
			value = value.substring(value.indexOf("-") + 1);
			if ("dogs".equals(type)) {
				List<Dog> dogs = gson.fromJson(value, new TypeToken<List<Dog>>() {
				}.getType());
				pets.addAll(dogs);
			} else if ("cats".equals(type)) {
				List<Cat> cats = gson.fromJson(value, new TypeToken<List<Cat>>() {
				}.getType());
				pets.addAll(cats);
			}
		}
		if (petDao.update(pets))
			return "success";
		return "failed";
	}

}
