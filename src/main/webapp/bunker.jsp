<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.entity.Player" %>
<%
    Map<String, String> mainText = (Map<String, String>) request.getAttribute("mainText");
    Map<String, String> actionChoices = (Map<String, String>) request.getAttribute("actionChoices");
    String caretakerText = mainText.get("BUNKER");
%>
<html>
<head>
    <title>Bunker</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript">
        window.onload = function() {
            var message = "${message}";
            if (message) {
                alert(message);
            }
        };
    </script>
</head>
<body>
<h1><%= caretakerText %></h1>
<div class="button-container">
<form action="bunker" method="post">
    <button type="submit" name="action" value="armory"><%= actionChoices.get("ARMORY_ROOM") %></button><br />
    <button type="submit" name="action" value="storage"><%= actionChoices.get("STORAGE_ROOM") %></button><br />
    <button type="submit" name="action" value="chemical"><%= actionChoices.get("CHEMICAL_ROOM") %></button><br />
    <button type="submit" name="action" value="exit"><%= actionChoices.get("EXIT") %></button><br />
</form>
</div>

<% if (session.getAttribute("player") != null) {
    Player player = (Player) session.getAttribute("player");
%>
<div class="stats">
    <p>Игрок: <%= player.getName() %></p>
    <p>Игр сыграно: <%= player.getTotalGames() %></p>
    <p>Побед: <%= player.getTotalWins() %></p>
    <p>Магазины к пистолету: <%= player.getPistolMagazines() %> </p>
    <p>Количество канистр: <%= player.getCanisters() %></p>
    <p>Защита от радиации: <%= player.getChemicalProtection() %> </p>
    <% if (player.isPistol()) { %>
    🔫
    <% } %>
    <% if (player.getChemicalProtection()>0) { %>
    🥼
    <% } %>
    <% if (player.isShovel()) { %>
    🗡
    <% } %>
</div>
<% } %>
</body>
</html>
