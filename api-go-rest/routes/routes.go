package routes

import (
	"log"
	"net/http"

	"go-rest-api/controllers"
	"go-rest-api/middleware"

	"github.com/gorilla/mux"
)

func HandleRequest() {

	r := mux.NewRouter()
	r.Use(middleware.ContentTypeMiddleware)

	r.HandleFunc("/", controllers.Home)
	r.HandleFunc("/api/personalidades", controllers.BuscarTodasAsPersonalidades).Methods("Get")
	r.HandleFunc("/api/personalidades/{id}", controllers.BuscarPersonalidadePorId).Methods("Get")
	r.HandleFunc("/api/personalidades", controllers.CriarNovaPersonalidade).Methods("Post")
	r.HandleFunc("/api/personalidades/{id}", controllers.DeletarPersonalidade).Methods("Delete")
	r.HandleFunc("/api/personalidades/{id}", controllers.EditarPersonalidade).Methods("Put")

	log.Fatal(http.ListenAndServe(":8000", r))
}
