document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('[data-bs-target="#editModal"]').forEach(button => {
        button.addEventListener('click', userFormSetter('edit'))
    })

    document.querySelectorAll('[data-bs-target="#deleteModal"]').forEach(button => {
        button.addEventListener('click', userFormSetter('delete'))
    })

    function userFormSetter(prefix) {
        return function () {
            document.getElementById(prefix+'Id').value = this.dataset.userId ?? "";
            document.getElementById(prefix+'Username').value = this.dataset.userName ?? "";
            document.getElementById(prefix+'LastName').value = this.dataset.userSurname ?? "";
            document.getElementById(prefix+'Password').value = this.dataset.userPassword ?? "";
            document.getElementById(prefix+'Email').value = this.dataset.userEmail ?? "";

            const roleSelect = document.getElementById(prefix+'Role');

            for (var i = 0; i < roleSelect.options.length; i++) {
                var option = roleSelect.options[i];
                if (this.dataset.userRoles.includes(option.value)) {
                    option.selected = true;
                }
            }
        }
    }
});
