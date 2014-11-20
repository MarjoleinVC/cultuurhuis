<!-- per voorstelling aantal tickets reserveren -->
<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<fmt:setLocale value="nl_BE" />
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Het Cultuurhuis: reserveren</title>
<link rel='shortcut icon' href='${contextPath}/images/favicon.ico'
	type='image/x-icon' />
<link rel="stylesheet" href="${contextPath}/css/default.css">
</head>
<body>
	<header>
		<h1 class="blauweTekst">
			Het Cultuurhuis: voorstellingen <span> <img
				src="${contextPath}/images/reserveren.png" id="reserveren"
				alt="reserveren" title="Reserveren" />
			</span>
		</h1>
		<nav class="oneLineMenu">
			<c:url var="voorstellingenURL" value="/voorstellingen.htm" />
			<a href="${voorstellingenURL}">Voorstellingen</a>
			<%-- <c:url var="reservatiemandjeURL" value="/reservatiemandje.htm" />
			<a href="${reservatiemandjeURL}">Reservatiemandje</a>
			<c:url var="reserverenBevestigenURL"
				value="/reserverenBevestigen.htm" />
			<a href="${reserverenBevestigenURL}">Bevestig reservatie</a> --%>
		</nav>
	</header>
	<article>
		<dl>
			<dd class="OpsommingOpmaak">Voorstelling:</dd>
			<dt>${voorstelling.titel}</dt>
			<dd class="OpsommingOpmaak">Uitvoerders:</dd>
			<dt>${voorstelling.uitvoerders}</dt>
			<dd class="OpsommingOpmaak">Datum:</dd>
			<dt>
				<fmt:formatDate value="${voorstelling.datum}"
					pattern="dd/MM/yyyy HH:mm" />
			</dt>
			<dd class="OpsommingOpmaak">Prijs:</dd>
			<!-- bij gebruik van currencySymbol in fmt:formatNumeber staat het €-teken achter de prijs! -->
			<dt>
				&euro;
				<fmt:formatNumber value="${voorstelling.prijs}"
					minFractionDigits="2" />
			</dt>
			<dd class="OpsommingOpmaak">Vrije plaatsen:</dd>
			<dt>${voorstelling.vrijeplaatsen}</dt>
			<dd class="OpsommingOpmaak">Plaatsen:</dd>
		</dl>
		<c:url value="/reservatiemandje.htm" var="reservatiemandjeURL" />
		<form method="post"
			action="${reservatiemandjeURL}">
			<input type="hidden" name="voorstellingId" value="${voorstelling.voorstellingId}}">
			<input type="number" name="aantalTeReserveren" min="1"
				max="${voorstelling.vrijeplaatsen}" value="${param.aantalTeReserveren}"
				required><br><br><input type="submit" value="Reserveren" />
			<c:if test="${not empty fout}"><div></div></c:if>
		</form>
	</article>
</body>
</html>