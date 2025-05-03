<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="css/header.jsp" %>

<div class="container">
  <h1>Калькулятор объема</h1>

  <label for="shape">Фигура</label>
  <select id="shape" name="shape">
    <option value="cube">Куб</option>
    <option value="sphere">Сфера</option>
    <option value="cylinder">Цилиндр</option>
    <option value="tetrahedron">Тетраэдр</option>
  </select>

  <label for="param1">Параметр 1</label>
  <input type="number" id="param1" name="param1" required>

  <label id="param2-label" for="param2">Параметр 2</label>
  <input type="number" id="param2" name="param2">

  <button onclick="calculateVolume()">Рассчитать</button>

  <div id="result"></div>
</div>

<%@ include file="js/footer.jsp" %>

