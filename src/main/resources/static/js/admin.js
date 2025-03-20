document.addEventListener('DOMContentLoaded', function () {
    updateData(fetchData())
    initForms();
});

function fetchData() {
    return fetch('admin/get', {
        credentials: 'include'
    })
        .then(response => response.json())
}

function updateData(data) {
    setAdmin(data);
    reFillTable(data);
}

function setAdmin(data) {
    data.then(map => {
        document.querySelectorAll('.admin-name')
            .forEach(item => item.textContent = map.admin.username);
        document.querySelectorAll('.admin-role')
            .forEach(item => item.textContent = map.admin.authorities.join(' '));
        if (!map.admin.authorities.includes('USER')) {
            document.querySelectorAll('.role-user')
                .forEach(item => item.style.display = 'none');
        }
    })
}

function reFillTable(data) {
    const table = document.querySelector('.users-table');
    Array.from(table.querySelectorAll('tr')).slice(1).forEach(row => row.remove());

    data.then(map => map.users.forEach(user => {
        const row = table.insertRow();
        row.insertCell(0).textContent = user.id;
        row.insertCell(1).textContent = user.username;
        row.insertCell(2).textContent = user.surname;
        row.insertCell(3).textContent = user.password;
        row.insertCell(4).textContent = user.email;
        row.insertCell(5).textContent = user.authorities.join(' ');
        row.insertCell(6).appendChild(createUserButton(
            'Edit',
            'btn btn-info text-white',
            '#editModal', user));
        row.insertCell(7).appendChild(createUserButton(
            'Delete',
            'btn btn-danger',
            '#deleteModal', user));
    }));
}

function createUserButton(value, style, target, user) {
    const btn = document.createElement('input');
    btn.setAttribute('type', 'button');
    btn.setAttribute('value', value);
    btn.setAttribute('class', style);
    btn.setAttribute('data-bs-toggle', 'modal');
    btn.setAttribute('data-bs-target', target);
    btn.addEventListener('click', () => {
        document.querySelectorAll('.modal-id').forEach(item => item.value = user.id);
        document.querySelectorAll('.modal-name').forEach(item => item.value = user.username);
        document.querySelectorAll('.modal-surname').forEach(item => item.value = user.surname);
        document.querySelectorAll('.modal-password').forEach(item => item.value = user.password);
        document.querySelectorAll('.modal-email').forEach(item => item.value = user.email);
        document.querySelectorAll('.modal-select-role').forEach(select => {
            for (i = 0; i < select.options.length; i++) {
                var option = select.options[i];
                if (user.authorities.includes(option.value)) {
                    option.selected = true;
                } else {
                    option.selected = false;
                }
            }
        });
    })

    return btn;
}

function initForms() {
    const createForm = document.getElementById('createForm');
    const editForm = document.getElementById('editForm');
    const deleteForm = document.getElementById('deleteForm');
    createForm.addEventListener('submit', (event) => {
        event.preventDefault();
        createUser(formToJson(createForm)).then(() => updateData(fetchData()));
    });
    editForm.addEventListener('submit', (event) => {
        event.preventDefault();
        editUser(formToJson(editForm)).then(() => updateData(fetchData()));
    });
    deleteForm.addEventListener('submit', (event) => {
        event.preventDefault();
        deleteUser(formToJson(deleteForm)).then(() => updateData(fetchData()));
    });
}

function createUser(data) {
    return fetch('/admin', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .catch(error => {
            alert(error);
        })
}

function editUser(data) {
    return fetch('/admin', {
        method: 'PUT',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .catch(error => {
            alert(error);
        })
}

function deleteUser(data) {
    return fetch('/admin', {
        method: 'DELETE',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .catch(error => {
            alert(error);
        })
}

function formToJson(form) {
    const formData = new FormData(form);
    const data = {};

    for (const [key, value] of formData.entries()) {
        const isMultiple = form.elements[key].multiple;
        if (isMultiple) {
            data[key] = Array.from(formData.getAll(key));
        } else {
            data[key] = value;
        }
    }

    return data;
}
