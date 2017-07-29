package br.com.rhfactor.myburger.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.rhfactor.myburger.model.Menu;
import br.com.rhfactor.myburger.service.IIngredientService;
import br.com.rhfactor.myburger.service.IMenuService;

@Controller
@Path("admin/menu")
public class MenuController {

	private Logger logger = Logger.getLogger(MenuController.class);
	
	@Inject
	private IIngredientService ingredientService;
	@Inject
	private IMenuService menuService;
	@Inject
	private Validator validator;
	@Inject
	private Result result;

	@Get("/")
	public List<Menu> list() {
		return this.menuService.listAll();
	}

	@Get("/add")
	public void form() {
		this.result.include("ingredientList",this.ingredientService.listAll());
	}

	@Get("/{menu.id}")
	public Menu form(@NotNull Menu menu) {
		this.result.include("ingredientList",this.ingredientService.listAll());
		return this.menuService.get(menu.getId());
	}

	@Post("/")
	public void insert(@NotNull @Valid Menu menu) {
		this.validator.onErrorRedirectTo(this).form();
		this.menuService.save(menu);
		this.result.include("success", true);
		this.result.redirectTo(this).list();
	}

	@Put("/")
	public void update(@NotNull @Valid Menu menu) {
		this.validator.onErrorRedirectTo(this).form(menu);
		this.menuService.save(menu);
		this.result.include("success", true);
		this.result.redirectTo(this).list();
	}

}
