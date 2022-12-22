// Globale Variablen für Attribute Configuration und Order
var configCut = "";
var configColor = "";
var configSize = "";
var configPattern = "";
var configPrice = 0.0;
var orderQuantity = 0;
var orderShippingMethod = "";
var orderPrice = 0.0;

$(document).ready(function () {
    // initial die Panels orderDetailsDiv und afterSalesDiv ausblenden
    $("#orderDetailsDiv").hide();
    $("#afterSalesDiv").hide();
});

document.getElementById("btnOrder").addEventListener("click", (e) => {
    $("#configurationDiv").hide();
    $("#orderDetailsDiv").show();
});