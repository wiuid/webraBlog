document.onreadystatechange = function () {
    if (document.readyState == "complete") {
        document.body.style.display = "block";
    } else {
        document.body.style.display = "none";
    }
}
