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
let current_color = "white";

$(document).ready(function () {

    // Event listeners
    // ------------------------------------- Control panel
    document.getElementById("btnOrder").addEventListener("click", (e) => {
        document.getElementById("configuration-panel").classList.add("d-none");
        document.getElementById("order-panel").classList.remove("d-none");
    });

    // Opens login popup window as soon as the button login is clicked
    document.getElementById("login_button").addEventListener("click", function() {
        const modal = document.getElementById("modal");
        modal.style.display = "block";
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

    // Opens the login popup as soon as the login button is clicked
    document.getElementById("login_button").addEventListener("click", function() {
        document.getElementById("modal").style.display = "block";
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

    // ------------------------------------- Color buttons
    const btns_color = document.getElementsByClassName("btn-color");
    Array.from(btns_color).forEach((element) => {
        element.addEventListener("click", (e) => {
            current_color = e.target.value;
            swap_shirts();
        })
    })

    // ------------------------------------- Cut & Pattern selectors
    document.getElementById("cutSelect").addEventListener("change", (e) => {
        swap_shirts();
    });
    document.getElementById("patternSelect").addEventListener("change", (e) => {
        swap_shirts();
    });

});

// -------------------------- Custom functions
function swap_shirts() {

    // Grab control elements
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
    let cut = cut_select.options[cut_select.selectedIndex].text.toLowerCase();
    let pattern = pattern_select.options[pattern_select.selectedIndex].text.toLowerCase();

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

    // ----------------------------------------------- Handles modal (login popup)
    const openLoginButtons = document.querySelectorAll('[data-modal-target]')
    const closeLoginButtons = document.querySelectorAll('[data-close-button]')
    const overlay = document.getElementById('overlay')

    // Opens login popup
    openLoginButtons.forEach(button => {
        button.addEventListener('click', () => {
            const modal = document.querySelector(button.dataset.modalTarget)
            openModal(modal)
        })
    })

    // Switches the two tabs registration and login
    overlay.addEventListener('click', () => {
        const modals = document.querySelectorAll('.loginModal.active')
        modals.forEach(modal => {
            closeModal(modal)
        })
    })

    // Closes login popup
    closeLoginButtons.forEach(button => {
        button.addEventListener('click', () => {
            const modal = button.closest('.loginModal')
            closeModal(modal)
        })
    })

    function openModal(loginModal) {
        if (loginModal == null)
            return
                loginModal.classList.add('active')
                overlay.classList.add('active')
    }

    function closeModal(loginModal) {
        if (loginModal == null)
            return
                loginModal.classList.remove('active')
                overlay.classList.remove('active')
    }
}