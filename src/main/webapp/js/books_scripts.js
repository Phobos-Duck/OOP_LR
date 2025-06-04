document.getElementById("bookForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const book = {
        title: document.getElementById("title").value,
        author: document.getElementById("author").value,
        year: parseInt(document.getElementById("year").value),
        level: document.getElementById("level").value,
        topic: document.getElementById("topic").value
    };

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "books", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onload = function () {
        if (xhr.status === 200) {
            loadBooks();
        } else {
            alert("Ошибка при добавлении книги. Код: " + xhr.status);
        }
    };
    xhr.send(JSON.stringify(book));
    this.reset();
});

function loadBooks() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "books", true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            const books = JSON.parse(xhr.responseText);
            const list = document.getElementById("list");
            list.innerHTML = "";

            books.forEach(book => {
                const li = document.createElement("li");
                li.textContent = `${book.title} — ${book.author}, ${book.year} (${book.level}, ${book.topic})`;
                li.style.marginBottom = "8px";
                list.appendChild(li);
            });
        }
    };
    xhr.send();
}

window.onload = loadBooks;