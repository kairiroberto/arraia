function validarCPF(cpf) {
    var Soma;
    var Resto;
    Soma = 0;
    if (cpf == "00000000000") {
        return false;
    }
    for (i = 1; i <= 9; i++) {
        Soma = Soma + parseInt(cpf.substring(i - 1, i)) * (11 - i);
    }
    Resto = (Soma * 10) % 11;
    if ((Resto == 10) || (Resto == 11)) {
        Resto = 0;
    }
    if (Resto != parseInt(cpf.substring(9, 10))) {
        return false;
    }
    Soma = 0;
    for (i = 1; i <= 10; i++) {
        Soma = Soma + parseInt(cpf.substring(i - 1, i)) * (12 - i);
    }
    Resto = (Soma * 10) % 11;
    if ((Resto == 10) || (Resto == 11)) {
        Resto = 0;
    }
    if (Resto != parseInt(cpf.substring(10, 11))) {
        return false;
    }
    return true;
}

function validarData(data) {
    //alert(data);
    var data = data;
    var dia = data.substring(8, 10)
    var mes = data.substring(5, 7)
    var ano = data.substring(0, 4)

    //Criando um objeto Date usando os valores ano, mes e dia.
    var novaData = new Date(ano, (mes - 1), dia);

    var mesmoDia = parseInt(dia, 10) == parseInt(novaData.getDate());
    var mesmoMes = parseInt(mes, 10) == parseInt(novaData.getMonth()) + 1;
    var mesmoAno = parseInt(ano) == parseInt(novaData.getFullYear());

    if ((mesmoDia) && (mesmoMes) && (mesmoAno)) {
        return true;
    }
    return false;
}

function validarCampos(imei, cpf, nascimento, nome, email, telefone, endereco, cidade) {
    if (!validarCPF(cpf)) {
        alert("CPF inválido.");
        //showAndroidToast("CPF inválido.");
        return false;
    }
    if (!validarData(nascimento)) {
        alert('Data inválida.');
        //showAndroidToast("Data inválida.");
        return false;
    }
    if (nome == '' || email == '' || telefone == '' || endereco == '' || cidade == '') {
        alert("Campos vazios.");
        //showAndroidToast("Campos vazios.");
        return false;
    }
    if (imei == null || imei == "") {
        alert("Campo imei vazio");
        return false;
    }
    if (nome.length < 8) {
        alert("Nome do usuário muito curto.");
        //showAndroidToast("Nome do usuário muito curto.");
        return false;
    }
    return true;
}

function validar(usuario) {
    if (validarCampos(usuario.imei, usuario.cpf, usuario.nascimento, usuario.nome, usuario.email, usuario.telefone, usuario.endereco, usuario.cidade)) {
        return true;
    } else {
        var msg = "Faça a correção do(s) campo(s)!";
        alert(msg);
        return false;
    }
}