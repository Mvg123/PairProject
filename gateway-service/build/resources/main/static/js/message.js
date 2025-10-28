async function loadMessage() {
    const res = await fetch("/api/message")
    const message = await res.json()
    const tbody = document.getElementById("message-table");
    tbody.innerHTML = "";

    message.forEach(m => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${m.id}</td>
            <td><input type="text" value="${m.content}" id="content-${m.id}" readonly style="background-color:#f8f9fa; border:1px solid #ddd;"></td>
            <td>
                <button onclick="deleteMessage(${m.id})">삭제</button>
            </td>
        `;

        tbody.appendChild(tr);
    });

}

async function deleteMessage(id) {
    if (!confirm("정말 삭제하시겠습니까?")) return;

    await fetch(`/api/message/${id}`, { method: "DELETE" });
    loadMessage();
}

document.addEventListener("DOMContentLoaded", loadMessage);