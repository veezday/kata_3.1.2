document.addEventListener('DOMContentLoaded', function () {
    const user = fetchUser();

    setUser(user);
    checkRole(user);
});

function fetchUser() {
    return fetch('user/get', {
        credentials: 'include'
    })
        .then(response => response.json());
}

function setUser(user) {
    const id = document.querySelectorAll('.user-id');
    const name = document.querySelectorAll('.user-name');
    const surname = document.querySelectorAll('.user-surname');
    const password = document.querySelectorAll('.user-password');
    const email = document.querySelectorAll('.user-email');
    const role = document.querySelectorAll('.user-role');


    user.then(data => {
        setTextContentForEach(id, data.id);
        setTextContentForEach(name, data.username);
        setTextContentForEach(surname, data.surname);
        setTextContentForEach(password, data.password);
        setTextContentForEach(email, data.email);
        setTextContentForEach(role, data.authorities.join(' '));
    });
}

function setTextContentForEach(list, value) {
    list.forEach(item => item.textContent = value);
}

function checkRole(user) {
    const admin = document.querySelectorAll('.role-admin');

    user.then(data => {
       if (!data.authorities.includes('ADMIN')) {
           admin.forEach(item => item.style.display = 'none')
       }
    });
}