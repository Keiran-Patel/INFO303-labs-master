/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


/* global Vue */
var catalogueApi = '//localhost:8080/api/catalogue';
var productApi = ({id}) => `http://localhost:8080/api/catalogue/product/${id}`;
const app = Vue.createApp({

    data() {
        return {
            catalogue: new Array(),
            product: new Object()
        };
    },
    mounted() {
        this.getCatalogue();
    },
    methods: {
        getCatalogue() {
            axios.get(catalogueApi)
                    .then(response => {
                        this.catalogue = response.data;
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });
        },
        addProduct() {
            axios.post(catalogueApi, this.product)
                    .then(() => {
                        // update the catalogue
                        this.getCatalogue();
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
                    });
        }
    }
});

// mount the page at the <main> tag - this needs to be the last line in the file
app.mount("main");