<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Квест бункер 99</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <meta charset="utf-8">
</head>
<body>
<div class="container-fluid">
    <h1 class="text-center">Добро пожаловать в квест!</h1>
<p>Действия происходят в постапокалиптическом мире, в бункере №99. Назовите свое имя, житель убежища №99. </p>
<form action="quest" method="post">
    <label>Введите ваше имя: </label>
    <input type="text" name="playerName">
    <button type="submit">Начать игру</button>
</form>
</div>
</body>
</html>