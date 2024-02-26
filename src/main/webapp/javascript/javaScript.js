
//function for submit form language in header (reload page after select new language).
function submitLanguageForm(idForm){
    let domForm = document.getElementById(idForm);
    if(!domForm){
        console.log('form not found with id ['+idForm+'].');
        return;
    }
    domForm.submit();
}

//loading page.
window.addEventListener('load', function(){
    document.addEventListener('scroll', function(){
        replacePopUpError();
    });
})

//function for scrolling position pop-up message.
function replacePopUpError(){

    let cardPopUpMessage = document.getElementById('form-pop-up-message:pop-up-message_container'); //get pop up error element.
    let header = document.getElementById('header'); //get header.

    if(!header || !cardPopUpMessage)
        return;

    let headerHeight = header.getBoundingClientRect().height; //get height of header.
    let scrollY = window.scrollY; //get scroll position.

    cardPopUpMessage.style.top = (Math.max(headerHeight - scrollY, 0) +10) +'px';
}