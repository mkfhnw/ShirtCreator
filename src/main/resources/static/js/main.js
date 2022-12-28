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
let current_config_id = 1;
let current_cut = "Round";
let current_color = "White";
let current_size = "Small";
let current_pattern = "Plain";
let current_quantity = 1;
let item_price = 15.0;
let current_price = 15.0;

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
            current_color = e.target.value;
            update_configuration();
            swap_shirts();
        })
    })

    // ------------------------------------- Cut & Pattern & Size selectors
    document.getElementById("cutSelect").addEventListener("change", (e) => {
        let cut_select = document.getElementById("cutSelect");
        current_cut = cut_select.options[cut_select.selectedIndex].text;
        update_configuration();
        swap_shirts();
    });
    document.getElementById("patternSelect").addEventListener("change", (e) => {
        let pattern_select = document.getElementById("patternSelect");
        current_pattern = pattern_select.options[pattern_select.selectedIndex].text;
        update_configuration();
        swap_shirts();
    });
    document.getElementById("sizeSelect").addEventListener("change", (e) => {
        let size_select = document.getElementById("sizeSelect");
        current_size = size_select.options[size_select.selectedIndex].text;
        update_configuration();
    });

    // ------------------------------------- Quantity
    document.getElementById("inputQuantity").addEventListener("change", (e) => {
        current_quantity = document.getElementById("inputQuantity").value;
        update_price();
    });

    // ------------------------------------- Shipping selector
    document.getElementById("selectShippingMethod").addEventListener("change", (e) => {
        //TODO
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
    let cut = current_cut.toLowerCase();
    let pattern = current_pattern.toLowerCase();

    // Replace images
    img_front.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', current_color)
            .replaceAll('{side}', "front")
    })
    img_back.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', current_color)
            .replaceAll('{side}', "back")
    })
    img_side.forEach((element) => {
        element.src = shirt_template.replace('{pattern}', pattern)
            .replaceAll('{cut}', cut)
            .replaceAll('{color}', current_color)
            .replaceAll('{side}', "side")
    })

}

function update_configuration() {

    $.getJSON("http://localhost:8080/api/configuration", {"cut" : current_cut , "pattern" : current_pattern , "size" : current_size , "color" : current_color})
        .done(handleConfigurationReply)

    update_price();
}

function handleConfigurationReply(configuration){

    current_config_id = configuration["id"];
    item_price = configuration["price"];
}

function update_price(){

    current_price = item_price * current_quantity;
    document.getElementById("item_price").innerText = current_price.toString() + " CHF";
}
