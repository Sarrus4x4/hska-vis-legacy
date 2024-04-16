package hska.iwi.eShopMaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6565401833074694229L;
	
	
	private String searchDescription = null;
	private String searchMinPrice;
	private String searchMaxPrice;
	
	private Double sMinPrice = null;
	private Double sMaxPrice = null;
	
	private User user;
	private List<Product> products;
	private List<Category> categories;
	

	public String execute() throws Exception {
		
		String result = "input";
		
		// Get user:
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		ActionContext.getContext().setLocale(Locale.US);  
		
		if(user != null){
			// Search products and show results:
			String args = "?details=" + this.searchDescription;

			if (!searchMinPrice.isEmpty()){
				args += "&minPrice=" + this.searchMinPrice;
			}
			if (!searchMaxPrice.isEmpty()){
				args += "&maxPrice=" + this.searchMaxPrice;
			}

			JsonNode response = RestHelper.getCall("Search" + args, "product");

			List<Product> products = new ArrayList<Product>();

			for (JsonNode product : response) {
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

			// Show all categories:
			//CategoryManager categoryManager = new CategoryManagerImpl();
			//this.categories = categoryManager.getCategories();
			result = "success";
		}
		
		return result;
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
		
		public List<Category> getCategories() {
			return categories;
		}

		public void setCategories(List<Category> categories) {
			this.categories = categories;
		}
		
		


	public String getSearchValue() {
		return searchDescription;
	}


	public void setSearchValue(String searchValue) {
		this.searchDescription = searchValue;
	}


	public String getSearchMinPrice() {
		return searchMinPrice;
	}


	public void setSearchMinPrice(String searchMinPrice) {
		this.searchMinPrice = searchMinPrice;
	}


	public String getSearchMaxPrice() {
		return searchMaxPrice;
	}


	public void setSearchMaxPrice(String searchMaxPrice) {
		this.searchMaxPrice = searchMaxPrice;
	}


//	public Double getSearchMinPrice() {
//		return searchMinPrice;
//	}
//
//
//	public void setSearchMinPrice(Double searchMinPrice) {
//		this.searchMinPrice = searchMinPrice;
//	}
//
//
//	public Double getSearchMaxPrice() {
//		return searchMaxPrice;
//	}
//
//
//	public void setSearchMaxPrice(Double searchMaxPrice) {
//		this.searchMaxPrice = searchMaxPrice;
//	}
}
