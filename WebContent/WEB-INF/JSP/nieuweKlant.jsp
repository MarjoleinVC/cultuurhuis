<!-- 
klanten toevoegen aan database:
per nieuwe klant een regel in tabel klanten (long id (=primary), String voornaam, String familienaam, String straat, String huisnr, String postcode, String gemeente, String gebruikersnaam, String wachtwoord  

Adres = String straat, String huisnr, String postcode, String gemeente

alle velden zijn verplicht! op OK klikken op toe te voegen aan database
mogelijke fouten: lege velden, paswoord != herhaal paswoord, gebruikersnaam bestaat al
-> ieder fout oplijsten onder formulier
->geslaagde aanmaak nieuwe klant = terugkeren naar reserverenBevestigen
-->

<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<fmt:setLocale value="nl_BE" />
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Het Cultuurhuis: nieuwe klant</title>
<link rel='shortcut icon' href='${contextPath}/images/favicon.ico'
	type='image/x-icon' />
<link rel="stylesheet" href="${contextPath}/css/default.css">
</head>
<body>
	<header>
		<h1>
			Het Cultuurhuis: nieuwe klant <span> <img
				src="${contextPath}/images/nieuweklant.png" id="nieuweKlant"
				alt="nieuwe klant" title="nieuwe klant" />
			</span>
		</h1>
		<nav class="oneLineMenu">
			<c:if test="${not empty reservatiemandje}">
				<c:url var="voorstellingenURL" value="/voorstellingen.htm" />
				<a href="${voorstellingenURL}">Voorstellingen</a>
				<c:url var="reservatiemandjeURL" value="/reservatiemandje.htm" />
				<a href="${reservatiemandjeURL}">Reservatiemandje</a>
				<c:url var="reserverenBevestigenURL"
					value="/reserverenBevestigen.htm" />
				<a href="${reserverenBevestigenURL}">Bevestig reservatie</a>
			</c:if>
		</nav>
	</header>
	<article>
		<!-- Geen required bij <input> want opsomming fouten moet onder formulier weergegeven worden -->
		<c:url value="/nieuweKlant.htm" var="nieuweKlantURL" />
		<form action="${nieuweKlantURL}" method="post" id="klantToevoegen">
			<label>Voornaam:<%-- <span>${fouten.vnaam}</span>  --%> <br>
				<input title="vul hier uw voornaam in" value="${param.vnaam}"
				name="vnaam" class="kaderBreedte" autofocus>
			</label><br> <br> <label>Familienaam: <br> <input
				title="vul hier uw familienaam in" value="${param.fnaam}"
				name="fnaam" class="kaderBreedte">
			</label><br> <br> <label>Straat: <br> <input
				title="vul hier uw straat in" value="${param.straat}" name="straat"
				class="kaderBreedte">
			</label><br> <br> <label>Huisnr.: <br> <input
				title="vul hier uw huisnr in" value="${param.huisnr}" name="huisnr"
				class="kaderBreedte">
			</label><br> <br> <label>Postcode: <br> <input
				title="vul hier uw postcode in" value="${param.postcode}"
				name="postcode" class="kaderBreedte">
			</label><br> <br> <label>Gemeente: <br> <input
				title="vul hier uw gemeente in" value="${param.gemeente}"
				name="gemeente" class="kaderBreedte">
			</label><br> <br> <label>Gebruikersnaam: <br> <input
				title="vul hier uw gebruikersnaam in"
				value="${param.gebruikersnaam}" name="gnaam" class="kaderBreedte">
			</label><br> <br> <label>Paswoord: <br> <input
				type="password" title="vul hier uw paswoord in"
				value="${param.paswoord}" name="paswoord" class="kaderBreedte">
			</label><br> <br> <label>Herhaal paswoord: <br> <input
				type="password" title="vul hier uw paswoord in"
				value="${param.paswoord2}" name="paswoord2" class="kaderBreedte">
			</label><br> <br> <input type="submit" value="OK">
		</form>
		<c:if test="${not empty fouten}">
			<ol class="rodeTekst">
				<c:forEach var="fout" items="${fouten}">
					<li>${fout.value}<br></li>
				</c:forEach>
			</ol>
		</c:if>
	</article>
</body>
</html>