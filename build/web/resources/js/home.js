$(document).ready(function () {
    $('#loading').hide();
   
   
   
    
    function alerta(){
        alert("teste de alert");
        return true;
    }
    
    
   
   
    $.ajax({
        url: 'rodape'
    }).done(function (resposta) {
        $('#rodape').html(resposta);
    });
    
    $('#botao_novo_calculo').on('click', function () {           
        $.ajax({
            url: 'd/prestacao/cadastrar_lancamento'
        }).done(function (resposta) {
            $.getScript('js/plugins/jquery.mask.defaults.js');
            $('#conteudo').html(resposta);
        });
    });
    
    $('#botao_apurar_taxa_cc').on('click', function () {
        $.ajax({
            url: 'teste'
        }).done(function (resposta) {
            $.getScript('js/plugins/jquery.mask.defaults.js');
            $('#conteudo').html(resposta);
        });
    });
    
    $('#botao_correcao_monetaria').on('click', function () {
        $.ajax({
            url: 'd/prestacao/calcular_correcao_monetaria'
        }).done(function (resposta) {
            $.getScript('js/plugins/jquery.mask.defaults.js');
            $('#conteudo').html(resposta);
        });
    });
    
    
    
    $('#botao_novo_calculo').on('click',function (){
       $.ajax({
        url: 'novo-calculo'
    }).done(function (resposta) {
        $('#conteudo').html(resposta);
    });
        
    });
    
    
    $('#botao_calculo_anterior').on('click', function () {
         $.ajax({
        url: 'calculoAnterior'
    }).done(function (resposta) {
        $('#conteudo').html(resposta);
    });
    });
    
    
    
    $('#botao_gsv').on('click', function () {
         $.ajax({
        url: 'gsv'
    }).done(function (resposta) {
        $('#conteudo').html(resposta);
    });
    });
    
    
    
    
    
    
    
    

});


 




$(document)
    .ajaxStart(function () {
        $('#loading').show();
    })
    .ajaxStop(function () {
        $('#loading').hide();
    });