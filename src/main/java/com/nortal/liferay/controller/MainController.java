package com.nortal.liferay.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nortal.liferay.menu.MenuDigger;
import com.nortal.liferay.menu.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.annotation.Resource;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("VIEW")
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@Resource
	private MenuDigger menuDigger;

	@RenderMapping
	public String renderView(PortletRequest request) {
		log.info("Rendering view");
		return "view";
	}

	@ResourceMapping("menu")
	public void renderMenu(ResourceRequest request, ResourceResponse response) throws IOException {
		log.info("Handling rest get Request");
		List<MenuItem> menuItems = menuDigger.diggMenu(request);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.writeValue(response.getPortletOutputStream(), menuItems);
		mapper.writeValue(System.out, menuItems);
	}
}
