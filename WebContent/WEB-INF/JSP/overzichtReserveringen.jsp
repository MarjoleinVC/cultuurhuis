<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<fmt:setLocale value="nl_BE" />
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Het Cultuurhuis: bevestigen reservaties</title>
<link rel='shortcut icon' href='${contextPath}/images/favicon.ico'
	type='image/x-icon' />
<link rel="stylesheet" href="${contextPath}/css/default.css">
</head>
<body>
	<header>
		<h1>
			Het Cultuurhuis: bevestigen reservaties <span> <img
				src="${contextPath}/images/bevestig.png" id="bevestigReservatie"
				alt="bevestig reservatie" title="Bevestig reservatie" />
			</span>
		</h1>
		<nav class="oneLineMenu">
			<c:if test="${not empty reservatiemandje}">
				<c:url var="voorstellingenURL" value="/voorstellingen.htm" />
				<a href="${voorstellingenURL}">Voorstellingen</a>
				<%-- <c:url var="reservatiemandjeURL" value="/reservatiemandje.htm" />
			<a href="${reservatiemandjeURL}">Reservatiemandje</a>
			<c:url var="reserverenBevestigenURL"
				value="/reserverenBevestigen.htm" />
			<a href="${reserverenBevestigenURL}">Bevestig reservatie</a> --%>
			</c:if>
		</nav>
	</header>
	<h2>Gelukte reserveringen</h2>
	<table>
		<thead>
			<tr class="titels">
				<th>Datum</th>
				<th>Titel</th>
				<th>Uitvoerders</th>
				<th>Prijs(&euro;)</th>
				<th>Plaatsen</th>
			</tr>
		</thead>
		<tbody class="zebra">
			<c:forEach var="gelukteReserveringen" items="${gelukteReserveringen}">
				<tr>
					<td><fmt:formatDate value="${gelukteReserveringen.voorstelling.datum}"
							pattern="dd/MM/yyyy HH:mm" /></td>
					<td>${gelukteReserveringen.voorstelling.titel}</td>
					<td>${gelukteReserveringen.voorstelling.uitvoerders}</td>
					<!-- bij gebruik van currencySymbol in fmt:formatNumeber staat het €-teken achter de prijs! -->
					<td><fmt:formatNumber
							value="${gelukteReserveringen.voorstelling.prijs}"
							minFractionDigits="2" /></td>
					<td>${gelukteReserveringen.aantalPlaatsen}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Mislukte reserveringen</h2>
	<table>
		<thead>
			<tr class="titels">
				<th>Datum</th>
				<th>Titel</th>
				<th>Uitvoerders</th>
				<th>Prijs(&euro;)</th>
				<th class="getal">Plaatsen</th>
				<th class="getal">Vrije plaatsen</th>
			</tr>
		</thead>
		<tbody class="zebra">
			<c:forEach var="mislukteReserveringen"
				items="${mislukteReserveringen}">
				<tr>
					<td><fmt:formatDate
							value="${mislukteReserveringen.voorstelling.datum}"
							pattern="dd/MM/yyyy HH:mm" /></td>
					<td>${mislukteReserveringen.voorstelling.titel}</td>
					<td>${mislukteReserveringen.voorstelling.uitvoerders}</td>
					<!-- bij gebruik van currencySymbol in fmt:formatNumeber staat het €-teken achter de prijs! -->
					<td><fmt:formatNumber value="${voorstelling.prijs}"
							minFractionDigits="2" /></td>
					<td>${mislukteReserveringen.aantalPlaatsen}</td>
					<td>${mislukteReserveringen.voorstelling.vrijeplaatsen}"</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>