function loadBooks() {
    fetch("books?ajax=true", {
        headers: { "Accept": "application/json" }
    })
        .then(response => response.json())
        .then(books => {
            const list = document.getElementById("bookList");
            list.innerHTML = "";
            books.forEach((book, index) => {
                const li = document.createElement("li");
                li.textContent = `${book.title} — ${book.author}, ${book.year} (${book.level}, ${book.topic})`;
                list.appendChild(li);
            });
        });
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
    }).then(response => {
        if (response.ok) {
            document.getElementById("bookForm").reset();
            loadBooks();
        }
    });
});

window.onload = loadBooks;
