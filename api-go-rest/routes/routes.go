package routes

import (
	"log"
	"net/http"

	"go-rest-api/controllers"
	"github.com/gorilla/mux"
)

func HandleRequest() {

	r := mux.NewRouter()

	r.HandleFunc("/", controllers.Home)
	r.HandleFunc("/personalidades", controllers.TodasAsPersonalidades).Methods("Get")
	r.HandleFunc("/personalidades/{id}", controllers.PersonalidadePorId).Methods("Get")
	log.Fatal(http.ListenAndServe(":8000", r))
}
