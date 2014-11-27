<!--
bestaande klant meld zich aan met gebruikersnaam en wachtwoord
+ klikt op "zoek me op"
    ->als klant bestaat regel met voornaam, familienaam, adres
      + wordt knop "bevestigen" bruikbaar
      + velden én buttons "zoek me op" en "ik ben nieuw" niet bruikbaar
    ->als fout getikt: foutmelding "verkeerde gebruikersnaam of paswoord"

reservaties wegschrijven in database: vrije plaatsen aanpassen in tabel voorstellingen (verminderen met aantal tickets)
+ record toevoegen aan tabel reservaties (long id (primary), long klantid, long voorstellingsid, long plaatsen)
+ aangeven gelukte en mislukte reserveringen (als vrije plaatsen < gevraagde tickets
+ reservatiemandje leegmaken

nieuwe klant klikt op "ik ben nieuw"
    ->naar webpagina voor nieuwe klant
*/
-->

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
		<h1 class="blauweTekst">
			Het Cultuurhuis: bevestigen reservaties <span> <img
				src="${contextPath}/images/bevestig.png" id="bevestigReservatie"
				alt="bevestig reservatie" title="Bevestig reservatie" />
			</span>
		</h1>
		<nav class="oneLineMenu">
			<c:if test="${not empty reservatiemandje}">
				<c:url var="voorstellingenURL" value="/voorstellingen.htm" />
				<a href="${voorstellingenURL}">Voorstellingen</a>
				<c:url var="reservatiemandjeURL" value="/reservatiemandje.htm" />
				<a href="${reservatiemandjeURL}">Reservatiemandje</a>
				<%-- <c:url var="reserverenBevestigenURL"
				value="/reserverenBevestigen.htm" />
			<a href="${reserverenBevestigenURL}">Bevestig reservatie</a> --%>
			</c:if>
		</nav>
	</header>
	<article>
		<h2 class="blauweTekst">Stap 1: Wie ben je?</h2>
		<c:url value="/reserverenBevestigen.htm" var="reserverenBevestigenURL" />
		<form action="${reserverenBevestigenURL}" method="post">
			<label>Gebruikersnaam:<br> <input
				title="vul hier uw gebruikersnaam in"
				value="${param.gebruikersnaam}" name="gebruikersnaam" autofocus></label><br>
			<label>Paswoord:<br> <input type="password"
				title="vul hier uw paswoord in" value="${param.paswoord}"
				name="paswoord"></label><br> <br> <input type="submit"
				value="Zoek me op" <c:if test="${not empty klant}">disabled</c:if>>
		</form>
		<c:url value="/nieuweKlant.htm" var="nieuweKlantURL" />
		<form action="${nieuweKlantURL}" method="post">
			<input type="submit" value="Ik ben nieuw"
				<c:if test="${not empty klant}">disabled</c:if>>
		</form>
		<c:if test="${not empty klant}">
			<p class="bold">${klant.vnaam} ${klant.fnaam}
				${klant.adres.straat} ${klant.adres.huisNr} ${klant.adres.postcode}
				${klant.adres.gemeente}</p>
		</c:if>
		<c:if test="${not empty fout}">
			<p class="bold">${fout}</p>
		</c:if>
		<h2 class="blauweTekst">Stap 2: Bevestigen</h2>
		<c:url value="/overzichtReserveringen.htm"
			var="toevoegenReserveringenDbURL" />
		<form action="${toevoegenReserveringenDbURL}" method="post">
			<input type="submit" value="Bevestigen"
				<c:if test="${empty klant}">disabled</c:if>>
		</form>
	</article>
</body>
</html>