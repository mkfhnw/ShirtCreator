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

let currentAccount = null;


$(document).ready(function () {

    /******************
     EVENT LISTENERS
     ******************/
    // ------------------------------------- Control panel
    document.getElementById("btnOrder").addEventListener("click", (e) => {

        if (document.getElementById('tblShoppingBasket').length < 1) {
            alert("Shopping Cart is empty")
        } else {

            // If no user is logged in, take shipping details
            if (currentAccount == null) {
                document.getElementById("configuration-panel").classList.add("d-none");
                document.getElementById("order-panel").classList.remove("d-none");
            } else {
                // User is logged in -> Shipping address already available. Skip order-panel, and set order to definitive.
                updateOrder(true);
                document.getElementById("configuration-panel").classList.add("d-none");
                document.getElementById("aftersales-panel").classList.remove("d-none");
            }
        }
    });

    document.getElementById("btnBack").addEventListener("click", (e) => {
        document.getElementById("order-panel").classList.add("d-none");
        document.getElementById("configuration-panel").classList.remove("d-none");
    });

    document.getElementById("btnSubmit").addEventListener("click", (e) => {

        // Validate user input
        let inputs = [
            document.getElementById('inputFirstName-registration1'),
            document.getElementById('inputLastName-registration2'),
            document.getElementById('inputStreet'),
            document.getElementById('inputPlz'),
            document.getElementById('inputLocation'),
            document.getElementById('inputEmail')
            ];

        let no_errors = true;
        for(let input of inputs) {
            if(!input.checkValidity()) {
                input.setAttribute('aria-invalid', input.checkValidity);
                input.classList.add('is-invalid');
                console.log(input.value)
                no_errors = false;
            } else {
                input.classList.remove('is-invalid');
            }
        }

        if(no_errors) {
            document.getElementById("order-panel").classList.add("d-none");
            document.getElementById("aftersales-panel").classList.remove("d-none");
            // Only create new customer if user is not logged in
            if(currentAccount == null) {
                createCustomer();
            }

            // Set definitive state on order
            updateOrder(true);
        }

    });

    document.getElementById("btnNewOrder").addEventListener("click", (e) => {
        document.getElementById("aftersales-panel").classList.add("d-none");
        document.getElementById("configuration-panel").classList.remove("d-none");
        setVariablesToInitial();
    });

    document.getElementById("btnAddToCart").addEventListener("click", (e) => {

        //Validate user Input
        let inputs = [
            document.getElementById('inputQuantity'),
        ];

        let no_errors = true;
        for(let input of inputs) {
            if(!input.checkValidity()) {
                input.setAttribute('aria-invalid', input.checkValidity);
                input.classList.add('is-invalid');
                console.log(input.value)
                no_errors = false;
            } else {
                input.classList.remove('is-invalid');
            }
        }

        if(no_errors) {
            if (orderId === -1) {
                createOrder();
            } else {
                addItemToOrder(configQuantity, configId);
            }
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

    // ------------------------------------- Registration
    document.getElementById("create-account-button").addEventListener("click", (e) => {
        // Validate user input
        let inputs = [
            document.getElementById('FirstName-registration'),
            document.getElementById('LastName-registration'),
            document.getElementById('Street-registration'),
            document.getElementById('Plz-registration'),
            document.getElementById('Location-registration'),
            document.getElementById('Email-registration'),
            document.getElementById('Password-registration')
        ];

        let no_errors = true;
        for(let input of inputs) {
            if(!input.checkValidity()) {
                input.setAttribute('aria-invalid', input.checkValidity);
                input.classList.add('is-invalid');
                console.log(input.value)
                no_errors = false;
            } else {
                input.classList.remove('is-invalid');
            }
        }

        if(no_errors) {
            createAccount();
        }
    });

    // ------------------------------------- Login
    document.getElementById("okLogin").addEventListener("click", (e) => {
        // Validate user input
        let inputs = [
            document.getElementById('Email-login'),
            document.getElementById('password-login'),
        ];

        let no_errors = true;
        for(let input of inputs) {
            if(!input.checkValidity()) {
                input.setAttribute('aria-invalid', input.checkValidity);
                input.classList.add('is-invalid');
                console.log(input.value)
                no_errors = false;
            } else {
                input.classList.remove('is-invalid');
            }
        }

        if(no_errors) {
            e.preventDefault();
            loginAccount();}
    })

    // ------------------------------------- Logout
    document.getElementById("okLogout").addEventListener("click", (e) => {
        e.preventDefault();
        logoutAccount();
    })

    // ------------------------------------- Order History
    document.getElementById("okOrders").addEventListener("click", (e) => {
        e.preventDefault();
        showOrderHistory();
    })

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

    // TODO: This throws errors, check the JS console in the browser
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

    // Hide orders-modal
    let ordersModal = new bootstrap.Modal('#modal-orders');
    ordersModal.hide();

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

    // If customer is logged in, create order with customer id
    if(currentAccount != null) {
        $.ajax({
            type: "GET",
            url: `/api/account/${currentAccount}`,
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                customerId = response['customer']['id'];
                let payload = {customerId: customerId, orderDate: order_date};
                $.ajax({
                    type: "POST",
                    url: "/api/order/",
                    data: JSON.stringify(payload),
                    success: handleCreateOrder,
                    dataType: 'json',
                    contentType: 'application/json'
                });
            }
        });
    } else {
        // Otherwise, go on without customer ID
        let payload = {customerId: null, orderDate: order_date}
        $.ajax({
            type: "POST",
            url: "/api/order/",
            data: JSON.stringify(payload),
            success: handleCreateOrder,
            dataType: 'json',
            contentType: 'application/json'
        });
    }

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

function getOrder() {
    $.getJSON("api/order/" + orderId).done(handleGetOrderReply);
}

// update existing order - when present with customerId
function updateOrder(definitiveState) {
    let order_date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString()
    let customer_id = customerId !== -1 ? customerId : null;
    $.ajax({
        type: "PUT",
        url: "/api/order/" + orderId,
        data: JSON.stringify({customerId: customer_id, orderDate: order_date, definitive: definitiveState}),
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

// Create account
function createAccount() {

    // Create JS object
    let account = {
        'firstName': document.getElementById("FirstName-registration").value,
        'lastName': document.getElementById("LastName-registration").value,
        'street': document.getElementById("Street-registration").value,
        'plz': document.getElementById("Plz-registration").value,
        'location': document.getElementById("Location-registration").value,
        'eMail': document.getElementById("Email-registration").value,
        'password': document.getElementById("Password-registration").value
    }

    // Send to backend
    $.ajax({
        type: 'POST',
        url: '/api/account/',
        data: JSON.stringify(account),
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {
            handleAccount(response);

            if(response != null) {
                // Clear UI
                document.getElementById("FirstName-registration").value = '';
                document.getElementById("LastName-registration").value = '';
                document.getElementById("Street-registration").value = '';
                document.getElementById("Plz-registration").value = '';
                document.getElementById("Location-registration").value = '';
                document.getElementById("Email-registration").value = '';
                document.getElementById("Password-registration").value = '';

                // Blend in feedback
                let toast = document.getElementById('toast-registration');
                let toastText = document.getElementById('toast-registration-text');
                toast.classList.add('text-bg-success');
                toastText.innerText = 'Registration successful!'
                let bootstrapToast = new bootstrap.Toast(toast);
                bootstrapToast.show();
            } else {
                // Blend in feedback
                let toast = document.getElementById('toast-registration');
                let toastText = document.getElementById('toast-registration-text');
                toast.classList.add('text-bg-danger');
                toastText.innerText = 'Registration failed!'
                let bootstrapToast = new bootstrap.Toast(toast);
                bootstrapToast.show();
            }

        }
    })

}

// Log in account
function loginAccount() {

    // Create JS object
    let login = {
        'eMail': document.getElementById('Email-login').value,
        'password': document.getElementById('password-login').value
    }

    // Send to backend
    $.ajax({
        type: 'PUT',
        url: '/api/account/login',
        data: JSON.stringify(login),
        dataType: 'json',
        contentType: 'application/json',
        success: handleLogin
    })

}

function logoutAccount() {
    let token = {
        'token': currentAccount
    }

    // Send to backend
    $.ajax({
        type: 'PUT',
        url: '/api/account/logout',
        data: JSON.stringify(token),
        dataType: 'json',
        contentType: 'application/json',
        success: handleLogout
    })
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
    getOrder();
    getOrderPrice();
}

function handleDeleteItemFromOrder(response) {
    getOrder();
    getOrderPrice();
}

function handleGetOrderReply(response) {
    $("#tblShoppingBasket tbody").empty();
    let count = 0;
    for (let item of response["items"]) {
        count++;
        let configuration = item["configuration"];
        let link = "javascript:deleteItemFromOrder(" + item["orderItemId"] + ")";
        let quantity = item["quantity"];
        let itemPrice = configuration["price"];
        let newRow = "<tr>";
        newRow += "<td>" + count + "</td>";
        newRow += "<td>" + configuration["cut"] + "</td>";
        newRow += "<td>" + configuration["color"] + "</td>";
        newRow += "<td>" + configuration["pattern"] + "</td>";
        newRow += "<td>" + configuration["size"] + "</td>";
        newRow += "<td>" + quantity + "</td>";
        newRow += "<td>" + itemPrice + "</td>";
        newRow += "<td>" + "CHF " + (quantity * itemPrice) + "</td>";
        newRow += "<td><a href=\"" + link + "\">" + "X" + "</a></td>";
        newRow += "</tr>";
        $("#tblShoppingBasket tbody").append(newRow);
    }
    if (response["items"].length > 0) {
        document.getElementById("emptyCart").classList.add("d-none");
        document.getElementById("tblShoppingBasket").classList.remove("d-none");
    } else {
        document.getElementById("emptyCart").classList.remove("d-none");
        document.getElementById("tblShoppingBasket").classList.add("d-none");
    }
}

function handleUpdateShippingMethod(response) {
    getOrderPrice();
}

function handleCreateCustomer(customer) {
    customerId = customer["id"];
    updateOrder(false);
}

function handleGetOrderPrice(price) {
    updateOrderPrice(price);
}

function handleAccount(response) {

    if(response != null) {
        currentAccount = response['token'];
    }
}

function handleLogin(response) {

    // Save reference
    currentAccount = response['token'];

    // Blend in feedback
    let toast = document.getElementById('toast-login');
    let toastText = document.getElementById('toast-login-text');

    if(response['token'] === "") {
        toastText.innerText = 'Could not log in. Check your credentials!';
        toast.classList.remove('text-bg-success');
        toast.classList.add('text-bg-danger');
    } else {
        toastText.innerText = 'Login successful!';
        toast.classList.remove('text-bg-danger');
        toast.classList.add('text-bg-success');
        document.getElementById('okLogout').classList.remove('d-none');
        document.getElementById('okOrders').classList.remove('d-none');
        document.getElementById('okLogin').classList.add('d-none');
        document.getElementById('password-login').value = '';
    }

    let bootstrapToast = new bootstrap.Toast(document.getElementById('toast-login'))
    bootstrapToast.show()
}

function handleLogout(response){

    // Blend in feedback & set account to null
    let toast = document.getElementById('toast-login');
    let toastText = document.getElementById('toast-login-text');

    if(response === true) {
        currentAccount = null;

        toastText.innerText = 'Logout successful!';
        toast.classList.remove('text-bg-danger');
        toast.classList.add('text-bg-success');
        document.getElementById('okLogout').classList.add('d-none');
        document.getElementById('okOrders').classList.add('d-none');
        document.getElementById('okLogin').classList.remove('d-none');

    } else {
        toastText.innerText = 'Could not log out.';
        toast.classList.remove('text-bg-success');
        toast.classList.add('text-bg-danger');
    }

    let bootstrapToast = new bootstrap.Toast(document.getElementById('toast-login'));
    bootstrapToast.show();

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

function showOrderHistory() {

    // Close login modal since only 1 modal can be opened at once
    document.getElementById('close-modal-button').click();
    document.getElementById("modal").style.display = "none";

    // Populate order history modal
    // Note: The call to /api/orders is somewhat risky since front-end users could theoretically obtain every order history for an arbitrary user
    let tableBody = document.getElementById('orders-table').getElementsByTagName('tbody')[0]
    let tableFooter = document.getElementById('orders-table').getElementsByTagName('tfoot')[0]

    // Get customer ID via API
    $.ajax({
        type: "GET",
        url: `/api/account/${currentAccount}`,
        dataType: 'json',
        contentType: 'application/json',
        success: function(accountResponse) {

            // Get orders for customer ID via API
            $.ajax({
                type: 'GET',
                url: '/api/orders',
                data: {'customerId': accountResponse['customer']['id']},
                success: function(ordersResponse) {

                    // Clear table first
                    $('#orders-table tbody tr').remove();
                    $('#orders-table tfoot tr').remove();

                    // Fill table if there are orders at all
                    if(ordersResponse.length > 0) {
                        let total = 0;
                        for(const order of ordersResponse) {
                            let newRow = tableBody.insertRow(-1);
                            newRow.insertCell(0).appendChild(document.createTextNode(order['orderId']));
                            newRow.insertCell(1).appendChild(document.createTextNode(order['totalQuantity']));
                            newRow.insertCell(2).appendChild(document.createTextNode(new Date(order['orderDate']).toUTCString()));
                            newRow.insertCell(3).appendChild(document.createTextNode(order['shippingMethod']));
                            newRow.insertCell(4).appendChild(document.createTextNode(order['price']));
                            total += order['price'];
                        }
                        let newFooter = tableFooter.insertRow(-1);
                        newFooter.insertCell(0).appendChild(document.createTextNode('Total'));
                        newFooter.insertCell(1).appendChild(document.createTextNode(''));
                        newFooter.insertCell(2).appendChild(document.createTextNode(''));
                        newFooter.insertCell(3).appendChild(document.createTextNode(''));
                        newFooter.insertCell(4).appendChild(document.createTextNode(total.toFixed(2)));
                    } else {
                        document.getElementById('orders-table').classList.add('d-none');
                        let node = document.createElement('h5');
                        node.innerText = 'You did not order anything yet. Go ahead and try out our configurator!';
                        node.classList.add('text-center');
                        document.getElementById('orders-modal-content').appendChild(node);

                    }


                },
                error: function(ordersResponse) {

                }
            });
        }
    });


    // Show order history modal
    let ordersModal = new bootstrap.Modal(document.getElementById('modal-orders'));
    ordersModal.show();

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

    document.getElementById("emptyCart").classList.remove("d-none");
    document.getElementById("tblShoppingBasket").classList.add("d-none");

    document.getElementById("cutSelect").selectedIndex = 0;
    document.getElementById("sizeSelect").selectedIndex = 0;
    document.getElementById("patternSelect").selectedIndex = 0;
    document.getElementById("inputQuantity").value = 1;

    document.getElementById("config_price").innerText = "CHF " + current_price.toString();
    document.getElementById("order_price").innerText = "CHF " + orderPrice.toString();
    swap_shirts();
}