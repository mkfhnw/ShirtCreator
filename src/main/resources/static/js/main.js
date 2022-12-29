// Globale Variablen für Attribute Configuration und Order
let configId = 1;
let configCut = "Round";
let configColor = "White";
let configSize = "Small";
let configPattern = "Plain";
let configPrice = 15;
let configQuantity = 1;
let current_price = 15;
let current_item_id = -1;

let orderId = -1;
const orderQuantity = 0;
let orderShippingMethod = "Economy";
let orderPrice = 0;

let customerId = -1;

const shirt_template = "/T-Shirts/{pattern}/{cut}/Tshirt_{cut}_{color}_{side}_basic.PNG"
let shoppingCart = [];


$(document).ready(function () {

    /******************
     EVENT LISTENERS
     ******************/
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
        createCustomer();
    });

    document.getElementById("btnNewOrder").addEventListener("click", (e) => {
        document.getElementById("aftersales-panel").classList.add("d-none");
        document.getElementById("configuration-panel").classList.remove("d-none");
        setVariablesToInitial();
    });

    document.getElementById("btnAddToCart").addEventListener("click", (e) => {
        // TODO Graphics
        if (orderId === -1) {
            createOrder();
        } else {
            addItemToOrder(configQuantity, configId);
        }
    });

    // ------------------------------------- Color buttons
    const btns_color = document.getElementsByClassName("btn-color");
    Array.from(btns_color).forEach((element) => {
        element.addEventListener("click", (e) => {
            configColor = e.target.value;
            getConfiguration();
            swap_shirts();
        })
    })

    // ------------------------------------- Cut & Pattern & Size selectors
    document.getElementById("cutSelect").addEventListener("change", (e) => {
        let cut_select = document.getElementById("cutSelect");
        configCut = cut_select.options[cut_select.selectedIndex].text;
        getConfiguration();
        swap_shirts();
    });
    document.getElementById("patternSelect").addEventListener("change", (e) => {
        let pattern_select = document.getElementById("patternSelect");
        configPattern = pattern_select.options[pattern_select.selectedIndex].text;
        getConfiguration();
        swap_shirts();
    });
    document.getElementById("sizeSelect").addEventListener("change", (e) => {
        let size_select = document.getElementById("sizeSelect");
        configSize = size_select.options[size_select.selectedIndex].text;
        getConfiguration();
    });

    // ------------------------------------- inputQuantity
    document.getElementById("inputQuantity").addEventListener("change", (e) => {
        configQuantity = document.getElementById("inputQuantity").value;
        update_config_price();
    });

    // ------------------------------------- Shipping selector
    document.getElementById("selectShippingMethod").addEventListener("change", (e) => {
        let shipping_select = document.getElementById("selectShippingMethod");
        orderShippingMethod = shipping_select.options[shipping_select.selectedIndex].value;
        updateShippingMethod();
    });

    /******************
     Handle landingpages
     ******************/
    // Opens login popup as soon as the button login is clicked
    document.getElementById("login_button").addEventListener("click", function () {
        const modal = document.getElementById("modal");
        modal.style.display = "block";
    });

    // Closes the login popup
    document.getElementById("close-modal-button").addEventListener("click", function () {
        document.getElementById("modal").style.display = "none";
    });

// Switches between the two tabs login and registration and sets them active or not
    const modalTabs = document.querySelectorAll(".modal-tab");

    modalTabs.forEach(function (modalTab) {
        modalTab.addEventListener("click", function () {
            const tab = this.dataset.tab;
            const tabContent = document.getElementById(tab);

            modalTabs.forEach(function (modalTab) {
                modalTab.classList.remove("active");
            });

            this.classList.add("active");

            const modalTabContents = document.querySelectorAll(".modal-tab-content");
            modalTabContents.forEach(function (modalTabContent) {
                modalTabContent.style.display = 'none';
            });

            tabContent.style.display = 'block';
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
    contactLink.addEventListener('click', (e) => {
        // prevent the default link behavior (navigating to a new page)
        event.preventDefault();
        window.location.href = 'contact.html';
    });

    // TODO: Does not work yet! When clicked on "Home", index.html opens
    const homeLink = document.getElementById('home-link');

    homeLink.addEventListener('click', (e) => {
        window.open('index.html', '_blank');
    });

    // CONTACT: Deletes all inputs as soon as the send button is clicked
    document.getElementById('myform').addEventListener('submit', function (event) {
        event.preventDefault(); // prevent the form from being submitted

        // clear the input fields
        document.getElementById('name').value = '';
        document.getElementById('subject').value = '';
        document.getElementById('phone').value = '';
        document.getElementById('email').value = '';
        document.getElementById('message').value = '';
    });

});


/******************
 REST functions
 ******************/
// Update configuration variables
function getConfiguration() {
    $.getJSON("http://localhost:8080/api/configuration", {
        "cut": configCut,
        "pattern": configPattern,
        "size": configSize,
        "color": configColor
    })
        .done(handleGetConfiguration)
}

// create a new Order
function createOrder() {
    let order_date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString()
    $.ajax({
        type: "POST",
        url: "/api/order/",
        data: JSON.stringify({customer: null, orderDate: order_date}),
        success: handleCreateOrder,
        dataType: 'json',
        contentType: 'application/json'
    });
}

// add Item to existing order
function addItemToOrder(quantity, configuration_id) {
    $.ajax({
        type: "PUT",
        url: "/api/order/" + orderId + "/addItem",
        data: JSON.stringify({quantity: quantity, configurationId: configuration_id}),
        success: handleAddItemToOrder,
        dataType: 'json',
        contentType: 'application/json'
    });
}

// delete Item from existing order
function deleteItemFromOrder(item_id) {
    $.ajax({
        type: "PUT",
        url: "/api/order/" + orderId + "/deleteItem/" + item_id,
        data: null,
        success: handleDeleteItemFromOrder,
        dataType: 'json',
        contentType: 'application/json'
    });
}

// update existing order - when present with customerId
function updateOrder() {
    let order_date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString()
    let customer_id = null;
    if (customerId !== -1)
        customer_id = customerId;
    $.ajax({
        type: "PUT",
        url: "/api/order/" + orderId,
        data: JSON.stringify({customerId: customer_id, orderDate: order_date}),
        success: null,
        dataType: 'json',
        contentType: 'application/json'
    });
}

// update shipping_method of existing order
function updateShippingMethod() {
    $.ajax({
        type: "PUT",
        url: "/api/order/" + orderId + "/updateShippingMethod/" + orderShippingMethod,
        data: null,
        success: handleUpdateShippingMethod,
        dataType: 'json',
        contentType: 'application/json'
    });
}

// get price of existing order
function getOrderPrice() {
    $.getJSON("http://localhost:8080/api/order/" + orderId + "/getPrice")
        .done(handleGetOrderPrice)
}

// create customer and address
function createCustomer() {
    let inputFirstName = document.getElementById("inputFirstName-registration1").value;
    let inputLastName = document.getElementById("inputLastName-registration2").value;
    let inputStreet = document.getElementById("inputStreet").value;
    let inputPlz = document.getElementById("inputPlz").value;
    let inputLocation = document.getElementById("inputLocation").value;
    let inputEmail = document.getElementById("inputEmail").value;
    let address_data = {street: inputStreet, plz: inputPlz, location: inputLocation};
    let customer_data = {firstName: inputFirstName, lastName: inputLastName, email: inputEmail, address: address_data};

    $.ajax({
        type: "POST",
        url: "/api/customer/",
        data: JSON.stringify(customer_data),
        success: handleCreateCustomer,
        dataType: 'json',
        contentType: 'application/json'
    });
}


/******************
 Handle functions of REST functions
 ******************/
function handleGetConfiguration(configuration) {
    configId = configuration["id"];
    configPrice = configuration["price"];
    update_config_price();
}

function handleCreateOrder(response) {
    orderId = response;
    addItemToOrder(configQuantity, configId);
}

function handleAddItemToOrder(response) {
    current_item_id = response;
    shoppingCart.push({
        "itemId": current_item_id,
        "configId": configId,
        "quantity": configQuantity,
        "itemPrice": configPrice,
        "sumPrice": current_price,
        "cut": configCut,
        "pattern": configPattern,
        "size": configSize,
        "color": configColor
    });
    document.getElementById("emptyCart").classList.add("d-none");
    update_shopping_cart();
    getOrderPrice();
}

function handleDeleteItemFromOrder(response) {
    let index = -1;
    for (let item of shoppingCart) {
        if (item["itemId"] === response) {
            index = shoppingCart.indexOf(item)
        }
    }
    shoppingCart.splice(index, 1);
    update_shopping_cart();
    getOrderPrice();
}

function handleUpdateShippingMethod(response) {
    getOrderPrice();
}

function handleCreateCustomer(customer) {
    customerId = customer["id"];
    updateOrder();
}

function handleGetOrderPrice(price) {
    updateOrderPrice(price);
}


/******************
 GUI Update functions
 ******************/
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

// Updates Configuration Price
function update_config_price() {
    current_price = configPrice * configQuantity;
    document.getElementById("config_price").innerText = "CHF " + current_price.toString();
}

// Updates Order Price
function updateOrderPrice(price) {
    orderPrice = price;
    document.getElementById("order_price").innerText = "CHF " + orderPrice.toString();
}

// Updates Shopping cart
function update_shopping_cart() {
    $("#tblShoppingBasket tbody").empty();
    for (let item of shoppingCart) {
        var link = "javascript:deleteItemFromOrder(" + item["itemId"] + ")";
        var newRow = "<tr>";
        newRow += "<td>" + (shoppingCart.indexOf(item) + 1) + "</td>";
        newRow += "<td>" + item["cut"] + "</td>";
        newRow += "<td>" + item["color"] + "</td>";
        newRow += "<td>" + item["pattern"] + "</td>";
        newRow += "<td>" + item["size"] + "</td>";
        newRow += "<td>" + item["quantity"] + "</td>";
        newRow += "<td>" + item["itemPrice"] + "</td>";
        newRow += "<td>" + "CHF " + item["sumPrice"] + "</td>";
        newRow += "<td><a href=\"" + link + "\">" + "X" + "</a></td>";
        newRow += "</tr>";
        $("#tblShoppingBasket tbody").append(newRow);
    }
    if (shoppingCart.length > 0) {
        document.getElementById("emptyCart").classList.add("d-none");
        document.getElementById("tblShoppingBasket").classList.remove("d-none");
    }
}


/******************
 Set all to initial
 ******************/
function setVariablesToInitial() {
    configId = 1;
    configCut = "Round";
    configColor = "White";
    configSize = "Small";
    configPattern = "Plain";
    configPrice = 15;
    configQuantity = 1;
    current_price = 15;
    current_item_id = -1;

    orderId = -1;
    orderShippingMethod = "Economy";
    orderPrice = 0;

    customerId = -1;

    shoppingCart = [];
    update_shopping_cart();

    document.getElementById("cutSelect").selectedIndex = 0;
    document.getElementById("sizeSelect").selectedIndex = 0;
    document.getElementById("patternSelect").selectedIndex = 0;
    document.getElementById("inputQuantity").value = 1;

    document.getElementById("config_price").innerText = current_price.toString() + ".- CHF";
    document.getElementById("order_price").innerText = orderPrice.toString() + ".- CHF";
    swap_shirts();
}