function clickRespectiveCheckbox(elem) {
    // find checkbox belonging to accordion row
    const cb = elem.closest(".group-row").firstElementChild;
    // toggle checkbox
    cb.checked = cb.checked === true ? false : true;
}