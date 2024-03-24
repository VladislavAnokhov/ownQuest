<%@ page import="java.util.List" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.locations.Locations" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.entity.Player" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.entity.Action" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Результат</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h2>${message}</h2>
<%
    Locations currentLocation = (Locations) request.getAttribute("currentLocation");

    if (session.getAttribute("player") != null) {
        Player player = (Player) session.getAttribute("player");
    List<Action> options = (List<Action>) request.getAttribute("options");
    if (options == null) {
        options = (List<Action>) session.getAttribute("options");
        session.removeAttribute("options");
    }
    if (options != null) {
        for (Action option : options) {

%>
<form action="<%= request.getContextPath() + '/' + option.getName() %>" method="post">
    <input type="submit" value="<%= option.getDescription() %>" />
</form>
<%
        }
    }

%>

<div class="stats">
    <p>Игрок: <%= player.getName() %></p>
    <p>Игр сыграно: <%= player.getTotalGames() %></p>
    <p>Побед: <%= player.getTotalWins() %></p>
    <p>Магазины к пистолету: <%= player.getPistolMagazines() %> </p>
    <p>Количество канистр: <%= player.getCanisters() %></p>
    <p>ЗАЩИТА СЕЙЧАС <%= player.getChemicalProtection() %> </p>
    <% if (player.isPistol()) { %>
    🔫
    <% } %>
    <% if (player.getChemicalProtection()>1) { %>
    🥼
    <% } %>
    <% if (player.isShovel()) { %>
    🗡
    <% } %>
</div>
<% } %>
</body>
</html>
