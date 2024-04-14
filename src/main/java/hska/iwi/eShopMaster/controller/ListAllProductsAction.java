package hska.iwi.eShopMaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ListAllProductsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -94109228677381902L;
	
	User user;
	private List<Product> products;
	
	public String execute() throws Exception{
		String result = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		
		if(user != null){
			System.out.println("list all products!");
			//ProductManager productManager = new ProductManagerImpl();
			//this.products = productManager.getProducts();
			getFromMicroservice();
			result = "success";
		}
		
		return result;
	}

	public void getFromMicroservice(){
		JsonNode allProducts = RestHelper.getCall("Product","product");

		List<Product> products = new ArrayList<Product>();

		for (JsonNode product : allProducts) {
			int id = product.get("id").asInt();
			String name = product.get("name").asText();
			int productCategoryId = product.get("categoryId").asInt();

			JsonNode catResult = RestHelper.getCall("Category/" + productCategoryId, "category");

			String categoryName = catResult.get("name").asText();
			int categoryId =  catResult.get("id").asInt();

			Category category = new Category(categoryName,categoryId);

			double price = product.get("price").asDouble();
			String details =  product.get("details").asText();


			Product newOne = new Product(id,name,price,category,details);
			products.add(newOne);
		}
		this.products = products;
	}



	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
