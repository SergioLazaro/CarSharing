$(document).ready(function(){
    $('#transport-selector').on('change',function(){
        if($(this).val() === 'car'){
            $('#destination-block').show();
            $('#type-block').show();
            $('#year-block').show();
        }
        else{
            $('#destination-block').hide();
            $('#type-block').hide();
            $('#year-block').hide();
        }
    });
});
