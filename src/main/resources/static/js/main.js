// Globale Variablen für Attribute Configuration und Order
const configCut = "";
const configColor = "";
const configSize = "";
const configPattern = "";
const configPrice = 0.0;
const orderQuantity = 0;
const orderShippingMethod = "";
const orderPrice = 0.0;
const shirt_template = "/T-Shirts/{pattern}/{cut}/Tshirt_{cut}_{color}_{side}_basic.PNG"

$(document).ready(function () {

    // Event listeners
    // ------------------------------------- Control panel
    document.getElementById("btnOrder").addEventListener("click", (e) => {
        document.getElementById("configuration-panel").classList.add("d-none");
        document.getElementById("order-panel").classList.remove("d-none");
    });

    document.getElementById("btnBack").addEventListener("click", (e) => {
        document.getElementById("order-panel").classList.add("d-none");
        document.getElementById("configuration-panel").classList.remove("d-none");
    });

    document.getElementById("btnSubmit").addEventListener("click", (e) => {
        document.getElementById("order-panel").classList.add("d-none");
        document.getElementById("aftersales-panel").classList.remove("d-none");
    });

    document.getElementById("btnNewOrder").addEventListener("click", (e) => {
        document.getElementById("aftersales-panel").classList.add("d-none");
        document.getElementById("configuration-panel").classList.remove("d-none");
    });

    // ------------------------------------- Color buttons
    const btns_color = document.getElementsByClassName("btn-color");
    Array.from(btns_color).forEach((element) => {
        element.addEventListener("click", (e) => {
            swap_shirts(e);
        })
    })

});

// -------------------------- Custom functions
function swap_shirts(event) {

    // Grab control elements
    let size_select = document.getElementById("sizeSelect");
    let cut_select = document.getElementById("cutSelect");
    let pattern_select = document.getElementById("patternSelect");

    // Grab images
    let front_view = document.getElementById("front-view");
    let back_view = document.getElementById("back-view");
    let side_view = document.getElementById("side-view");
    let front_view_thumb = document.getElementById("front-view-thumb");
    let back_view_thumb = document.getElementById("back-view-thumb");
    let side_view_thumb = document.getElementById("side-view-thumb");
    let img_front = [front_view, front_view_thumb]
    let img_back = [back_view, back_view_thumb]
    let img_side = [side_view, side_view_thumb]

    // Grab all information required to build target string
    let color = event.target.value;
    let cut = cut_select.options[cut_select.selectedIndex].text.toLowerCase();
    let pattern = pattern_select.options[pattern_select.selectedIndex].text.toLowerCase();

    // Replace images
    img_front.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', color)
            .replaceAll('{side}', "front")
    })
    img_back.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', color)
            .replaceAll('{side}', "back")
    })
    img_side.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', color)
            .replaceAll('{side}', "side")
    })


}