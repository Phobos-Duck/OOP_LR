<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="css/header.jsp" %>

<div class="container">
    <h1>Обратная связь</h1>

    <form method="post" action="feedback">
        <label for="name">Ваше имя</label>
        <input type="text" id="name" name="name" required>

        <label for="message">Сообщение</label>
        <input type="text" id="message" name="message" required>

        <button type="submit">Отправить</button>
    </form>

    <%
        Boolean feedbackReceived = (Boolean) request.getAttribute("feedbackReceived");
        if (feedbackReceived != null && feedbackReceived) {
    %>
    <div id="result">Спасибо за ваш отклик, <%= request.getAttribute("name") %>!</div>
    <%
        }
    %>
</div>

<%@ include file="js/footer.jsp" %>
