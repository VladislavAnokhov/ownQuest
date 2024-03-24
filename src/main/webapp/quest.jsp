<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Map" %>
<%@ page import="com.javarush.quest.anokhov.ownQuest.entity.Player" %>
<%
    Map<String, String> mainText = (Map<String, String>) request.getAttribute("mainText");
    Map<String, String> incorrectAnswers = (Map<String, String>) request.getAttribute("incorrectAnswers");
    Map<String, String> correctAnswers = (Map<String, String>) request.getAttribute("correctAnswers");
    String caretakerText = mainText.get("CARETAKER");
    session = request.getSession(false);
    Player player = (Player) session.getAttribute("player");
    String greetingName = player != null ? player.getName() : "UNKNOWN";

    System.out.println(incorrectAnswers.get("DENY_QUEST"));
%>
<html>
<head>
    <title>Квест бункер №99</title>
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
<h1>Приветствую тебя, <%= greetingName %>!</h1>
<p><%= caretakerText %></p>
<form action="quest" method="post">
    <input type="hidden" name="playerName" value="${playerName}" />
    <input type="submit" name="acceptQuest" value="<%=correctAnswers.get("ACCEPT_QUEST")%>" />
</form>

<form action="quest" method="post">
    <input type="hidden" name="playerName" value="${playerName}" />
    <input type="submit" name="rejectQuest" value="<%=incorrectAnswers.get("DENY_QUEST")%>" />
</form>

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
</body>
</html>
