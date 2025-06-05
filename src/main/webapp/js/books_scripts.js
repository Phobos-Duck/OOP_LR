function loadBooks() {
    fetch("books?ajax=true", {
        headers: { "Accept": "application/json" }
    })
        .then(response => response.json())
        .then(books => {
            const list = document.getElementById("bookList");
            list.innerHTML = "";

            books.forEach(book => {
                const li = document.createElement("li");
                li.innerHTML = `
                    ${book.title} — ${book.author}, ${book.year} (${book.level}, ${book.topic})
                    <button onclick="deleteBook(${book.id})" class="delete-btn">Удалить</button>
                `;
                list.appendChild(li);
            });
        });
}

function deleteBook(id) {
    fetch(`books?id=${id}`, {
        method: "DELETE"
    }).then(() => loadBooks());
}

document.getElementById("bookForm").addEventListener("submit", function (e) {
    e.preventDefault();
    const book = {
        title: document.getElementById("book-title").value,
        author: document.getElementById("book-author").value,
        year: parseInt(document.getElementById("book-year").value),
        level: document.getElementById("book-level").value,
        topic: document.getElementById("book-topic").value
    };

    fetch("books", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(book)
    }).then(() => {
        this.reset();
        loadBooks();
    });
});

window.onload = loadBooks;
