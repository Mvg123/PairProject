async function loadCustomers() {
    const res = await fetch("/api/customers");
    const customers = await res.json();
    const tbody = document.getElementById("customer-table");
    tbody.innerHTML = "";

    customers.forEach(c => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${c.id}</td>
            <td><input type="text" value="${c.name}" id="name-${c.id}"></td>
            <td><input type="text" value="${c.phone}" id="phone-${c.id}"></td>
            <td><input type="text" value="${c.cusnum}" id="cusnum-${c.id}"></td>
            <td>
                <button onclick="updateCustomer(${c.id})">수정</button>
                <button onclick="deleteCustomer(${c.id})">삭제</button>
            </td>
        `;

        tbody.appendChild(tr);
    });
}

async function addCustomer() {
    const name = document.getElementById("name").value;
    const phone = document.getElementById("phone").value;
    const cusnum = document.getElementById("cusnum").value;

    if (!name || !phone || !cusnum) {
        alert("이름과 전화번호, 고객수를 입력하세요!");
        return;
    }

    await fetch("/api/customers", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, phone, cusnum })
    });

    document.getElementById("name").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("cusnum").value = "";
    loadCustomers();
}

async function updateCustomer(id) {
    const name = document.getElementById(`name-${id}`).value;
    const phone = document.getElementById(`phone-${id}`).value;
    const cusnum = document.getElementById(`cusnum-${id}`).value;

    await fetch(`/api/customers/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, phone, cusnum })
    });
    if (!confirm("수정되었습니다.")) return;
    loadCustomers();
}

async function deleteCustomer(id) {
    if (!confirm("정말 삭제하시겠습니까?")) return;

    await fetch(`/api/customers/${id}`, { method: "DELETE" });
    loadCustomers();
}

document.addEventListener("DOMContentLoaded", loadCustomers);