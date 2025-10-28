async function loadGym() {
    const res = await fetch("/api/gym");
    const gym = await res.json();
    const tbody = document.getElementById("gym-table");
    tbody.innerHTML = "";

    gym.forEach(g => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${g.id}</td>
            <td><input type="text" value="${g.name}" id="name-${g.id}" ></td>
            <td><input type="text" value="${g.item}" id="item-${g.id}" ></td>
            <td>
                <button onclick="updateGym(${g.id})">수정</button>
                <button onclick="deleteGym(${g.id})">삭제</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

async function addGym() {
    const name = document.getElementById("name").value;
    const item = document.getElementById("item").value;

    if (!name || !item) {
        alert("장소와 종목을 입력하세요!");
        return;
    }

    await fetch("/api/gym", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, item })
    });

    document.getElementById("name").value = "";
    document.getElementById("item").value = "";
    loadGym();
}

async function updateGym(id) {
    const name = document.getElementById(`name-${id}`).value;
    const item = document.getElementById(`item-${id}`).value;

    await fetch(`/api/gym/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, item })
    });

    loadGym();
}

async function deleteGym(id) {
    if (!confirm("정말 삭제하시겠습니까?")) return;

    await fetch(`/api/gym/${id}`, { method: "DELETE" });
    loadGym();
}

document.addEventListener("DOMContentLoaded", loadGym);