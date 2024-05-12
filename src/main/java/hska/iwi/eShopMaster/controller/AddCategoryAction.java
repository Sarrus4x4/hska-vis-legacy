package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import com.fasterxml.jackson.databind.JsonNode;


public class AddCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6704600867133294378L;
	
	private String newCatName = null;
	
	private List<Category> categories;
	
	User user;

	public String execute() throws Exception {

		JsonNode test = RestHelper.getCall("/Category/1","category");

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			//call microservice
			addCategory(newCatName);
			getCategoriesFromMicroservice();

			res = "success";
		}
		
		return res;
	
	}

	public void addCategory(String name){
		JsonNode allCategories = RestHelper.postCall("Category","category",null, "?name=" + name );
	}

	public void getCategoriesFromMicroservice(){
		JsonNode allCategories = RestHelper.getCall("Category","category");

		List<Category> categories = new ArrayList<Category>();

		for (JsonNode category : allCategories) {
			int id = category.get("id").asInt();
			String name = category.get("name").asText();

			Category newOne = new Category(name, id);
			categories.add(newOne);
		}
		this.categories = categories;
	}


	@Override
	public void validate(){
		if (getNewCatName().length() == 0) {
			addActionError(getText("error.catname.required"));
		}
		// Go and get new Category list
		CategoryManager categoryManager = new CategoryManagerImpl();
		this.setCategories(categoryManager.getCategories());
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public String getNewCatName() {
		return newCatName;
	}

	public void setNewCatName(String newCatName) {
		this.newCatName = newCatName;
	}
}
