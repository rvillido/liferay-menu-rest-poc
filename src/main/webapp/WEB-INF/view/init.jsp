<%@page contentType="text/html; charset=UTF-8" %>
<%--
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
--%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%--

--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--

--%>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%--
--%>
<%@ taglib prefix="liferay-theme" uri="http://liferay.com/tld/theme" %>
<%--
--%>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>

<portlet:defineObjects/>
<liferay-theme:defineObjects/>

<c:set var="ns" scope="request">
    <portlet:namespace/>
</c:set>
