<!-- Webpagina waarin overzicht gegeven wordt van de reserveringen die de klant heeft gedaan, maar nog moet bevestigen.
 Aangevinkte reservatielijnen verwijderen en reservatiemandje opnieuw tonen. -->

<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<fmt:setLocale value="nl_BE" />
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Het Cultuurhuis: reservatiemandje</title>
<link rel='shortcut icon' href='${contextPath}/images/favicon.ico'
	type='image/x-icon' />
<link rel="stylesheet" href="${contextPath}/css/default.css">
</head>
<body>
	<header>
		<h1 class="blauweTekst">
			Het Cultuurhuis: reservatiemandje <span> <img
				src="${contextPath}/images/mandje.png" id="reservatiemandje"
				alt="reservatiemandje" title="Reservatiemandje" />
			</span>
		</h1>
		<nav class="oneLineMenu">
				<c:url var="voorstellingenURL" value="/voorstellingen.htm" />
				<a href="${voorstellingenURL}">Voorstellingen</a>
				<%-- <c:url var="reservatiemandjeURL" value="/reservatiemandje.htm" />
			<a href="${reservatiemandjeURL}">Reservatiemandje</a> --%>
				<c:url var="reserverenBevestigenURL"
					value="/reserverenBevestigen.htm" />
				<a href="${reserverenBevestigenURL}">Bevestig reservatie</a>
		</nav>
	</header>
	<c:url var="reservatiemandjeURL" value="/reservatiemandje.htm" />
	<form action="${reservatiemandjeURL}" method="post">
		<table>
			<thead>
				<tr class="titels">
					<th>Datum</th>
					<th>Titel</th>
					<th>Uitvoerders</th>
					<th>Prijs</th>
					<th>Plaatsen</th>
					<th><input type="submit" value="Verwijderen" /></th>
				</tr>
			</thead>
			<!-- TODO geeft de reserveringen niet weer in het mandje! -->
			<tbody class="zebra">
				<c:forEach var="reservering"
					items="${reserveringenInReservatiemandje}">
					<tr>
						<td><fmt:formatDate value="${reservering.voorstelling.datum}" pattern="dd/MM/yyyy HH:mm"/></td>
						<td>${reservering.voorstelling.titel}</td>
						<td>${reservering.voorstelling.uitvoerders}</td>
						<td>&euro;${reservering.voorstelling.prijs}</td>
						<td class="getal">${reservering.aantalPlaatsen}</td>
						<td><input type="checkbox" id="verwijderenAangevinkt" name="verwijderenAangevinkt"
							value="${reservering.voorstelling.voorstellingId}"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<p>Te betalen: &euro;${totaalTeBetalen}</p>
</body>
</html>