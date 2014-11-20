<!--Fouten-->
<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<fmt:setLocale value="nl_BE" />
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Het Cultuurhuis: voorstellingen</title>
<link rel='shortcut icon' href='${contextPath}/images/favicon.ico'
	type='image/x-icon' />
<link rel="stylesheet" href="${contextPath}/css/default.css">
</head>
<body>
	<header>
		<h1 class="blauweTekst">
			Het Cultuurhuis<span> <img
				src="${contextPath}/images/voorstellingen.png" id="voorstellingen"
				alt="voorstellingen" title="Voorstellingen" />
			</span>
		</h1>
	</header>
	<h2 class="blauweTekst">Problemen bij het ophalen van data</h2>
	<p>
		We kunnen de gevraagde data niet ophalen wegens een technische
		storing.<br> Gelieve de helpdesk te contacteren.
	</p>
	<p>
		Om terug te keren naar het overzicht klik
		<c:url var="voorstellingenURL" value="/index.htm" />
		<a href="${voorstellingenURL}">hier</a>.
	</p>
</body>
</html>
