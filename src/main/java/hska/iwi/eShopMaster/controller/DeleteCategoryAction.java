package hska.iwi.eShopMaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1254575994729199914L;
	
	private int catId;
	private List<Category> categories;

	public String execute() throws Exception {
		
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			//call microservice
			deleteCategory(catId);
			getCategoriesFromMicroservice();

			res = "success";

		}
		
		return res;
		
	}

	public void deleteCategory(int id){

		JsonNode allCategories = RestHelper.deleteCall("Category/" + id,"category");


	}
	public void getCategoriesFromMicroservice(){
		JsonNode allCategories = RestHelper.getCall("Category-getall","category");

		List<Category> categories = new ArrayList<Category>();

		for (JsonNode category : allCategories) {
			int id = category.get("id").asInt();
			String name = category.get("name").asText();


			Category newOne = new Category(name, id);
			categories.add(newOne);
		}
		this.categories = categories;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
