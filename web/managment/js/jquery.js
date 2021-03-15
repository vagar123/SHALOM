$ (document).ready(function(){
    $('.menu2 li:has(ul)').click(function(e){
        e.preventDefault();

        if($(this).hasClass('activado')){
            $(this).removeClass('activado');
            $(this).children('ul').slideUp();
        }

        else {
            $('.menu2 li ul').slideUp();
            $('.menu2 li').removeClass('activado');
            $(this).addClass('activado');
            $(this).children('ul').slideDown();
        }
    
    });

    $('.menu2 li ul li a').click(function(){
        window.location.href = $(this).attr("href");
    });

});