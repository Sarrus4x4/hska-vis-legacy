package hska.iwi.eShopMaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class AddProductAction extends ActionSupport {

	private static final long serialVersionUID = 39979991339088L;

	private String name = null;
	private String price = null;
	private int categoryId = 0;
	private String details = null;
	private List<Category> categories;

	public String execute() throws Exception {
		String result = "input";
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			// Restcall zu Kategorien um zu überprüfen ob Kategroie existieren
			JsonNode catResult = RestHelper.getCall("Category/" + categoryId, "category");

			if(catResult != null && !catResult.isNull()) {
				// Restcall zu Produkte um Produkt anzulegen
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("name", name));
				postParameters.add(new BasicNameValuePair("categoryId", categoryId + ""));
				postParameters.add(new BasicNameValuePair("price", price));
				postParameters.add(new BasicNameValuePair("details", details));

				String res = RestHelper.postCallParam("Product", "product", postParameters);
			}

			result = "success";
		}

		return result;
	}



	@Override
	public void validate() {
		CategoryManager categoryManager = new CategoryManagerImpl();
		this.setCategories(categoryManager.getCategories());
		// Validate name:

		if (getName() == null || getName().length() == 0) {
			addActionError(getText("error.product.name.required"));
		}

		// Validate price:

		if (String.valueOf(getPrice()).length() > 0) {
			if (!getPrice().matches("[0-9]+(.[0-9][0-9]?)?")
					|| Double.parseDouble(getPrice()) < 0.0) {
				addActionError(getText("error.product.price.regex"));
			}
		} else {
			addActionError(getText("error.product.price.required"));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
