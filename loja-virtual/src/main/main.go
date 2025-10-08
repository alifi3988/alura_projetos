package main

import (
	"fmt"
	"html/template"
	"net/http"
	model "loja-virtual/src/main/models"

	_ "github.com/lib/pq"
	_ "github.com/go-sql-driver/mysql"
	
)

var temp = template.Must(template.ParseGlob("./templates/*.html"))

func main() {
	fmt.Println("✅ Iniciando o template do programa...")
	http.HandleFunc("/", index)
	fmt.Println("✅ Iniciando programa em: https://localhost:8000")
	err := http.ListenAndServe(":8000", nil)

	if err != nil {
		fmt.Println("❌ Erro ao iniciar servidor:", err)
	}

	fmt.Println("✅ Finalizando o programa...")

}

func index(w http.ResponseWriter, r *http.Request) {

	produtos := model.BuscaTodosOsProdutos()

	err := temp.ExecuteTemplate(w, "Index", produtos)

	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
