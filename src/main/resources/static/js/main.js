// Globale Variablen für Attribute Configuration und Order
let configId = 1;
let configCut = "Round";
let configColor = "White";
let configSize = "Small";
let configPattern = "Plain";
let configPrice = 15;
let configQuantity = 1;
let orderId = -1;
const orderQuantity = 0;
let orderShippingMethod = "Economy";
let orderPrice = 0;
const shirt_template = "/T-Shirts/{pattern}/{cut}/Tshirt_{cut}_{color}_{side}_basic.PNG"
let shoppingCart = [];

let current_price = 15;

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
        // TODO CreateCustomer, UpdateOrder
    });

    document.getElementById("btnNewOrder").addEventListener("click", (e) => {
        document.getElementById("aftersales-panel").classList.add("d-none");
        document.getElementById("configuration-panel").classList.remove("d-none");
    });

    document.getElementById("btnAddToCart").addEventListener("click", (e) => {

        // TODO Graphics
        //document.getElementById("aftersales-panel").classList.add("d-none");
        //document.getElementById("configuration-panel").classList.remove("d-none");
console.log("btnAddToCart " + orderId)
        if (orderId === -1) {
            create_order();
        } else {
            console.log("btnAddToCart2 " + orderId)
            //update_order();
            add_to_shopping_cart();
            update_order_price();
        }

    });

    // ------------------------------------- Color buttons
    const btns_color = document.getElementsByClassName("btn-color");
    Array.from(btns_color).forEach((element) => {
        element.addEventListener("click", (e) => {
            console.log("color");
            configColor = e.target.value;
            update_configuration();
            swap_shirts();
        })
    })

    // ------------------------------------- Cut & Pattern & Size selectors
    document.getElementById("cutSelect").addEventListener("change", (e) => {
        console.log("cut " + configCut);
        let cut_select = document.getElementById("cutSelect");
        configCut = cut_select.options[cut_select.selectedIndex].text;
        update_configuration();
        swap_shirts();
        console.log("cut " + configCut);
    });
    document.getElementById("patternSelect").addEventListener("change", (e) => {
        console.log("pattern " + configPattern);
        let pattern_select = document.getElementById("patternSelect");
        configPattern = pattern_select.options[pattern_select.selectedIndex].text;
        update_configuration();
        swap_shirts();
        console.log("pattern " + configPattern);
    });
    document.getElementById("sizeSelect").addEventListener("change", (e) => {
        console.log("size " + configSize);
        let size_select = document.getElementById("sizeSelect");
        configSize = size_select.options[size_select.selectedIndex].text;
        update_configuration();
        console.log("size " + configSize);
    });

    // ------------------------------------- Quantity
    document.getElementById("inputQuantity").addEventListener("change", (e) => {
        console.log("quantity " + configQuantity);
        configQuantity = document.getElementById("inputQuantity").value;
        update_config_price();
        console.log("quantity " + configQuantity);
    });

    // ------------------------------------- Shipping selector
    document.getElementById("selectShippingMethod").addEventListener("change", (e) => {
        let shipping_select = document.getElementById("selectShippingMethod");
        orderShippingMethod = shipping_select.options[shipping_select.selectedIndex].value;
        update_shipping_method();
    });

    // -------------------------------------- Handle landingpages
    // Opens login popup as soon as the button login is clicked
    document.getElementById("login_button").addEventListener("click", function() {
        const modal = document.getElementById("modal");
        modal.style.display = "block";
    });

    // Closes the login popup
    document.getElementById("close-modal-button").addEventListener("click", function() {
        document.getElementById("modal").style.display = "none";
    });

    // Switches between the two tabs login and registration
    const modalTabs = document.querySelectorAll(".modal-tab");

    modalTabs.forEach(function(modalTab) {
        modalTab.addEventListener("click", function() {
            const tab = this.dataset.tab;
            const tabContent = document.getElementById(tab);

            modalTabs.forEach(function(modalTab) {
                modalTab.classList.remove("active");
            });

            this.classList.add("active");

            const modalTabContents = document.querySelectorAll(".modal-tab-content");
            modalTabContents.forEach(function(modalTabContent) {
                modalTabContent.classList.remove("active");
            });

            tabContent.classList.add("active");
        });
    });

    // Content handling
    const loginTab = document.querySelector('[data-tab="login"]');
    const registrationTab = document.querySelector('[data-tab="registration"]');
    const registrationDetails = document.querySelector('#registrationDetails');
    const registrationDetails2 = document.querySelector('#email-password-registration');
    const loginTabContent = document.querySelector('#login');
    const registrationTabContent = document.querySelector('#registration');

    // Add click event listeners to the tab login (none = hidden / block = shown)
    loginTab.addEventListener('click', (e) => {
        registrationTabContent.style.display = 'none';
        registrationDetails.style.display = 'none';
        registrationDetails2.style.display = 'none';
        loginTabContent.style.display = 'block';
    });
    // Add click event listeners to the tab registration (none = hidden / block = shown)
    registrationTab.addEventListener('click', (e) => {
        loginTabContent.style.display = 'none';
        registrationTabContent.style.display = 'block';
        registrationDetails.style.display = 'block';
        registrationDetails2.style.display = 'block';
    });

    // ROUTE: Opens Contact landingpage contact as soon as Contact on the navigation bar is clicked
    const contactLink = document.getElementById('contact-link');
    contactLink.addEventListener('click', function(event) {
        // prevent the default link behavior (navigating to a new page)
        event.preventDefault();
        window.location.href = 'contact.html';
    });

    // CONTACT: Deletes all inputs as soon as the send button is clicked
    document.getElementById('myform').addEventListener('submit', function(event) {
        event.preventDefault(); // prevent the form from being submitted

        // clear the input fields
        document.getElementById('name').value = '';
        document.getElementById('subject').value = '';
        document.getElementById('phone').value = '';
        document.getElementById('email').value = '';
        document.getElementById('message').value = '';
    });

});

// -------------------------- Custom functions
function swap_shirts() {

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
    let cut = configCut.toLowerCase();
    let pattern = configPattern.toLowerCase();

    // Replace images
    img_front.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', configColor)
            .replaceAll('{side}', "front")
    })
    img_back.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', configColor)
            .replaceAll('{side}', "back")
    })
    img_side.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', configColor)
            .replaceAll('{side}', "side")
    })

}

function update_configuration() {

    $.getJSON("http://localhost:8080/api/configuration", {"cut" : configCut , "pattern" : configPattern , "size" : configSize , "color" : configColor})
        .done(handleConfigurationReply)
}

function create_order() {
    let order_date = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString()

    $.ajax({
        type: "POST",
        url: "/api/order/",
        data: JSON.stringify({ customer : null , orderDate: order_date }),
        success: orderCreated,
        dataType: 'json',
        contentType: 'application/json'
    });
}

function update_order() {
    let order_date = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString()

    $.ajax({
        type: "POST",
        url: "/api/order/",
        data: JSON.stringify({ customer : null , orderDate: order_date }),
        success: null,
        dataType: 'json',
        contentType: 'application/json'
    });
}

function update_shipping_method() {

    $.ajax({
        type: "PUT",
        url: "/api/order/"+orderId+"/updateShippingMethod/"+orderShippingMethod,
        data: null,
        success: null,
        dataType: 'json',
        contentType: 'application/json'
    });
}

function orderCreated(response){
    orderId = response;
    add_to_shopping_cart();
    update_order_price();
}


function addItemToOrder(quantity, configuration_id){
console.log("addItemToOrder " + quantity + configuration_id + " " + orderId);
    $.ajax({
        type: "PUT",
        url: "/api/order/"+orderId+"/addItem",
        data: JSON.stringify({ quantity : quantity , configurationId: configuration_id }),
        success: null,
        dataType: 'json',
        contentType: 'application/json'
    });

}



function handleConfigurationReply(configuration){
    console.log("handle config " + configPrice)
    configId = configuration["id"];
    configPrice = configuration["price"];
    console.log("handle config " + configPrice)
    update_config_price();
}

function update_config_price(){
    console.log("update price " + current_price);
    current_price = configPrice * configQuantity;
    document.getElementById("config_price").innerText = current_price.toString() + ".- CHF";
    console.log("update price " + current_price);
}

function update_order_price() {
    console.log("update orderPrice " + orderPrice);
    orderPrice += current_price;
    document.getElementById("order_price").innerText = orderPrice.toString() + ".- CHF";
    console.log("update orderPrice " + orderPrice);
}


function add_to_shopping_cart() {
    console.log("add_to_shopping_cart " + orderId);
    shoppingCart.push({"id" : configId, "quantity" : configQuantity});
    console.log("add_to_shopping_cart2 " + orderId);
    addItemToOrder(configQuantity, configId);
    update_shopping_cart();

    console.log(shoppingCart);
}

function update_shopping_cart() {
    $("#tblShoppingBasket tbody").empty();

    for (let item of shoppingCart) {
        var newRow = "<tr>";
        newRow += "<td>" + item["id"]+ "</td>";
        newRow += "<td>" + item["quantity"] + "</td>";
        newRow += "</tr>";
        $("#tblShoppingBasket tbody").append(newRow);
    }

}