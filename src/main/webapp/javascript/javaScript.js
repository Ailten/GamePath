
//function for submit form language in header (reload page after select new language).
function submitLanguageForm(idForm){
    let domForm = document.getElementById(idForm);
    if(!domForm){
        console.log('form not found with id ['+idForm+'].');
        return;
    }
    domForm.submit();
}