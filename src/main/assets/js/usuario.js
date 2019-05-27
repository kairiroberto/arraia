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
    var dia = data.substring(8,10)
    var mes = data.substring(5,7)
    var ano = data.substring(0,4)
 
    //Criando um objeto Date usando os valores ano, mes e dia.
    var novaData = new Date(ano, (mes-1) ,dia);
 
    var mesmoDia = parseInt(dia, 10) == parseInt(novaData.getDate());
    var mesmoMes = parseInt(mes, 10) == parseInt(novaData.getMonth()) + 1;
    var mesmoAno = parseInt(ano) == parseInt(novaData.getFullYear());
 
    if ((mesmoDia) && (mesmoMes) && (mesmoAno)) {
        return true;
    }  
    return false;
}

function validarCampos(cpf, nascimento, nome, email, telefone, endereco, cidade) {
    if (!validarCPF(cpf)) {
        alert("CPF inválido.");
        return false;
    }
    if (!validarData(nascimento)) {
        alert('Data inválida.');   
        return false;
    }
    if (nome == '' || email == '' || telefone == '' || endereco == '' || cidade == '') {
        alert("Campos vazios.");
        return false;
    }
    if (nome.length < 8) {
        alert("Nome do usuário muito curto.");
        return false;
    }
    return true;
}

function add() {
    var email = document.getElementById("email").value;
    var telefone = document.getElementById("telefone").value;
    var nome = document.getElementById("nome").value;
    var cpf = document.getElementById("cpf").value;
    var nascimento = document.getElementById("nascimento").value;
    var endereco = document.getElementById("endereco").value;
    var cidade = document.getElementById("cidade").value;

    if (validarCampos(cpf, nascimento, nome, email, telefone, endereco, cidade)) {

        var firebaseConfig = {
            apiKey: "AIzaSyDyw6S4YRYPPRmLiL6DKvHtZnDrqUlzwAU",
            authDomain: "arraia-ea992.firebaseapp.com",
            databaseURL: "https://arraia-ea992.firebaseio.com",
            projectId: "arraia-ea992",
            storageBucket: "arraia-ea992.appspot.com",
            messagingSenderId: "212604244048",
            appId: "1:212604244048:web:b4f33e67ee59faf4"
        };
        // Initialize Firebase
        firebase.initializeApp(firebaseConfig);
        var id = firebase.database().ref().child('usuario').push().key;
        firebase.database().ref('usuario/' + id).set({
            nome: nome,
            email: email,
            telefone: telefone,
            cpf: cpf,
            nascimento: nascimento,
            endereco: endereco,
            cidade: cidade
        }).then(function () {
            alert("Dados salvos com sucesso.");
            window.location.href = "create.html?mensagem=OK";
        }).catch(function (error) {
            var errorCode = error.code;
            var errorMessage = error.message;
            alert("Erro: " + errorCode + ": " + errorMessage);
            window.location.href = "create.html?erro=" + errorCode + "-" + errorMessage;
        });

    } else {
        alert("Faça a correção do(s) campo(s)!");
    }
}

function edit() {
    
}

function findId(id) {
    
}

function findCPF(cpf) {
    
}

function find() {
    
}