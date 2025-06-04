document.getElementById("shape").addEventListener("change", function () {
    let shape = this.value;
    let param2Label = document.getElementById("param2-label");
    let param2 = document.getElementById("param2");

    if (shape === "cube" || shape === "sphere" || shape === "tetrahedron") {
        param2Label.style.display = "none";
        param2.style.display = "none";
    } else {
        param2Label.style.display = "block";
        param2.style.display = "block";
    }
});

function calculateVolume() {
    let shape = document.getElementById("shape").value;
    let param1 = parseFloat(document.getElementById("param1").value);
    let param2 = parseFloat(document.getElementById("param2").value) || 0;

    fetch("calculateVolume", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `shape=${shape}&param1=${param1}&param2=${param2}`
    })
        .then(response => response.text())
        .then(data => document.getElementById("result").textContent = data)
        .catch(error => console.error("Ошибка:", error));
}

