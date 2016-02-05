package com.nortal.liferay.menu;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class MenuDigger implements Serializable {

	private static final Logger log = LoggerFactory.getLogger(MenuDigger.class);
	private static final String LANDING_PAGE = "/test-landing-page";

	public List<MenuItem> diggMenu(PortletRequest request) {
		log.debug("Digging menu");
		try {
			long groupId = getGroupId(request);
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, false, LANDING_PAGE);
			List<MenuItem> menuItems = new ArrayList<MenuItem>();
			MenuItem item = digMenuItem(layout);
			menuItems.add(item);
			return menuItems;
		} catch (PortalException e) {
			log.error("Error: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private MenuItem digMenuItem(Layout layout) {
		MenuItem item = createMenuItem(layout);
		List<Layout> children = layout.getChildren();
		List<MenuItem> childItems = new ArrayList<MenuItem>();
		for(Layout childLayout: children) {
			MenuItem childItem = digMenuItem(childLayout);
			childItems.add(childItem);
		}
		item.setChildren(childItems);
		return item;
	}

	private MenuItem createMenuItem(Layout layout) {
		MenuItem item = new MenuItem();
		item.setName(layout.getHTMLTitle(Locale.ENGLISH));
		item.setUrl(layout.getFriendlyURL());
		return item;
	}

	public long getGroupId(PortletRequest renderRequest){
		HttpServletRequest servletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		ThemeDisplay display = getThemeDisplay(servletRequest);
		if( display != null ){
			return display.getSiteGroupId();
		}
		LayoutSet layoutSet = (LayoutSet)servletRequest.getAttribute( com.liferay.portal.util.WebKeys.VIRTUAL_HOST_LAYOUT_SET );
		return layoutSet.getGroupId();
	}

	public ThemeDisplay getThemeDisplay(HttpServletRequest request){
		if( request != null ){
			return (ThemeDisplay)request.getAttribute( WebKeys.THEME_DISPLAY );
		}
		return null;
	}

}
