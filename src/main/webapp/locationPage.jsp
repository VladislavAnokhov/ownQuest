<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.locations.Locations" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.entity.Player" %>

<%
    Map<String, String> mainText = (Map<String, String>) request.getAttribute("mainText");
    Map<String, String> actionChoices = (Map<String, String>) request.getAttribute("actionChoices");

    Locations currentLocation = (Locations) request.getAttribute("currentLocation");
    String locationDescription = mainText.get(currentLocation.name());
    Player player = (Player) session.getAttribute("player");

%>

<html>
<head>
    <title>${currentLocation}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1><%= locationDescription %></h1>

<c:if test="${not empty sessionScope.player and sessionScope.player.pistol}">
    <form action="actionServlet" method="post">
        <input type="hidden" name="currentLocation" value="<%= currentLocation.name() %>" />
        <button type="submit" name="action" value="usePistol"> <%=actionChoices.get("USE_PISTOL")%></button><br />
    </form>
</c:if>

<c:if test="${not empty sessionScope.player and sessionScope.player.shovel}">
    <form action="actionServlet" method="post">
        <input type="hidden" name="currentLocation" value="<%= currentLocation.name() %>" />
        <button type="submit" name="action" value="useShovel"> <%=actionChoices.get("USE_SHOVEL")%></button><br />

    </form>
</c:if>

<form action="actionServlet" method="post">
    <input type="hidden" name="currentLocation" value="<%= currentLocation.name() %>" />
    <button type="submit" name="action" value="useHands"> <%=actionChoices.get("USE_HANDS")%></button><br />
</form>

<div class="stats">
    <p>Игрок: <%= player.getName() %></p>
    <p>Игр сыграно: <%= player.getTotalGames() %></p>
    <p>Побед: <%= player.getTotalWins() %></p>
    <p>Магазины к пистолету: <%= player.getPistolMagazines() %></p>
    <p>Количество канистр: <%= player.getCanisters() %></p>
    <p>ЗАЩИТА СЕЙЧАС: <%= player.getChemicalProtection() %></p>
    <% if (player.isPistol()) { %>
    🔫
    <% } %>
    <% if (player.getChemicalProtection() > 1) { %>
    🥼
    <% } %>
    <% if (player.isShovel()) { %>
    🗡
    <% } %>
</div>
</body>
</html>