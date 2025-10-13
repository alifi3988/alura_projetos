package controllers

import (
	"encoding/json"
	"fmt"
	"go-rest-api/models"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

func Home(w http.ResponseWriter, r *http.Request) {
	fmt.Fprint(w, "Home Page")
}

func TodasAsPersonalidades(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(models.Personalidades)
}

func PersonalidadePorId(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id := vars["id"]

	for _, personalidade := range models.Personalidades {
		if strconv.Itoa(personalidade.Id) == id {
			json.NewEncoder(w).Encode(personalidade)
		}
	}
}