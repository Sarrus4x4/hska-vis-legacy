package hska.iwi.eShopMaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport {
	
	private User user;
	private int id;
	private String searchValue;
	private Integer searchMinPrice;
	private Integer searchMaxPrice;
	private Product product;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7708747680872125699L;

	public String execute() throws Exception {

		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		
		if(user != null) {

			product = new Product();
			JsonNode productRes = RestHelper.getCall("Product/" + id,"product");

			if(productRes != null && !productRes.isNull()) {

				int id = productRes.get("id").asInt();
				String name = productRes.get("name").asText();
				int productCategoryId = productRes.get("categoryId").asInt();
				double price = productRes.get("price").asDouble();
				String details =  productRes.get("details").asText();

				JsonNode catResult = RestHelper.getCall("Category/" + productCategoryId, "category");

				if(catResult != null && !catResult.isNull()) {
					String categoryName = catResult.get("name").asText();
					int categoryId =  catResult.get("id").asInt();

					Category category = new Category(categoryName, categoryId);

					product = new Product(id, name, price, category, details);

				}
			}
			
			res = "success";			
		}
		
		return res;		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getSearchMinPrice() {
		return searchMinPrice;
	}

	public void setSearchMinPrice(Integer searchMinPrice) {
		this.searchMinPrice = searchMinPrice;
	}

	public Integer getSearchMaxPrice() {
		return searchMaxPrice;
	}

	public void setSearchMaxPrice(Integer searchMaxPrice) {
		this.searchMaxPrice = searchMaxPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
