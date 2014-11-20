<!--overzicht voorstellingen-->
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
			Het Cultuurhuis: voorstellingen <span> <img
				src="${contextPath}/images/voorstellingen.png" id="voorstellingen"
				alt="voorstellingen" title="Voorstellingen" />
			</span>
		</h1>
		<nav class="oneLineMenu">
			<%-- <c:url var="voorstellingenURL" value="/voorstellingen.htm" />
			<a href="${voorstellingenURL}">Voorstellingen</a> --%>
			<c:url var="reservatiemandjeURL" value="/reservatiemandje.htm" />
			<a href="${reservatiemandjeURL}">Reservatiemandje</a>
			<c:url var="reserverenBevestigenURL"
				value="/reserverenBevestigen.htm" />
			<a href="${reserverenBevestigenURL}">Bevestig reservatie</a>
		</nav>
		<h2 class="blauweTekst">Genres</h2>
		<nav class="oneLineMenu">
			<c:forEach var="genre" items="${genres}">
				<c:url var="genreURL" value="/voorstellingen.htm" />
				<a href="${genreURL}?genre=${genre.naam}">${genre.naam}</a>
			</c:forEach>
		</nav>
	</header>
	<h2 class="blauweTekst">${param.genre} voorstellingen</h2>
	<table>
		<thead>
			<tr class="titels">
				<th>Datum</th>
				<th>Titel</th>
				<th>Uitvoerders</th>
				<th>Prijs</th>
				<th id="getal">Vrije plaatsen</th>
				<th>Reserveren</th>
			</tr>
		</thead>
		<tbody class="zebra">
			<c:forEach var="voorstelling" items="${voorstellingen}">
				<tr>
					<td><fmt:formatDate value="${voorstelling.datum}"
							pattern="dd/MM/yyyy HH:mm" /></td>
					<td>${voorstelling.titel}</td>
					<td>${voorstelling.uitvoerders}</td>
					<!-- bij gebruik van currencySymbol in fmt:formatNumeber staat het €-teken achter de prijs! -->
					<td>&euro;<fmt:formatNumber value="${voorstelling.prijs}"
							minFractionDigits="2" /></td>
					<td>${voorstelling.vrijeplaatsen}</td>
					<td><c:if test="${voorstelling.vrijeplaatsen>0}">
							<c:url value="/reserveren.htm" var="reserverenURL" />
							<a
								href="${reserverenURL}?voorstelling=${voorstelling.voorstellingId}">Reserveren</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
