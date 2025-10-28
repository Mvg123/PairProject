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
    // 1. 버튼과 입력 필드 가져오기 (기존과 동일)
    const btn = document.getElementById("add-customer-btn");
    const nameInput = document.getElementById("name");
    const phoneInput = document.getElementById("phone");
    const cusnumInput = document.getElementById("cusnum");

    const name = nameInput.value;
    const phone = phoneInput.value;
    const cusnum = cusnumInput.value;

    // 2. 유효성 검사 (기존과 동일)
    if (!name || !phone || !cusnum) {
        alert("이름과 전화번호, 고객수를 입력하세요!");
        return;
    }

    // 3. 버튼 상태 변경 (기존과 동일)
    const originalBtnText = btn.innerHTML;
    btn.disabled = true;
    btn.innerHTML = `<span class="spinner-border spinner-border-sm"></span> 전송 중...`;

    const data = { name, phone, cusnum };

    // 4. ⭐ 두 개의 API 호출을 각각 Promise로 정의합니다.

    // 작업 1: DB에 고객 정보 저장
    const saveToDb = fetch("/api/customers", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    // 작업 2: RabbitMQ로 메시지 전송
    const sendToMq = fetch("/api/cusmsg", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data) // MQ에도 동일한 데이터를 보낸다고 가정
    });

    // 5. ⭐ Promise.all로 두 작업을 동시에 실행하고 결과를 기다립니다.
    try {
        const [resDb, resMq] = await Promise.all([saveToDb, sendToMq]);

        // 6. 두 작업이 "모두" 성공했는지 확인
        if (resDb.ok && resMq.ok) {
            alert("✅ 성공: DB 저장 및 MQ 전송이 모두 완료되었습니다!");

            // 폼 필드 초기화
            nameInput.value = "";
            phoneInput.value = "";
            cusnumInput.value = "";
            await loadCustomers(); // 목록 새로고침

        } else {
            // 둘 중 하나라도 실패한 경우
            let errorMsg = "❌ 처리에 실패했습니다:\n";
            if (!resDb.ok) {
                errorMsg += `- DB 저장 실패: ${resDb.statusText}\n`;
            }
            if (!resMq.ok) {
                errorMsg += `- MQ 전송 실패: ${resMq.statusText}\n`;
            }
            alert(errorMsg);
        }

    } catch (error) {
        // 7. 네트워크 오류 등으로 Promise.all 자체가 실패한 경우
        alert("⚠️ 네트워크 오류가 발생했습니다. 두 작업 모두 실패했을 수 있습니다.");
        console.error(error);
    } finally {
        // 8. 버튼 상태 원래대로 복구 (기존과 동일)
        btn.disabled = false;
        btn.innerHTML = originalBtnText;
    }
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