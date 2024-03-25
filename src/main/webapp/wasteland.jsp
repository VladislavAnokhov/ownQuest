<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.entity.Player" %>
<%
    Map<String, String> mainText = (Map<String, String>) request.getAttribute("mainText");
    Map<String, String> actionChoices = (Map<String, String>) request.getAttribute("actionChoices");
    String caretakerText = mainText.get("BUNKER_EXIT");
%>
<html>
<head>
    <title>Wasteland</title>
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
<form action="wasteland" method="post">

    <button type="submit" name="direction" value="left" ><%=actionChoices.get("GO_LEFT")%></button><br />
    <button type="submit" name="direction" value="straight" ><%=actionChoices.get("GO_STRAIGHT")%></button><br />
    <button type="submit" name="direction" value="right" ><%=actionChoices.get("GO_RIGHT")%></button><br />
    <button type="submit" name="direction" value="back" ><%=actionChoices.get("BACK_TO_BUNKER")%></button><br />
</form>
</div>

<% if (session.getAttribute("player") != null) {
    Player player = (Player) session.getAttribute("player");
    player.decreaseChemicalProtection();
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
