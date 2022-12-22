// Globale Variablen für Attribute Configuration und Order
const configCut = "";
const configColor = "";
const configSize = "";
const configPattern = "";
const configPrice = 0.0;
const orderQuantity = 0;
const orderShippingMethod = "";
const orderPrice = 0.0;

$(document).ready(function () {

    // Event listeners
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

});