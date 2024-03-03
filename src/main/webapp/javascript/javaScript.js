
//loading page.
window.addEventListener('load', function(){
    document.addEventListener('scroll', function(){
        replacePopUpError();
    });
});

//event call when load a new page.
function eventLoadPage(urlPath){

    replacePopUpError();

    //set event focus input text combo box tag with class.
    $('.check-box-menu-on-focus').on('click', function(event){
        focusOnComboBox(event.target.parentElement);
    });

    //set event focus input text combo box tag with class.
    //let checkBoxMenuOnFocus = document.getElementsByClassName('check-box-menu-on-focus');
    //for(let i=0; i<checkBoxMenuOnFocus.length; i++){
    //    checkBoxMenuOnFocus[i].addEventListener('click', function(event){
    //        focusOnComboBox(event.target.parentElement);
    //    });
    //}

}


//function for submit form (send id).
function submitFormById(idForm){
    let domForm = document.getElementById(idForm);
    if(!domForm){
        console.log('form not found with id ['+idForm+'].');
        return;
    }
    domForm.submit();
}


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

//function for auto select input filter in combo box when click on it.
function focusOnComboBox(comboBoxFocused){

    //get id of element and sanitise it.
    let idFocused = comboBoxFocused.getAttribute('id');
    idFocused ||= comboBoxFocused.parentElement().getAttribute('id');
    let idSanitise = idFocused.replace(/_.{1,}$/, '');

    //get panel combo box by id.
    let panelComboBox = document.getElementById(idSanitise + '_panel');
    if(panelComboBox == null){
        console.log('panel combo box not found ['+idSanitise+'].');
        return;
    }

    //get input text into panel combo box (input filter).
    let inputComboBoxFilter = panelComboBox.getElementsByClassName('ui-inputtext');
    if(inputComboBoxFilter.length !== 1){
        console.log('panel combo box have zero or more than one input text ['+idSanitise+'].');
        return;
    }

    //focus input text filter for user read on it.
    inputComboBoxFilter[0].focus();

    //form-crud-...:input-..._*** //from this.
    //form-crud-...:input-... //or from this.
    //form-crud-...:input-..._panel .ui-inputtext //to that.

}

//function to reload current page.
function reloadPage(){
    document.getElementById('reload-page').click();
}