function clickRespectiveCheckbox(elem) {
    // find checkbox belonging to accordion row
    const cb = elem.closest(".group-row").firstElementChild;
    // toggle checkbox
    cb.checked = cb.checked === true ? false : true;
}

function addToFavorites(elem) {

    const star = elem.closest(".bookmark").firstElementChild;
    const hint = elem.closest(".bookmark").lastElementChild;
    console.log("found my star: ", star);
    if(star.classList.contains("marked")) {
        star.classList.remove("marked");
        star.classList.add("free");
        hint.classList.remove("marked");
        hint.classList.add("free");
    } else {
        star.classList.remove("free");
        star.classList.add("marked");
        hint.classList.remove("free");
        hint.classList.add("marked");
    }
}