package routes

import (
	"log"
	"net/http"

	"go-rest-api/controllers"
	"go-rest-api/middleware"

	"github.com/gorilla/mux"
	"github.com/gorilla/handlers"
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

	log.Fatal(http.ListenAndServe(":8000", handlers.CORS(handlers.AllowedOrigins([]string{"*"}))(r))) // fazendo com que qualquer aplicação tenha acesso (front no caso)
}
