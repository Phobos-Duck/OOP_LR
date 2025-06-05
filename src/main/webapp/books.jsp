<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список книг</title>
    <link rel="stylesheet" href="static/styles.css">
</head>
<body>
<div class="nav-container">
    <div class="nav-content">
        <a class="nav-btn left-btn" href="feedback.html">Обратная связь</a>
        <a class="nav-btn left-btn" href="index.html">На главную</a>
        <div class="right-buttons">
            <a class="nav-btn" href="contacts.html">Контакты</a>
            <a class="nav-btn" href="about.html">О нас</a>
        </div>
    </div>
</div>

<div class="main-content">
    <div class="calculator-container">
        <h1>Добавление книги</h1>
        <form id="bookForm">
            <label for="book-title">Название:</label>
            <input type="text" id="book-title" required>

            <label for="book-author">Автор:</label>
            <input type="text" id="book-author" required>

            <label for="book-year">Год:</label>
            <input type="number" id="book-year" required>

            <label for="book-level">Уровень:</label>
            <input type="text" id="book-level" required>

            <label for="book-topic">Тема:</label>
            <input type="text" id="book-topic" required>

            <button type="submit">Добавить книгу</button>
        </form>

        <h2>Список книг:</h2>
        <ul id="bookList" style="text-align:left; padding-left:0; color: #4a075e"></ul>
    </div>
</div>

<script src="js/books_scripts.js"></script>
</body>
</html>
