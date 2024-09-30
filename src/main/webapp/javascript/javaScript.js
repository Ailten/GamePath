
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

    //call function for loading page crud product.
    if(document.getElementById('form-crud-product'))
        loadPageCrudProduct();

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

}

//function to reload current page.
function reloadPage(){
    document.getElementById('reload-page').click();
}

//function call when load page crud Product.
function loadPageCrudProduct(){

    //get all img of product.
    $('div[id="form-crud-product:panel-draw-picture"] img').on('click', function(event){
        eventClickZoomImg(event.target);
    });
}

//event call when click on an image for zoom.
function eventClickZoomImg(imgClicked){

    //get data from img.
    let urlImg = imgClicked.getAttribute('src');
    let altImg = imgClicked.getAttribute('alt');

    //build div modal.
    let imgZoomModal = document.body.appendChild(document.createElement('div'));
    imgZoomModal.classList.add('img-zoom-modal');
    imgZoomModal.style.width = window.innerWidth +'px';
    imgZoomModal.style.height = window.innerHeight +'px';

    //build image into div modal.
    let imgIntoZoomModal = imgZoomModal.appendChild(document.createElement('img'));
    imgIntoZoomModal.setAttribute('src', urlImg);
    imgIntoZoomModal.setAttribute('alt', altImg);

    //image full size modal but stay ratio.
    let imgRatio = imgIntoZoomModal.clientWidth/imgIntoZoomModal.clientHeight;
    let modalRatio = imgZoomModal.clientWidth/imgZoomModal.clientHeight;
    if(imgRatio > modalRatio){
        console.log('t')
        let width = imgZoomModal.clientWidth - 30;
        imgIntoZoomModal.style.width = width + 'px';
        imgIntoZoomModal.style.height = (width * imgRatio) + 'px';
    }else{
        let height = imgZoomModal.clientHeight - 30;
        imgIntoZoomModal.style.height = height + 'px';
        imgIntoZoomModal.style.width = (height * imgRatio) + 'px';
    }

    //add event click for exit modal.
    imgZoomModal.addEventListener('click', function(){
        eventCloseModalZoomImg();
    });
    imgIntoZoomModal.addEventListener('click', function(){
        eventCloseModalZoomImg();
    });
}

//event call when click for exit modal zoom img.
function eventCloseModalZoomImg(){
    $('.img-zoom-modal').remove();
}


//function call when load page order analytics.
function loadAnalytics(orderAnalyticsData){

    //get canvas and data.
    let canvas = document.getElementById('canvas-analytics');
    let objDataForCharJs = JSON.parse(orderAnalyticsData);

    //make graphics.
    new Chart(canvas, objDataForCharJs);

}